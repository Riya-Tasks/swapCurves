import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

private SwapRate parseSwapRatesFromResponse(String swapRatesResponse) {
    SwapRate swapRate = new SwapRate();
    List<String> terms = new ArrayList<>();
    List<Double> midRates = new ArrayList<>();
    List<Double> spreads = new ArrayList<>();

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

            // Populate basic SwapRate fields
            swapRate.setLocation(swapRatesElement.getAttribute("location"));
            swapRate.setMarketTime(swapRatesElement.getAttribute("marketTime"));
            swapRate.setCurrency(swapRatesElement.getAttribute("ccy"));
            swapRate.setRateFixingIndex(swapRatesElement.getAttribute("rateFixingIndex"));
            swapRate.setIndexTerm(swapRatesElement.getAttribute("indexTerm"));
            swapRate.setName(swapRatesElement.getAttribute("name"));

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

                    terms.add(term);

                    // Parse midRate and spread as doubles, handle possible NumberFormatExceptions
                    try {
                        double midRate = Double.parseDouble(midRateStr);
                        double spread = Double.parseDouble(spreadStr);

                        midRates.add(midRate);
                        spreads.add(spread);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // Set terms, midRates, and spreads in the SwapRate object
        swapRate.setTerms(terms);
        swapRate.setMidRates(midRates);
        swapRate.setSpreads(spreads);

    } catch (Exception e) {
        e.printStackTrace();
    }

    return swapRate;
}
