package com.cs_499.rescue_animal_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "rescue_animals") // Base table for parent fields
@Inheritance(strategy = InheritanceType.JOINED) // Using joined strategy
public abstract class RescueAnimal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID generated in the rescue_animals table
    private Long id;

    private String name;
    private String gender;
    private String age;
    private String weight;
    private String acquisitionDate;
    private String acquisitionCountry;
    private String trainingStatus;
    private boolean reserved;
    private String inServiceCountry;

    // Constructor to be used by subclasses for calling super()
    public RescueAnimal(String name, String gender, String age, String weight, String acquisitionDate, String acquisitionCountry, String trainingStatus, boolean reserved, String inServiceCountry) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.acquisitionDate = acquisitionDate;
        this.acquisitionCountry = acquisitionCountry;
        this.trainingStatus = trainingStatus;
        this.reserved = reserved;
        this.inServiceCountry = inServiceCountry;
    }
}