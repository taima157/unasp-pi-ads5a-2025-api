package com.projetovirtus.api.Models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "commentaries")
@Data
public class CommentModel {
    
    @Id
    @Column(name = "commentary_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "TEXT")
    @Lob
    @JsonProperty(value = "content")
    private String content;

    @Column
    @JsonProperty(value = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "edited", columnDefinition = "boolean default false")
    @JsonProperty(value = "edited")
    private Boolean edited;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

}
