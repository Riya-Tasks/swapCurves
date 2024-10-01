@RestController
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

    @GetMapping("/connect")
    public ResponseEntity<List<SwapRate>> connectToXds(@RequestParam String fileName, @RequestParam String date) {
        // Get the parsed dataMap from XMLParserService
        Map<String, String> dataMap = xmlParserService.parseXMLFromResources(fileName);

        // Pass the dataMap to XDSConnectionService to process URLs and get SwapRates data
        List<SwapRate> swapRatesList = xdsConnectionService.getSwapRatesFromXds(dataMap, username, password, date);

        // Return the list of SwapRate data
        return ResponseEntity.ok(swapRatesList);
    }
}
