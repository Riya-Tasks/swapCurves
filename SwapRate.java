// public class SwapRate {
//     private String location;
//     private String marketTime;
//     private String currency;
//     private String rateFixingIndex;
//     private String indexTerm;
//     private String name;
//     private List<String> terms;
//     private List<Double> midRates;
//     private List<Double> spreads;
//
//     // Constructors, getters, setters
// }


import java.util.List;

public class SwapRate {
    private String location;
    private String marketTime;
    private String currency;
    private String rateFixingIndex;
    private String indexTerm;
    private String name;
    private List<String> terms;
    private List<Double> midRates;
    private List<Double> spreads;

    // Constructors
    public SwapRate() {}

    public SwapRate(String location, String marketTime, String currency, String rateFixingIndex,
                    String indexTerm, String name, List<String> terms, List<Double> midRates, List<Double> spreads) {
        this.location = location;
        this.marketTime = marketTime;
        this.currency = currency;
        this.rateFixingIndex = rateFixingIndex;
        this.indexTerm = indexTerm;
        this.name = name;
        this.terms = terms;
        this.midRates = midRates;
        this.spreads = spreads;
    }

    // Getters and Setters
    // ...

    public String getFormattedTermsMidRatesSpreads() {
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
        return formattedString.toString();
    }
}
