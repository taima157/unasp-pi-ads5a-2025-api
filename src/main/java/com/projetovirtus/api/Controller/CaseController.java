package com.projetovirtus.api.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetovirtus.api.Data.CaseData;
import com.projetovirtus.api.Services.CaseService;

@RestController
@RequestMapping("/case")
public class CaseController {

    @Autowired
    private CaseService caseService;

    @GetMapping
    public ResponseEntity<?> listAllCases() {
        CaseData[] listOfCases = caseService.getAllCases();
        return ResponseEntity.status(HttpStatus.OK).body(listOfCases);
    }

    @GetMapping("/{caseId}")
    public ResponseEntity<?> getCaseById(@PathVariable Integer caseId) {
        CaseData caseData = caseService.getCaseById(caseId);
        return ResponseEntity.status(HttpStatus.OK).body(caseData);
    }
}
