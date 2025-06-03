package com.cs_499.rescue_animal_api.repository;

import com.cs_499.rescue_animal_api.model.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Extend JpaRepository, ID type is Long
@Repository
public interface DogRepository extends JpaRepository<Dog, Long> { 

    // JpaRepository provides basic CRUD operations, no need to define them explictly
    List<Dog> findByName(String name);
    List<Dog> findByBreed(String breed);
    List<Dog> findByAge(String age);
    List<Dog> findByWeight(String weight);
    List<Dog> findByAcquisitionDate(String acquisitionDate);
    List<Dog> findByAcquisitionCountry(String acquisitionCountry);
    List<Dog> findByTrainingStatus(String trainingStatus);
    List<Dog> findByReserved(boolean reserved);
    List<Dog> findByInServiceCountry(String inServiceCountry);
    List<Dog> findByTrainingStatusAndReservedFalse(String trainingStatus);
}