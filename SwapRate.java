// public class SwapRate {
//     private String location;
//     private String marketTime;
//     private String currency;
//     private String rateFixingIndex;
//     private String indexTerm;
//     private String name;
//
//     private List<String> terms = new ArrayList<>();
//     private List<Double> midRates = new ArrayList<>();
//     private List<Double> spreads = new ArrayList<>();
//
//     // Getters and Setters
//     public String getLocation() { return location; }
//     public void setLocation(String location) { this.location = location; }
//
//     public String getMarketTime() { return marketTime; }
//     public void setMarketTime(String marketTime) { this.marketTime = marketTime; }
//
//     public String getCurrency() { return currency; }
//     public void setCurrency(String currency) { this.currency = currency; }
//
//     public String getRateFixingIndex() { return rateFixingIndex; }
//     public void setRateFixingIndex(String rateFixingIndex) { this.rateFixingIndex = rateFixingIndex; }
//
//     public String getIndexTerm() { return indexTerm; }
//     public void setIndexTerm(String indexTerm) { this.indexTerm = indexTerm; }
//
//     public String getName() { return name; }
//     public void setName(String name) { this.name = name; }
//
//     public List<String> getTerms() { return terms; }
//     public void setTerms(List<String> terms) { this.terms = terms; }
//
//     public List<Double> getMidRates() { return midRates; }
//     public void setMidRates(List<Double> midRates) { this.midRates = midRates; }
//
//     public List<Double> getSpreads() { return spreads; }
//     public void setSpreads(List<Double> spreads) { this.spreads = spreads; }
//
// @Override
// public String toString() {
//     // Prepare a formatted output for terms, midRates, and spreads
//     StringBuilder formattedOutput = new StringBuilder();
//
//     // Assuming terms, midRates, and spreads lists are of the same size
//     for (int i = 0; i < terms.size(); i++) {
//         formattedOutput.append(String.format("[%s, %.5f, %.5f]",
//             terms.get(i), midRates.get(i), spreads.get(i)));
//
//         // Add a comma and space if not the last element
//         if (i < terms.size() - 1) {
//             formattedOutput.append(", ");
//         }
//     }
//
//     // Return the full string including other values and the formatted list of terms, midRates, and spreads
//     return String.format(
//         "Location: %s, MarketTime: %s, Currency: %s, RateFixingIndex: %s, IndexTerm: %s, Name: %s, " +
//         "terms, midRates, spreads = %s",
//         location, marketTime, currency, rateFixingIndex, indexTerm, name,
//         formattedOutput.toString()
//     );
// }
//
// }


public class SwapRate {
    private String location;
    private String marketTime;
    private String currency;
    private String rateFixingIndex;
    private String indexTerm;
    private String name;

    private String termsMidRatesSpreads; // Stores terms, midRates, spreads in custom format

    // Getters and Setters
    // ...

    public String getTermsMidRatesSpreads() { return termsMidRatesSpreads; }
    public void setTermsMidRatesSpreads(String termsMidRatesSpreads) { this.termsMidRatesSpreads = termsMidRatesSpreads; }

    public void setTermsMidRatesSpreads(List<String> terms, List<Double> midRates, List<Double> spreads) {
        StringBuilder formattedString = new StringBuilder();
        for (int i = 0; i < terms.size(); i++) {
            formattedString.append("[").append(terms.get(i)).append(", ")
                           .append(midRates.get(i)).append(", ")
                           .append(spreads.get(i)).append("], ");
        }
        // Remove the last comma and space
        if (formattedString.length() > 0) {
            formattedString.setLength(formattedString.length() - 2);
        }
        this.termsMidRatesSpreads = formattedString.toString();
    }
}
