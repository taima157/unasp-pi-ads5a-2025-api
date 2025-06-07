package com.projetovirtus.api.ViewObject;

import com.projetovirtus.api.Data.GenderData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserViewObject implements Serializable {
    
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Date birth;
    private Boolean isProfessional;
    private String actuationArea;
    private String OABCode;
    private String phoneNumber;
    private GenderData genderData;

}
