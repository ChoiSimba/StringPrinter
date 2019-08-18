package io.simba.StringPrinter.controller;

import io.simba.StringPrinter.dto.PrintCondition;
import io.simba.StringPrinter.dto.PrintResult;
import io.simba.StringPrinter.service.PrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    private PrintService printService;


    @Autowired
    MainController(PrintService printService) {
        this.printService = printService;
    }

    @GetMapping("/")
    public ResponseEntity<PrintResult> main(PrintCondition printCondition) {
        return ResponseEntity.ok(printService.request(printCondition));
    }
}
