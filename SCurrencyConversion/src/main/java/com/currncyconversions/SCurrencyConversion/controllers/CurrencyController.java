package com.currncyconversions.SCurrencyConversion.controllers;


import com.currncyconversions.SCurrencyConversion.entity.CurrencyRates;
import com.currncyconversions.SCurrencyConversion.repositories.CurrencyRatesRepositories;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.ui.Model;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Controller
public class CurrencyController {

    @Autowired
    private CurrencyRatesRepositories repository;

    private WebProperties.LocaleResolver localeResolver;

  /*  @GetMapping("/hello")
    public String index() {
        return "index";
    }*/

    private final OkHttpClient client = new OkHttpClient();



   /* @GetMapping("/currency-conversion")
    public String performCurrencyConversion( @Valid
            @RequestParam("fromCurrency") String fromCurrency,
            @RequestParam("toCurrency") String toCurrency,
            @RequestParam("amount") BigDecimal amount,
            @RequestParam("date") String dateStr,
            Model model) {
        // Check for invalid input
        *//*if (fromCurrency == null  || toCurrency == null || amount == null ) {
            model.addAttribute("convertedAmount", null);
            return "error_page"; // Return the same page
        }*//*

        if (fromCurrency == null  || toCurrency == null || amount == null ) {
            model.addAttribute("errorMessage", "Invalid input. Please provide valid values.");
            return "error_page"; // Return the error page
        }

        if (amount.toString().matches(".*[a-zA-Z@$].*")) {
            model.addAttribute("errorMessage", "invalid input");
            return "error_page"; // Return the error page
        }



        try {
            String apiUrl = "https://api.apilayer.com/fixer/convert?" +
                    "to=" + toCurrency +
                    "&from=" + fromCurrency +
                    "&amount=" + amount +
                    "&date=" + dateStr;

            Request request = new Request.Builder()
                    .url(apiUrl)
                    .addHeader("apikey", "8JpJ9HZ4x6C2mvmAHTIVtMVOFUF1wLZT")
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseBody = response.body().string();

                // Parse the JSON response using Spring's JsonParser
                JsonParser jsonParser = JsonParserFactory.getJsonParser();
                Map<String, Object> jsonResponse = jsonParser.parseMap(responseBody);
                BigDecimal convertedAmount = new BigDecimal(jsonResponse.get("result").toString());

                // Save the conversion result to the database
                CurrencyRates currencySave = new CurrencyRates();
                currencySave.setFromCurrency(fromCurrency);
                currencySave.setToCurrency(toCurrency);
                currencySave.setAmount(amount);
                currencySave.setDate(dateStr);
                currencySave.setConversionResult(convertedAmount.toString());// Store as a string
                repository.save(currencySave);

                // Add the converted amount to the model for display
                model.addAttribute("convertedAmount", convertedAmount);
            } else {
                // Handle API response errors
                model.addAttribute("convertedAmount", null);
            }
        } catch (IOException e) {
            model.addAttribute("convertedAmount", null);
        }

        return "index"; // Return the same page
    }*/

    @GetMapping("/currency-conversion")
    public String performCurrencyConversion(
            @Valid @RequestParam("fromCurrency") String fromCurrency,
            @RequestParam("toCurrency") String toCurrency,
            @RequestParam("amount") BigDecimal amount,
            @RequestParam("date") String dateStr,
            Model model
    ) {
        if (fromCurrency == null || toCurrency == null || amount == null) {
            model.addAttribute("errorMessage", "Invalid input. Please provide valid values.");
            return "error_page";
        }

        if (amount.toString().matches(".*[a-zA-Z@$].*")) {
            model.addAttribute("errorMessage", "Invalid input");
            return "error_page";
        }

        try {
            String apiUrl = "https://api.apilayer.com/fixer/convert?" +
                    "to=" + toCurrency +
                    "&from=" + fromCurrency +
                    "&amount=" + amount +
                    "&date=" + dateStr;

            Request request = new Request.Builder()
                    .url(apiUrl)
                    .addHeader("apikey", "8JpJ9HZ4x6C2mvmAHTIVtMVOFUF1wLZT")
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JsonParser jsonParser = JsonParserFactory.getJsonParser();
                Map<String, Object> jsonResponse = jsonParser.parseMap(responseBody);

                BigDecimal convertedAmount = new BigDecimal(jsonResponse.get("result").toString());

                // Add proper null checks for todayRate
                BigDecimal todayRate = jsonResponse.get("info") != null ?
                        new BigDecimal(((Map<String, Object>) jsonResponse.get("info")).get("rate").toString()) : BigDecimal.ZERO;

                CurrencyRates currencySave = new CurrencyRates();
                currencySave.setFromCurrency(fromCurrency);
                currencySave.setToCurrency(toCurrency);
                currencySave.setAmount(amount);
                currencySave.setDate(dateStr);
                currencySave.setConversionResult(convertedAmount.toString());
                currencySave.setTodayRate(todayRate);

                repository.save(currencySave);

                model.addAttribute("convertedAmount", convertedAmount);
                model.addAttribute("todayRate", todayRate);
            } else {
                model.addAttribute("convertedAmount", null);
            }
        } catch (IOException e) {
            model.addAttribute("convertedAmount", null);
        }

        return "index";
    }



    @GetMapping("/conversion-history")
    public String showConversionHistory(Model model) {
        // Retrieve the conversion history from the database
        List<CurrencyRates> conversionHistory = repository.findAll();

        // Add the history to the model
        model.addAttribute("conversionHistory", conversionHistory);

        return "conversionHistory";
    }


    /*@GetMapping("/locale")
    public String changeLocale(@RequestParam String language) {
        System.out.println("Language selected" + language);
        return "index";
    }*/
    @PostMapping("/changeLang")
    public String changeLang(@RequestParam String lang, HttpServletRequest request, HttpServletResponse response) {

        System.out.println( "Change lang " + lang);
        request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale(lang));



        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("pageTitle", "Currency Converter Application");

        Locale currentLocale = request.getLocale();
        String countryCode = currentLocale.getCountry();
        String countryName = currentLocale.getDisplayCountry();

        String langCode = currentLocale.getLanguage();
        String langName = currentLocale.getDisplayLanguage();

        System.out.println(countryCode + ": " + countryName);
        System.out.println(langCode + ": " + langName);

        System.out.println("//////");
        String[] language = Locale.getISOLanguages();

        for (String lang : language) {
            Locale locale = new Locale(lang);
//            System.out.println(lang +":"+locale.getDisplayLanguage());
        }

        return "index";
    }

    /*@GetMapping("/home")
    public String home() {
        return "home";
    }*/
}
