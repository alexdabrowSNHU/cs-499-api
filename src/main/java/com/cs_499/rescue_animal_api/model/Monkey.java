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
@Table(name = "monkeys") // Specific table for Monkey fields
@PrimaryKeyJoinColumn(name = "rescue_animal_id") // Specifies the FK column joining to rescue_animals table
public class Monkey extends RescueAnimal {

    private String species;
    private String tailLength;
    private String height;
    private String bodyLength;

    // Constructor
    public Monkey(String name, String species, String gender, String age,
                  String weight, String acquisitionDate, String acquisitionCountry,
                  String trainingStatus, boolean reserved, String inServiceCountry,
                  String tailLength, String height, String bodyLength) {
        super(name, gender, age, weight, acquisitionDate, acquisitionCountry, trainingStatus, reserved, inServiceCountry);
        this.species = species;
        this.tailLength = tailLength;
        this.height = height;
        this.bodyLength = bodyLength;
    }
}