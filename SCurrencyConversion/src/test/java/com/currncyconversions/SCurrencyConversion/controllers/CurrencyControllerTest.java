package com.currncyconversions.SCurrencyConversion.controllers;

import com.currncyconversions.SCurrencyConversion.entity.CurrencyRates;
import com.currncyconversions.SCurrencyConversion.repositories.CurrencyRatesRepositories;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CurrencyController.class)
class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyRatesRepositories mockRepository;

    
    @Test
    void testShowConversionHistory() throws Exception {
        // Setup
        // Configure CurrencyRatesRepositories.findAll(...).
        final CurrencyRates currencyRates1 = new CurrencyRates();
        currencyRates1.setFromCurrency("fromCurrency");
        currencyRates1.setToCurrency("toCurrency");
        currencyRates1.setAmount(new BigDecimal("0.00"));
        currencyRates1.setDate("dateStr");
        currencyRates1.setConversionResult("conversionResult");
        final List<CurrencyRates> currencyRates = List.of(currencyRates1);
        when(mockRepository.findAll()).thenReturn(currencyRates);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/conversion-history")
                        .accept(MediaType.TEXT_HTML))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        // Parse the HTML content with JSoup
        Document doc = Jsoup.parse(response.getContentAsString());

        // Validate the title (h1 element)
        Elements h1Elements = doc.select("h1");
        assertThat(h1Elements).hasSize(1); // Ensure there is only one h1 element
        assertThat(h1Elements.first().text()).isEqualTo("Currency Conversion History");

        // Validate table headers (th elements)
        Elements tableHeaders = doc.select("table.table th");
        assertThat(tableHeaders).hasSize(5); // Ensure there are five th elements
        assertThat(tableHeaders.get(0).text()).isEqualTo("From Currency");
        assertThat(tableHeaders.get(1).text()).isEqualTo("To Currency");
        assertThat(tableHeaders.get(2).text()).isEqualTo("Amount");
        assertThat(tableHeaders.get(3).text()).isEqualTo("Date");
        assertThat(tableHeaders.get(4).text()).isEqualTo("Conversion Result");
    }

}
