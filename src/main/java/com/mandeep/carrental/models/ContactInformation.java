package com.mandeep.carrental.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactInformation {
    private String phone;
    private String email;
    private Location address;
    private PersonalInfo personalInfo;
}
