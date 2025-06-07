package com.projetovirtus.api.Services;

import org.springframework.stereotype.Service;

import com.projetovirtus.api.Data.CaseData;

@Service
public class CaseService {

    private static final CaseData[] ALL_CASES = {
        new CaseData(0, "Outros"),
        new CaseData(1, "Família"),
        new CaseData(2, "Consumidor"),
        new CaseData(3, "Previdência"),
        new CaseData(4, "Trabalhista")
    };

    public CaseData[] getAllCases() {
        return ALL_CASES;
    }

    public CaseData getCaseById(Integer id) {
        for (CaseData caseData : ALL_CASES) {
            if (caseData.getCaseId().equals(id)) {
                return caseData;
            }
        }

        return ALL_CASES[0];
    }
}