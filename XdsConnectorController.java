package com.example.xdsapp.controller;

import com.example.xdsapp.service.XdsConnectionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xds")
public class XdsConnectorController {

    private final XdsConnectionService xdsConnectionService;

    @Value("${xds.username}")
    private String username;

    @Value("${xds.password}")
    private String password;

    public XdsConnectorController(XdsConnectionService xdsConnectionService) {
        this.xdsConnectionService = xdsConnectionService;
    }

    @GetMapping("/connect")
    public ResponseEntity<String> connectToXds() {
        // Define the date to replace ${DATE} in the URLs
        String date = "20240919";
        xdsConnectionService.processYieldCurveData(date, username, password);

        return ResponseEntity.ok("XDS requests processed");
    }
}
