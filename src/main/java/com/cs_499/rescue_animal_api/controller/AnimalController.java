package com.cs_499.rescue_animal_api.controller;

import com.cs_499.rescue_animal_api.model.AnimalUploadDTO;
import com.cs_499.rescue_animal_api.model.Dog;
import com.cs_499.rescue_animal_api.model.Monkey;
import com.cs_499.rescue_animal_api.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/animals")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AnimalController {

    private final AnimalService animalService;

    // GET /api/v1/animals/dogs
    @GetMapping("/dogs")
    // This returns a list of dogs, and a duration for the sorting time if a sort parameter is provided.
    public ResponseEntity<Map<String, Object>> getDogs(@RequestParam(required = false) String sort) {

        List<Dog> resultDogs = new java.util.ArrayList<>();
        long durationOfSort = 0;

        // Get all dogs from the service
        List<Dog> dogs = animalService.getAllDogs();

        // If insertion is provided as a sort parameter, sort the dogs by insertion
        if ("insertion".equalsIgnoreCase(sort)) {
            // Start the timer to measure the duration of the sort
            long start = System.currentTimeMillis();
            // Sort the dogs using insertion sort algorithm
            resultDogs = animalService.getDogsSortedByInsertion(dogs);
            // Calculate the duration of the sort
            durationOfSort = System.currentTimeMillis() - start;
            // If java is provided as a sort parameter, sort the dogs with .sort() method
        } else if ("java".equalsIgnoreCase(sort)) {
            // Start the timer to measure the duration of the sort
            long start = System.currentTimeMillis();
            // Sort the dogs using Java's built-in sort() method
            resultDogs = animalService.getDogsSortedByJava(dogs);
            // Calculate the duration of the sort
            durationOfSort = System.currentTimeMillis() - start;
        } else {
            // If no sort parameter is provided, return the unsorted list of dogs
            resultDogs = dogs;
        }

        // Create a response hashmap to include both the list of dogs and the sort duration
        Map<String, Object> response = new HashMap<>();
        response.put("dogs", resultDogs);
        response.put("sortDuration", durationOfSort);

        // Return the response entity with the list of dogs and the duration
        return ResponseEntity.ok(response);
    }

    // GET /api/v1/animals/monkeys
    @GetMapping("/monkeys")
    public List<Monkey> getAllMonkeys() {
        return animalService.getAllMonkeys();
    }

    // GET /api/v1/animals/dogs/{id}
    @GetMapping("/dogs/{id}")
    public Dog getDogById(@PathVariable Long id) {
        return animalService.findDogByIdOrThrow(id);
    }

    // GET /api/v1/animals/monkeys/{id}
    @GetMapping("/monkeys/{id}")
    public Monkey getMonkeyById(@PathVariable Long id) {
        return animalService.findMonkeyByIdOrThrow(id);
    }

    // GET /api/v1/animals/available
    @GetMapping("/available")
    public List<Object> getAvailableAnimals() {
        return animalService.getAvailableAnimals();
    }

    // POST /api/v1/animals/dogs
    @PostMapping("/dogs")
    @ResponseStatus(HttpStatus.CREATED)
    public Dog addDog(@RequestBody Dog dog) {
        return animalService.addDog(dog);
    }

    // POST /api/v1/animals/monkeys
    @PostMapping("/monkeys")
    @ResponseStatus(HttpStatus.CREATED)
    public Monkey addMonkey(@RequestBody Monkey monkey) {
        // When POSTing a new Monkey, the client should NOT send an 'id' field.
        return animalService.addMonkey(monkey);
    }

    // PUT /api/v1/animals/dogs/{id}
    @PutMapping("/dogs/{id}/reserve")
    public Dog reserveDog(@PathVariable Long id) {
        return animalService.reserveDog(id);
    }

    // PUT /api/v1/animals/monkeys/{id}/reserve
    @PutMapping("/monkeys/{id}/reserve")
    public Monkey reserveMonkey(@PathVariable Long id) {
        return animalService.reserveMonkey(id);
    }

    // POST Endpoint for Batch Upload
    @PostMapping("/batch-upload")
    // This endpoint allows a batch upload of animal objects.
    public ResponseEntity<Map<String, String>> batchUploadAnimals(@RequestBody List<AnimalUploadDTO> animals) {
        try {
            // Call the service method to process the batch upload
            animalService.batchProcessAnimals(animals);
            // Return a success response
            return ResponseEntity.ok(Map.of("message", "Upload successful."));
        } catch (IllegalArgumentException e) {
            // Handle exceptions for invalid input
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            // Catch any unexpected exceptions
            System.err.println("Batch upload error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An unexpected error occurred during batch upload."));
        }
    }
}