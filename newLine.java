public void processUrlsAndConnectToXds(HashMap<String, String> keyValueMap) {
        String date = "20240919";  // Hardcoded date for this example

        // Iterate over the HashMap and find the keys that start with "YieldCurve"
        for (Map.Entry<String, String> entry : keyValueMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
