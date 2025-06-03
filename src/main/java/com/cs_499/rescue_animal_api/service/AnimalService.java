package com.cs_499.rescue_animal_api.service;

import com.cs_499.rescue_animal_api.model.Dog;
import com.cs_499.rescue_animal_api.model.Monkey;
import com.cs_499.rescue_animal_api.repository.DogRepository;
import com.cs_499.rescue_animal_api.repository.MonkeyRepository;
import com.cs_499.rescue_animal_api.model.AnimalUploadDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final DogRepository dogRepository;
    private final MonkeyRepository monkeyRepository;

    private static final List<String> ALLOWED_MONKEY_SPECIES = Arrays.asList(
            "Capuchin", "Guenon", "Macaque", "Marmoset", "Squirrel monkey", "Tamarin");

    public List<Dog> getAllDogs() {
        return dogRepository.findAll(); // JpaRepository.findAll() directly returns List
    }

    public List<Monkey> getAllMonkeys() {
        return monkeyRepository.findAll(); // JpaRepository.findAll() directly returns List
    }

    public Dog findDogByIdOrThrow(Long id) {
        return dogRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dog not found with id: " + id));
    }

    public Monkey findMonkeyByIdOrThrow(Long id) {
        return monkeyRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Monkey not found with id: " + id));
    }

    public Dog addDog(Dog dog) {
        return dogRepository.save(dog);
    }

    public Monkey addMonkey(Monkey monkey) {
        if (!ALLOWED_MONKEY_SPECIES.stream().anyMatch(allowed -> allowed.equalsIgnoreCase(monkey.getSpecies()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Monkey species '" + monkey.getSpecies() + "' is not allowed.");
        }

        return monkeyRepository.save(monkey);
    }

    public Dog reserveDog(Long dogId) {
        Dog dog = findDogByIdOrThrow(dogId);
        if (dog.isReserved()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dog with id " + dogId + " is already reserved.");
        }
        dog.setReserved(true);
        return dogRepository.save(dog);
    }

    public Monkey reserveMonkey(Long monkeyId) {
        Monkey monkey = findMonkeyByIdOrThrow(monkeyId);
        if (monkey.isReserved()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Monkey with id " + monkeyId + " is already reserved.");
        }
        monkey.setReserved(true);
        return monkeyRepository.save(monkey);
    }

    public List<Object> getAvailableAnimals() {
        List<Object> availableAnimals = new ArrayList<>();
        List<Dog> availableDogs = dogRepository.findByTrainingStatusAndReservedFalse("in service");
        List<Monkey> availableMonkeys = monkeyRepository.findByTrainingStatusAndReservedFalse("in service");
        availableAnimals.addAll(availableDogs);
        availableAnimals.addAll(availableMonkeys);
        return availableAnimals;
    }

    @Transactional
    public void batchProcessAnimals(List<AnimalUploadDTO> animalDTOs) {
        // Null or empty check for the list
        if (animalDTOs == null || animalDTOs.isEmpty()) {
            throw new IllegalArgumentException("Animal upload list cannot be null or empty.");
        }
        // Separate lists for dogs and monkeys to save
        List<Dog> dogsToSave = new ArrayList<>();
        List<Monkey> monkeysToSave = new ArrayList<>();
        // For each data transfer object (DTO), create a new entity
        for (AnimalUploadDTO dto : animalDTOs) {

            // The ID from the DTO/CSV will be ignored
            // JPA will generate a new ID for each new entity upon saving
            if ("dog".equalsIgnoreCase(dto.getType())) {
                // Create a new Dog instance
                Dog dog = new Dog();
                // Set values for the dog attributes
                dog.setName(dto.getName());
                dog.setGender(dto.getGender());
                dog.setAge(dto.getAge());
                dog.setWeight(dto.getWeight());
                dog.setAcquisitionDate(dto.getAcquisitionDate());
                dog.setAcquisitionCountry(dto.getAcquisitionCountry());
                dog.setTrainingStatus(dto.getTrainingStatus());
                dog.setReserved(dto.getReserved() != null ? dto.getReserved() : false);
                dog.setInServiceCountry(dto.getInServiceCountry());
                // Dog-specific
                dog.setBreed(dto.getBreed());

                dogsToSave.add(dog);
                // elif type is monkey
            } else if ("monkey".equalsIgnoreCase(dto.getType())) {
                // Create a new Monkey instance
                Monkey monkey = new Monkey();
                // Set values for the monkey attributes
                monkey.setName(dto.getName());
                monkey.setGender(dto.getGender());
                monkey.setAge(dto.getAge());
                monkey.setWeight(dto.getWeight());
                monkey.setAcquisitionDate(dto.getAcquisitionDate());
                monkey.setAcquisitionCountry(dto.getAcquisitionCountry());
                monkey.setTrainingStatus(dto.getTrainingStatus());
                monkey.setReserved(dto.getReserved() != null ? dto.getReserved() : false);
                monkey.setInServiceCountry(dto.getInServiceCountry());
                // Monkey-specific
                monkey.setSpecies(dto.getSpecies());
                monkey.setTailLength(dto.getTailLength());
                monkey.setHeight(dto.getHeight());
                monkey.setBodyLength(dto.getBodyLength());

                // Save the monkey to the list
                monkeysToSave.add(monkey);
            }
        }

        // Save all new dogs and monkeys.
        if (!dogsToSave.isEmpty()) {
            dogRepository.saveAll(dogsToSave);
        }
        if (!monkeysToSave.isEmpty()) {
            monkeyRepository.saveAll(monkeysToSave);
        }
    }

    // Using Insertion Sort to sort dogs by name
    public List<Dog> getDogsSortedByInsertion(List<Dog> dogs) {
        // Loop through starting from the second element
        for (int i = 1; i < dogs.size(); i++) {
            int j = i;
            // While the current dog is less than the previous dog by name
            while (j > 0 && dogs.get(j - 1).getName().compareToIgnoreCase(dogs.get(j).getName()) > 0) {
                // Swap the dogs
                Collections.swap(dogs, j, j - 1);
                // Move to the previous dog
                j--;
            }
        }
        return dogs;
    }

    public List<Dog> getDogsSortedByJava(List<Dog> dogs) {
        // sort the list using Java's built-in sorting
        // Comparator to sort by name ignoring case
        dogs.sort(Comparator.comparing(d -> d.getName().toLowerCase()));
        return dogs;
    }

}
