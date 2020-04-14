package com.notspend.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.time.LocalDateTime;
import java.util.List;


public class CurrencyProcessor {
    private static final String API_LINK_NBU = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";

    private static String allCurrencies = null;
    private static LocalDateTime lastUpdated;

    private CurrencyProcessor(){
    }

    private static String getCurrencies(){
        try (CloseableHttpClient client = HttpClients.createDefault()){
            HttpGet httpGet = new HttpGet(API_LINK_NBU);

            HttpResponse response = client.execute(httpGet);
            ResponseHandler<String> handler = new BasicResponseHandler();
            lastUpdated = LocalDateTime.now();
            return handler.handleResponse(response);
        } catch (Exception e) {
            //TODO: log it
            System.out.println("Currency server is down");
        }
        return null;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Currency {
        private Double rate;
        private String cc;

        public Currency() {
        }

        public Double getRate() {
            return rate;
        }

        public String getCc() {
            return cc;
        }
    }

    public static Double getCurrencyRateToUah(String code){
        ObjectMapper mapper = new ObjectMapper();
        List<Currency> currencyList = null;
        try {
            if (allCurrencies == null || lastUpdated.plusHours(6L).isBefore(LocalDateTime.now())){
                allCurrencies = getCurrencies();
            }
            currencyList = mapper.readValue(allCurrencies, new TypeReference<List<Currency>>(){});
        } catch (Exception e) {
            //TODO: Log error here
            e.printStackTrace();
            return 1D;
        }
        for (Currency currency : currencyList){
            if (currency.getCc().equals(code)){
                return currency.getRate();
            }
        }
        return 1D;
    }
}
