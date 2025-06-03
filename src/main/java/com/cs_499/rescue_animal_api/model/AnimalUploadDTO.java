package com.cs_499.rescue_animal_api.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// We will use this DTO to upload new animals in the batch upload function in the AnimalController.
public class AnimalUploadDTO {
 
    private String type;
    private String name;
    private String gender;
    private String age;
    private String weight;
    private String acquisitionDate;
    private String acquisitionCountry;
    private String trainingStatus;
    private Boolean reserved;
    private String inServiceCountry;

    private String breed;

    private String species;
    private String tailLength;
    private String height;
    private String bodyLength;

}
