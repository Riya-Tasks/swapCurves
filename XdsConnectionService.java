// // import javax.xml.parsers.DocumentBuilder;
// // import javax.xml.parsers.DocumentBuilderFactory;
// // import org.w3c.dom.Document;
// // import org.w3c.dom.Element;
// // import org.w3c.dom.NodeList;
// // import java.util.ArrayList;
// // import java.util.List;
// // import org.w3c.dom.Node;
// //
// // @Service
// // public class XDSConnectionService {
// //
// //     private final RestTemplate restTemplate;
// //
// //     @Autowired
// //     public XDSConnectionService(RestTemplate restTemplate) {
// //         this.restTemplate = restTemplate;
// //     }
// //
// //     public List<SwapRate> getSwapRatesFromXds(Map<String, String> dataMap, String username, String password, String date) {
// //         List<SwapRate> swapRatesList = new ArrayList<>();
// //         String baseUrl = "https://xds-int.systems.uk.hsbc/api/v1/documents/";
// //
// //         // Loop through the dataMap to process each entry
// //         for (Map.Entry<String, String> entry : dataMap.entrySet()) {
// //             String key = entry.getKey();
// //             String value = entry.getValue();
// //
// //             // Check if the key starts with "YieldCurve"
// //             if (key.startsWith("YieldCurve")) {
// //                 // Form the first URL (SwapCurve URL)
// //                 String url1 = baseUrl + value.replace("${DATE}", date);
// //
// //                 // Make the first request to get SwapCurve data
// //                 String swapCurveResponse = getDataFromXds(url1, username, password);
// //
// //                 // Parse the response to extract assetName
// //                 String assetName = parseAssetNameFromResponse(swapCurveResponse);
// //
// //                 // Form the second URL (SwapRates URL)
// //                 String url2 = url1.replace("SwapCurve", "SwapRates").replace("MSSEOD", assetName + "/MSSEOD");
// //
// //                 // Make the second request to get SwapRates data
// //                 String swapRatesResponse = getDataFromXds(url2, username, password);
// //
// //                 // Parse the SwapRates response and store data in SwapRate class
// //                 SwapRate swapRate = parseSwapRatesFromResponse(swapRatesResponse);
// //                 swapRatesList.add(swapRate);
// //             }
// //         }
// //
// //         return swapRatesList;
// //     }
// //
// //     private String getDataFromXds(String url, String username, String password) {
// //         HttpHeaders headers = new HttpHeaders();
// //         String auth = username + ":" + password;
// //         byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
// //         String authHeader = "Basic " + new String(encodedAuth);
// //         headers.set("Authorization", authHeader);
// //         HttpEntity<String> entity = new HttpEntity<>(headers);
// //
// //         // Make the GET request
// //         ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
// //         return response.getBody();
// //     }
// //
// //
// //
// //     private String parseAssetNameFromResponse(String swapCurveResponse) {
// //         try {
// //             // Parse the response string into an XML Document
// //             DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
// //             DocumentBuilder builder = factory.newDocumentBuilder();
// //             Document document = builder.parse(new ByteArrayInputStream(swapCurveResponse.getBytes()));
// //
// //             // Normalize the XML structure
// //             document.getDocumentElement().normalize();
// //
// //             // Locate the SwapRates element in the XML
// //             NodeList nodeList = document.getElementsByTagName("SwapRates");
// //             if (nodeList.getLength() > 0) {
// //                 Element swapRatesElement = (Element) nodeList.item(0);
// //
// //                 // Extract the assetName attribute
// //                 return swapRatesElement.getAttribute("assetName");
// //             }
// //         } catch (Exception e) {
// //             e.printStackTrace();
// //         }
// //
// //         // If assetName was not found, return an empty string or handle accordingly
// //         return "";
// //     }
// //
// //
// //
// //
// //     private SwapRate parseSwapRatesFromResponse(String swapRatesResponse) {
// //         SwapRate swapRate = new SwapRate();
// //         List<String> terms = new ArrayList<>();
// //         List<Double> midRates = new ArrayList<>();
// //         List<Double> spreads = new ArrayList<>();
// //
// //         try {
// //             // Parse the response string into an XML Document
// //             DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
// //             DocumentBuilder builder = factory.newDocumentBuilder();
// //             Document document = builder.parse(new ByteArrayInputStream(swapRatesResponse.getBytes()));
// //
// //             // Normalize the XML structure
// //             document.getDocumentElement().normalize();
// //
// //             // Locate the SwapRates element
// //             NodeList swapRatesList = document.getElementsByTagName("SwapRates");
// //             if (swapRatesList.getLength() > 0) {
// //                 Element swapRatesElement = (Element) swapRatesList.item(0);
// //
// //                 // Populate basic SwapRate fields
// //                 swapRate.setLocation(swapRatesElement.getAttribute("location"));
// //                 swapRate.setMarketTime(swapRatesElement.getAttribute("marketTime"));
// //                 swapRate.setCurrency(swapRatesElement.getAttribute("ccy"));
// //                 swapRate.setRateFixingIndex(swapRatesElement.getAttribute("rateFixingIndex"));
// //                 swapRate.setIndexTerm(swapRatesElement.getAttribute("indexTerm"));
// //                 swapRate.setName(swapRatesElement.getAttribute("name"));
// //
// //                 // Extract <Quote> elements and get terms, midRates, and spreads
// //                 NodeList quoteList = swapRatesElement.getElementsByTagName("Quote");
// //                 for (int i = 0; i < quoteList.getLength(); i++) {
// //                     Node quoteNode = quoteList.item(i);
// //                     if (quoteNode.getNodeType() == Node.ELEMENT_NODE) {
// //                         Element quoteElement = (Element) quoteNode;
// //
// //                         // Extract term, midRate, and spread attributes
// //                         String term = quoteElement.getAttribute("term");
// //                         String midRateStr = quoteElement.getAttribute("midRate");
// //                         String spreadStr = quoteElement.getAttribute("spread");
// //
// //                         terms.add(term);
// //
// //                         // Parse midRate and spread as doubles, handle possible NumberFormatExceptions
// //                         try {
// //                             double midRate = Double.parseDouble(midRateStr);
// //                             double spread = Double.parseDouble(spreadStr);
// //
// //                             midRates.add(midRate);
// //                             spreads.add(spread);
// //                         } catch (NumberFormatException e) {
// //                             e.printStackTrace();
// //                         }
// //                     }
// //                 }
// //             }
// //
// //             // Set terms, midRates, and spreads in the SwapRate object
// //             swapRate.setTerms(terms);
// //             swapRate.setMidRates(midRates);
// //             swapRate.setSpreads(spreads);
// //
// //         } catch (Exception e) {
// //             e.printStackTrace();
// //         }
// //
// //         return swapRate;
// //     }
// //
// // }
//
// import javax.xml.parsers.DocumentBuilder;
// import javax.xml.parsers.DocumentBuilderFactory;
// import org.w3c.dom.Document;
// import org.w3c.dom.Element;
// import org.w3c.dom.NodeList;
// import java.util.ArrayList;
// import java.util.List;
// import org.w3c.dom.Node;
//
// @Service
// public class XDSConnectionService {
//
//     private final RestTemplate restTemplate;
//
//     @Autowired
//     public XDSConnectionService(RestTemplate restTemplate) {
//         this.restTemplate = restTemplate;
//     }
//
//     public List<SwapRate> getSwapRatesFromXds(Map<String, String> dataMap, String username, String password, String date) {
//         List<SwapRate> swapRatesList = new ArrayList<>();
//         String baseUrl = "https://xds-int.systems.uk.hsbc/api/v1/documents/";
//
//         // Loop through the dataMap to process each entry
//         for (Map.Entry<String, String> entry : dataMap.entrySet()) {
//             String key = entry.getKey();
//             String value = entry.getValue();
//
//             // Check if the key starts with "YieldCurve"
//             if (key.startsWith("YieldCurve")) {
//                 // Form the first URL (SwapCurve URL)
//                 String url1 = baseUrl + value.replace("${DATE}", date);
//
//                 // Make the first request to get SwapCurve data
//                 String swapCurveResponse = getDataFromXds(url1, username, password);
//
//                 // Parse the response to extract a list of assetNames
//                 List<String> assetNames = parseAssetNamesFromResponse(swapCurveResponse);
//
//                 // Loop through each assetName and generate the second URL
//                 for (String assetName : assetNames) {
//                     // Form the second URL (SwapRates URL)
//                     String url2 = url1.replace("SwapCurve", "SwapRates").replace("MSSEOD", assetName + "/MSSEOD");
//
//                     // Make the second request to get SwapRates data
//                     String swapRatesResponse = getDataFromXds(url2, username, password);
//
//                     // Parse the SwapRates response and store data in SwapRate class
//                     SwapRate swapRate = parseSwapRatesFromResponse(swapRatesResponse);
//                     swapRatesList.add(swapRate);
//                 }
//             }
//         }
//
//         return swapRatesList;
//     }
//
//     private String getDataFromXds(String url, String username, String password) {
//         HttpHeaders headers = new HttpHeaders();
//         String auth = username + ":" + password;
//         byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
//         String authHeader = "Basic " + new String(encodedAuth);
//         headers.set("Authorization", authHeader);
//         HttpEntity<String> entity = new HttpEntity<>(headers);
//
//         // Make the GET request
//         ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//         return response.getBody();
//     }
//
//     // Update to return List<String> for multiple asset names
//     private List<String> parseAssetNamesFromResponse(String swapCurveResponse) {
//         List<String> assetNames = new ArrayList<>();
//         try {
//             // Parse the response string into an XML Document
//             DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//             DocumentBuilder builder = factory.newDocumentBuilder();
//             Document document = builder.parse(new ByteArrayInputStream(swapCurveResponse.getBytes()));
//
//             // Normalize the XML structure
//             document.getDocumentElement().normalize();
//
//             // Locate all SwapRates elements in the XML
//             NodeList nodeList = document.getElementsByTagName("SwapRates");
//             for (int i = 0; i < nodeList.getLength(); i++) {
//                 Element swapRatesElement = (Element) nodeList.item(i);
//
//                 // Extract the assetName attribute and add it to the list
//                 String assetName = swapRatesElement.getAttribute("assetName");
//                 if (!assetName.isEmpty()) {
//                     assetNames.add(assetName);
//                 }
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//
//         // Return all found asset names
//         return assetNames;
//     }
//
//     // Method remains the same as before
//     private SwapRate parseSwapRatesFromResponse(String swapRatesResponse) {
//         SwapRate swapRate = new SwapRate();
//         List<String> terms = new ArrayList<>();
//         List<Double> midRates = new ArrayList<>();
//         List<Double> spreads = new ArrayList<>();
//
//         try {
//             // Parse the response string into an XML Document
//             DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//             DocumentBuilder builder = factory.newDocumentBuilder();
//             Document document = builder.parse(new ByteArrayInputStream(swapRatesResponse.getBytes()));
//
//             // Normalize the XML structure
//             document.getDocumentElement().normalize();
//
//             // Locate the SwapRates element
//             NodeList swapRatesList = document.getElementsByTagName("SwapRates");
//             if (swapRatesList.getLength() > 0) {
//                 Element swapRatesElement = (Element) swapRatesList.item(0);
//
//                 // Populate basic SwapRate fields
//                 swapRate.setLocation(swapRatesElement.getAttribute("location"));
//                 swapRate.setMarketTime(swapRatesElement.getAttribute("marketTime"));
//                 swapRate.setCurrency(swapRatesElement.getAttribute("ccy"));
//                 swapRate.setRateFixingIndex(swapRatesElement.getAttribute("rateFixingIndex"));
//                 swapRate.setIndexTerm(swapRatesElement.getAttribute("indexTerm"));
//                 swapRate.setName(swapRatesElement.getAttribute("name"));
//
//                 // Extract <Quote> elements and get terms, midRates, and spreads
//                 NodeList quoteList = swapRatesElement.getElementsByTagName("Quote");
//                 for (int i = 0; i < quoteList.getLength(); i++) {
//                     Node quoteNode = quoteList.item(i);
//                     if (quoteNode.getNodeType() == Node.ELEMENT_NODE) {
//                         Element quoteElement = (Element) quoteNode;
//
//                         // Extract term, midRate, and spread attributes
//                         String term = quoteElement.getAttribute("term");
//                         String midRateStr = quoteElement.getAttribute("midRate");
//                         String spreadStr = quoteElement.getAttribute("spread");
//
//                         terms.add(term);
//
//                         // Parse midRate and spread as doubles, handle possible NumberFormatExceptions
//                         try {
//                             double midRate = Double.parseDouble(midRateStr);
//                             double spread = Double.parseDouble(spreadStr);
//
//                             midRates.add(midRate);
//                             spreads.add(spread);
//                         } catch (NumberFormatException e) {
//                             e.printStackTrace();
//                         }
//                     }
//                 }
//             }
//
//             // Set terms, midRates, and spreads in the SwapRate object
//             swapRate.setTerms(terms);
//             swapRate.setMidRates(midRates);
//             swapRate.setSpreads(spreads);
//
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//
//         return swapRate;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Node;

@Service
public class XDSConnectionService {

    private final RestTemplate restTemplate;

    @Autowired
    public XDSConnectionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<String> getSwapRatesFromXds(Map<String, String> dataMap, String username, String password, String date) {
        List<String> formattedSwapRatesList = new ArrayList<>();
        String baseUrl = "https://xds-int.systems.uk.hsbc/api/v1/documents/";

        // Loop through the dataMap to process each entry
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            // Check if the key starts with "YieldCurve"
            if (key.startsWith("YieldCurve")) {
                // Form the first URL (SwapCurve URL)
                String url1 = baseUrl + value.replace("${DATE}", date);

                // Make the first request to get SwapCurve data
                String swapCurveResponse = getDataFromXds(url1, username, password);

                // Parse the response to extract a list of assetNames
                List<String> assetNames = parseAssetNamesFromResponse(swapCurveResponse);

                // Loop through each assetName and generate the second URL
                for (String assetName : assetNames) {
                    // Form the second URL (SwapRates URL)
                    String url2 = url1.replace("SwapCurve", "SwapRates").replace("MSSEOD", assetName + "/MSSEOD");

                    // Make the second request to get SwapRates data
                    String swapRatesResponse = getDataFromXds(url2, username, password);

                    // Parse and format the SwapRates response into the desired format
                    String formattedSwapRates = parseSwapRatesFromResponse(swapRatesResponse);
                    formattedSwapRatesList.add(formattedSwapRates); // Add formatted swap rate string to the list
                }
            }
        }

        return formattedSwapRatesList;
    }

    private String getDataFromXds(String url, String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make the GET request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    private List<String> parseAssetNamesFromResponse(String swapCurveResponse) {
        List<String> assetNames = new ArrayList<>();
        try {
            // Parse the response string into an XML Document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(swapCurveResponse.getBytes()));

            // Normalize the XML structure
            document.getDocumentElement().normalize();

            // Locate all SwapRates elements in the XML
            NodeList nodeList = document.getElementsByTagName("SwapRates");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element swapRatesElement = (Element) nodeList.item(i);

                // Extract the assetName attribute and add it to the list
                String assetName = swapRatesElement.getAttribute("assetName");
                if (!assetName.isEmpty()) {
                    assetNames.add(assetName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return assetNames;
    }

    // Updated method to format output as requested
    private String parseSwapRatesFromResponse(String swapRatesResponse) {
        List<String> swapRateEntries = new ArrayList<>();

        try {
            // Parse the response string into an XML Document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(swapRatesResponse.getBytes()));

            // Normalize the XML structure
            document.getDocumentElement().normalize();

            // Locate the SwapRates element
            NodeList swapRatesList = document.getElementsByTagName("SwapRates");
            if (swapRatesList.getLength() > 0) {
                Element swapRatesElement = (Element) swapRatesList.item(0);

                // Extract <Quote> elements and get terms, midRates, and spreads
                NodeList quoteList = swapRatesElement.getElementsByTagName("Quote");
                for (int i = 0; i < quoteList.getLength(); i++) {
                    Node quoteNode = quoteList.item(i);
                    if (quoteNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element quoteElement = (Element) quoteNode;

                        // Extract term, midRate, and spread attributes
                        String term = quoteElement.getAttribute("term");
                        String midRateStr = quoteElement.getAttribute("midRate");
                        String spreadStr = quoteElement.getAttribute("spread");

                        // Parse midRate and spread as doubles, handle possible NumberFormatExceptions
                        try {
                            double midRate = Double.parseDouble(midRateStr);
                            double spread = Double.parseDouble(spreadStr);

                            // Format each term, midRate, spread set as "[term, midRate, spread]"
                            String formattedEntry = String.format("[%s, %.5f, %.5f]", term, midRate, spread);
                            swapRateEntries.add(formattedEntry);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Join all entries with commas and return a single string
        return String.join(", ", swapRateEntries);
    }
}

//     }
// }
