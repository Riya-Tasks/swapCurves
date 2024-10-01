@Service
public class XDSConnectionService {

    private final RestTemplate restTemplate;

    @Autowired
    public XDSConnectionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<SwapRate> getSwapRatesFromXds(Map<String, String> dataMap, String username, String password, String date) {
        List<SwapRate> swapRatesList = new ArrayList<>();
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

                // Parse the response to extract assetName
                String assetName = parseAssetNameFromResponse(swapCurveResponse);

                // Form the second URL (SwapRates URL)
                String url2 = url1.replace("SwapCurve", "SwapRates").replace("MSSEOD", assetName + "/MSSEOD");

                // Make the second request to get SwapRates data
                String swapRatesResponse = getDataFromXds(url2, username, password);

                // Parse the SwapRates response and store data in SwapRate class
                SwapRate swapRate = parseSwapRatesFromResponse(swapRatesResponse);
                swapRatesList.add(swapRate);
            }
        }

        return swapRatesList;
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

    private String parseAssetNameFromResponse(String swapCurveResponse) {
        // Logic to parse the XML response and extract the assetName
        // For example, use an XML parser to find the 'assetName' element
        return ""; // Return the parsed assetName
    }

    private SwapRate parseSwapRatesFromResponse(String swapRatesResponse) {
        // Logic to parse the SwapRates response and populate a SwapRate object
        // This includes extracting terms, midRates, spreads, etc.
        SwapRate swapRate = new SwapRate();
        // Populate SwapRate object with parsed values
        return swapRate;
    }
}
