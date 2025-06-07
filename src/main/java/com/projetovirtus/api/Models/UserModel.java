package com.projetovirtus.api.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;


import java.util.Date;

@Entity
@Table(name = "users")
@Data
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column
    @JsonProperty("firstName")
    private String firstName;

    @Column
    @JsonProperty("lastName")
    private String lastName;

    @Column
    @JsonProperty("email")
    private String email;

    @Column
    @JsonProperty("password")
    private String password;

    @Column
    @JsonProperty("gender")
    private Integer gender;

    @Column
    @JsonProperty("birth")
    private Date birth;

    @Column
    @JsonProperty("isProfessional")
    private Boolean isProfessional;

    @Column
    @JsonProperty("actuationArea")
    private String actuationArea;

    @Column
    @JsonProperty("OABCode")
    private String OABCode;

    @Column
    @JsonProperty("phoneNumber")
    private String phoneNumber;

}
