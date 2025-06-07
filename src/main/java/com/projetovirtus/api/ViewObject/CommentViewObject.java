package com.projetovirtus.api.ViewObject;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CommentViewObject implements Serializable {
    
    private Long id;
    private LocalDateTime createdAt;
    private String content;
    private Boolean edited;
    private UserViewObject user;

}
