package com.currncyconversions.SCurrencyConversion.controllers;


import com.currncyconversions.SCurrencyConversion.entity.CurrencyRates;
import com.currncyconversions.SCurrencyConversion.repositories.CurrencyRatesRepositories;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

@Controller
public class CurrencyController {

    @Autowired
    private CurrencyRatesRepositories repository;

    @GetMapping("/hello")
    public String index() {
        return "index";
    }

    private final OkHttpClient client = new OkHttpClient();

   /* @GetMapping("/currency-conversion")
    public ResponseEntity<String> performCurrencyConversion(
            @RequestParam("fromCurrency") String fromCurrency,
            @RequestParam("toCurrency") String toCurrency,
            @RequestParam("amount") BigDecimal amount,
            @RequestParam("date") String dateStr) {

        int maxLength = 255;

        // Check for invalid input
        if (fromCurrency == null || toCurrency == null || amount == null) {
            return ResponseEntity.badRequest().body("Invalid input. Please provide valid values.");
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
                String truncatedResponse = responseBody.length() > maxLength ? responseBody.substring(0, maxLength) : responseBody;

//                BigDecimal conversionRate = extractConversionRate(responseBody);
//                BigDecimal convertedAmount = amount.multiply(conversionRate);

                CurrencyRates currencySave = new CurrencyRates();
                currencySave.setFromCurrency(fromCurrency);
                currencySave.setToCurrency(toCurrency);
                BigDecimal amountValue = new BigDecimal(String.valueOf(amount));
                currencySave.setAmount(amountValue);
                currencySave.setDate(dateStr);
                currencySave.setConversionResult(truncatedResponse);

                repository.save(currencySave);
                return ResponseEntity.ok("Conversion Result: " + responseBody);
            } else {
                if (response.code() == 400) {
                    return ResponseEntity.badRequest().body("Invalid conversion request. Please check your input.");
                } else if (response.code() == 404) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Currency not found.");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Request failed: " + response.code() + " - " + response.message());
                }
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while making the request: " + e.getMessage());
        }
    }*/

    /*@GetMapping("/currency-conversion")
    public ResponseEntity<String> performCurrencyConversion(
            @RequestParam("fromCurrency") String fromCurrency,
            @RequestParam("toCurrency") String toCurrency,
            @RequestParam("amount") BigDecimal amount,
            @RequestParam("date") String dateStr) {

        // Check for invalid input
        if (fromCurrency == null || toCurrency == null || amount == null) {
            return ResponseEntity.badRequest().body("Invalid input. Please provide valid values.");
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

                // Display the converted amount on the webpage
                return ResponseEntity.ok("Converted Amount: " + convertedAmount);
            } else {
                // Handle API response errors
                if (response.code() == 400) {
                    return ResponseEntity.badRequest().body("Invalid conversion request. Please check your input.");
                } else if (response.code() == 404) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Currency not found.");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Request failed: " + response.code() + " - " + response.message());
                }
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while making the request: " + e.getMessage());
        }
    }*/

    /*@GetMapping("/currency-conversion")
    public ResponseEntity<String> performCurrencyConversion(
            @RequestParam("fromCurrency") String fromCurrency,
            @RequestParam("toCurrency") String toCurrency,
            @RequestParam("amount") BigDecimal amount,
            @RequestParam("date") String dateStr) {

        // Check for invalid input
        if (fromCurrency == null || toCurrency == null || amount == null) {
            return ResponseEntity.badRequest().body("Invalid input. Please provide valid values.");
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
                currencySave.setConversionResult(convertedAmount.toString()); // Store as a string
                repository.save(currencySave);

                // Display the converted amount on the webpage
                return ResponseEntity.ok("Converted Amount: " + convertedAmount);
            } else {
                // Handle API response errors
                if (response.code() == 400) {
                    return ResponseEntity.badRequest().body("Invalid conversion request. Please check your input.");
                } else if (response.code() == 404) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Currency not found.");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Request failed: " + response.code() + " - " + response.message());
                }
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while making the request: " + e.getMessage());
        }
    }*/

   /* @GetMapping("/currency-conversion")
    public String performCurrencyConversion(
            @RequestParam("fromCurrency") String fromCurrency,
            @RequestParam("toCurrency") String toCurrency,
            @RequestParam("amount") BigDecimal amount,
            @RequestParam("date") String dateStr,
            Model model) {

        // Check for invalid input
        if (fromCurrency == null || toCurrency == null || amount == null) {
            model.addAttribute("convertedAmount", null);
            return "index"; // Return the same page
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
                currencySave.setConversionResult(convertedAmount.toString()); // Store as a string
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
            @RequestParam("fromCurrency") String fromCurrency,
            @RequestParam("toCurrency") String toCurrency,
            @RequestParam("amount") BigDecimal amount,
            @RequestParam("date") String dateStr,
            Model model) {
        // Check for invalid input
        if (fromCurrency == null || toCurrency == null || amount == null) {
            model.addAttribute("convertedAmount", null);
            return "index"; // Return the same page
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
                currencySave.setConversionResult(convertedAmount.toString()); // Store as a string
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
    }






    @GetMapping("/conversion-history")
    public String showConversionHistory(Model model) {
        // Retrieve the conversion history from the database
        List<CurrencyRates> conversionHistory = repository.findAll();

        // Add the history to the model
        model.addAttribute("conversionHistory", conversionHistory);

        return "conversionHistory";
    }


    @GetMapping("/locale")
    public String changeLocale(@RequestParam String language) {
        System.out.println("Language selected" + language);
        return "index";
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

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
