package com.cs_499.rescue_animal_api.repository;

import com.cs_499.rescue_animal_api.model.Monkey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

 // Extend JpaRepository, ID type is Long
@Repository
public interface MonkeyRepository extends JpaRepository<Monkey, Long> {

    // JpaRepository provides basic CRUD operations, no need to define them explicitly
    List<Monkey> findByName(String name);
    List<Monkey> findByGender(String gender);
    List<Monkey> findByAge(String age);
    List<Monkey> findByWeight(String weight);
    List<Monkey> findByAcquisitionDate(String acquisitionDate);
    List<Monkey> findByAcquisitionCountry(String acquisitionCountry);
    List<Monkey> findByTrainingStatus(String trainingStatus);
    List<Monkey> findByReserved(boolean reserved);
    List<Monkey> findByInServiceCountry(String inServiceCountry);
    List<Monkey> findBySpecies(String species);
    List<Monkey> findByTailLength(String tailLength);
    List<Monkey> findByHeight(String height);
    List<Monkey> findByBodyLength(String bodyLength);
    List<Monkey> findByTrainingStatusAndReservedFalse(String trainingStatus);
}