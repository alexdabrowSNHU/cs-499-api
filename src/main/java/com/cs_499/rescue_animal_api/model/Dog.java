package com.cs_499.rescue_animal_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "dogs") // Specific table for Dog fields
@PrimaryKeyJoinColumn(name = "rescue_animal_id") // Specifies the FK column joining to rescue_animals table
public class Dog extends RescueAnimal {

    private String breed;

    // Constructor
    public Dog(String name, String breed, String gender, String age,
               String weight, String acquisitionDate, String acquisitionCountry,
               String trainingStatus, boolean reserved, String inServiceCountry) {
        super(name, gender, age, weight, acquisitionDate, acquisitionCountry, trainingStatus, reserved, inServiceCountry);
        this.breed = breed;
    }
}