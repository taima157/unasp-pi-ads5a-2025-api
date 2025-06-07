package com.projetovirtus.api.ViewObject;

import com.projetovirtus.api.Data.CaseData;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PostViewObject implements Serializable {
    
    private Long id;
    private CaseData caseData;
    private String title;
    private String description;
    private Boolean profissionalNeeded;
    private UserViewObject user;
    private LocalDateTime createdAt;
    private Boolean solution = Boolean.FALSE;

}
