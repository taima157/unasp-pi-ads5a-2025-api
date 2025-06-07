package com.projetovirtus.api.Models;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "post")
@Data
public class PostModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private long Id;

    @Column
    @JsonProperty(value = "user_id")
    private Long postOwner;

    @Column
    @JsonProperty("caseId")
    private Integer caseId;

    @Column
    @JsonProperty("title")
    private String title;

    @Column(columnDefinition = "LONGTEXT")
    @Lob
    @JsonProperty("description")
    private String description;

    @Column
    @JsonProperty("professionalNeeded")
    private Boolean professionalNeeded;

    @Column
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CommentModel> comments;

    @OneToOne
    @JoinColumn(name = "commentary_id")
    private CommentModel solution;

}
