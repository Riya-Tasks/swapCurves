package com.example.xdsapp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@Service
public class XdsConnectionService {

    private final RestTemplate restTemplate;

    // Inject RestTemplate via constructor
    public XdsConnectionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Method to accept an already populated map (key-value pairs from XML)
    private Map<String, String> getMap() {
        // Assume this map comes from XML parsing
        return Map.of(
            "YieldCurve/EUR/ESTER/1D", "SwapCurve/official/${DATE}/LONDON/CLOSE/EUR/ESTER/1D/MSSEOD",
            "YieldCurve/EUR/EURIB/6M", "SwapCurve/official/${DATE}/LONDON/CLOSE/EUR/EURI6/6M/MSSEOD"
            // Add other key-value pairs as per actual XML parsing
        );
    }

    public void processYieldCurveData(String date, String username, String password) {
        Map<String, String> dataMap = getMap(); // Get the already populated map

        // Iterate through the map and process only the "YieldCurve" entries
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            if (entry.getKey().startsWith("YieldCurve")) {
                // Replace ${DATE} in the URL with the provided date
                String firstUrl = entry.getValue().replace("${DATE}", date);
                String baseUrl = "https://xds-int.systems.uk.hsbc/api/v1/documents/";

                // Complete first URL (from SwapCurve)
                String completeFirstUrl = baseUrl + firstUrl;
                System.out.println("First URL: " + completeFirstUrl);

                // Fetch data from the first URL
                String assetValue = fetchDataFromXds(completeFirstUrl, username, password);

                // Generate the second URL based on asset value and change "SwapCurve" to "SwapRates"
                String secondUrl = firstUrl.replace("SwapCurve", "SwapRates");
                secondUrl = secondUrl.substring(0, secondUrl.lastIndexOf('/') + 1) + assetValue + "_ABB/MSSEOD";
                String completeSecondUrl = baseUrl + secondUrl;

                System.out.println("Second URL: " + completeSecondUrl);

                // Fetch data from the second URL and parse it
                fetchAndParseXdsData(completeSecondUrl, username, password);
            }
        }
    }

    // Method to fetch data from the XDS system using the complete URL
    private String fetchDataFromXds(String url, String username, String password) {
        // Setup headers for Basic Authentication
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);

        // Create an HTTP entity with headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make the HTTP GET request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        // Log response for debugging purposes
        System.out.println("Response from first URL: " + response.getBody());

        // For simplicity, return a dummy asset value (replace this with actual logic)
        return "EUR_EURIB12M_EURIB6M";
    }

    // Method to fetch and parse data from the second XDS URL
    private void fetchAndParseXdsData(String url, String username, String password) {
        // Setup headers for Basic Authentication
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);

        // Create an HTTP entity with headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make the HTTP GET request to the second URL
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        // Log response for debugging purposes
        System.out.println("Response from second URL: " + response.getBody());

        // Here, parse the response and map it to your XdsData class
        // Implement the parsing logic based on the response structure
    }
}
