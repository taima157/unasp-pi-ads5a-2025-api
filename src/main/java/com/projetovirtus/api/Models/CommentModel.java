package com.projetovirtus.api.Models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "commentaries")
@Data
public class CommentModel {
    
    @Id
    @Column(name = "commentary_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "LONGTEXT")
    @Lob
    @JsonProperty(value = "content")
    private String content;

    @Column
    @JsonProperty(value = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "edited", columnDefinition = "boolean default false")
    @JsonProperty(value = "edited")
    private Boolean edited;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

}
