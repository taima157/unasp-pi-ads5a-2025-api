package com.projetovirtus.api.ViewObject;

import com.projetovirtus.api.Data.CaseData;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SpceificPostViewObject implements Serializable {
    
    private Long id;
    private CaseData caseData;
    private String title;
    private String description;
    private Boolean profissionalNeeded;
    private List<CommentViewObject> comments;
    private CommentViewObject solution;
    private LocalDateTime createdAt;
    private UserViewObject postOwner;

}
