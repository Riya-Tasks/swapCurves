import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

private String parseAssetNameFromResponse(String swapCurveResponse) {
    try {
        // Parse the response string into an XML Document
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new ByteArrayInputStream(swapCurveResponse.getBytes()));

        // Normalize the XML structure
        document.getDocumentElement().normalize();

        // Locate the SwapRates element in the XML
        NodeList nodeList = document.getElementsByTagName("SwapRates");
        if (nodeList.getLength() > 0) {
            Element swapRatesElement = (Element) nodeList.item(0);

            // Extract the assetName attribute
            return swapRatesElement.getAttribute("assetName");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    // If assetName was not found, return an empty string or handle accordingly
    return "";
}
