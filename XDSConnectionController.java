// import javax.xml.parsers.DocumentBuilder;
// import javax.xml.parsers.DocumentBuilderFactory;
// import org.w3c.dom.Document;
// import org.w3c.dom.Element;
// import org.w3c.dom.NodeList;
// import java.util.ArrayList;
// import java.util.List;
// import org.w3c.dom.Node;
//
// @RestController
// @RequestMapping("/api") // Optional base path for better organization
// public class XDSConnectionController {
//
//     private final XDSConnectionService xdsConnectionService;
//     private final XMLParserService xmlParserService;
//
//     @Value("${xds.username}")
//     private String username;
//
//     @Value("${xds.password}")
//     private String password;
//
//     @Autowired
//     public XDSConnectionController(XDSConnectionService xdsConnectionService, XMLParserService xmlParserService) {
//         this.xdsConnectionService = xdsConnectionService;
//         this.xmlParserService = xmlParserService;
//     }
//
//     @GetMapping("/swap-rates")
// public ResponseEntity<List<SwapRate>> getSwapRates(
//     @RequestParam Map<String, String> dataMap,
//     @RequestParam String username,
//     @RequestParam String password,
//     @RequestParam String date) {
//         try {
//             // Get the parsed dataMap from XMLParserService
//             Map<String, String> dataMap = xmlParserService.parseXMLFromResources(fileName);
//
//             // Pass the dataMap to XDSConnectionService to process URLs and get SwapRates data
//             List<SwapRate> swapRatesList = xdsConnectionService.getSwapRatesFromXds(dataMap, username, password, date);
//
//             // Check if swapRatesList is empty
//             if (swapRatesList.isEmpty()) {
//                 return ResponseEntity.noContent().build(); // Return 204 No Content
//             }
//
//             // Return the list of SwapRate data with a 200 OK status
//             return ResponseEntity.ok(swapRatesList);
//         } catch (Exception e) {
//             // Log the exception (optional)
//             // e.g., logger.error("Error fetching swap rates", e);
//
//             // Return a 400 Bad Request response
//             return ResponseEntity.badRequest().body(null); // or return a custom error message
//         }
//     }
// }


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import org.w3c.dom.Node;

@RestController
@RequestMapping("/api") // Optional base path for better organization
public class XDSConnectionController {

    private final XDSConnectionService xdsConnectionService;
    private final XMLParserService xmlParserService;

    @Value("${xds.username}")
    private String username;

    @Value("${xds.password}")
    private String password;

    @Autowired
    public XDSConnectionController(XDSConnectionService xdsConnectionService, XMLParserService xmlParserService) {
        this.xdsConnectionService = xdsConnectionService;
        this.xmlParserService = xmlParserService;
    }

    @GetMapping("/swap-rates")
    public ResponseEntity<List<SwapRate>> getSwapRates(
            @RequestParam String fileName,
            @RequestParam String date) {
        try {
            // Get the parsed dataMap from XMLParserService
            Map<String, String> dataMap = xmlParserService.parseXMLFromResources(fileName);

            // Pass the dataMap to XDSConnectionService to process URLs and get SwapRates data
            List<SwapRate> swapRatesList = xdsConnectionService.getSwapRatesFromXds(dataMap, username, password, date);

            // Check if swapRatesList is empty
            if (swapRatesList.isEmpty()) {
                return ResponseEntity.noContent().build(); // Return 204 No Content
            }

            // Return the list of SwapRate data with a 200 OK status
            return ResponseEntity.ok(swapRatesList);
        } catch (Exception e) {
            // Log the exception (optional)
            // e.g., logger.error("Error fetching swap rates", e);

            // Return a 400 Bad Request response
            return ResponseEntity.badRequest().body(null); // or return a custom error message
        }
    }
}
