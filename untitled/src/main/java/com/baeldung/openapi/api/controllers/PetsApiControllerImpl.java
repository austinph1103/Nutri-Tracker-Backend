package com.baeldung.openapi.api.controllers;

import com.baeldung.openapi.api.PetsApiDelegate;
import com.baeldung.openapi.model.Pet;
import com.google.common.collect.ImmutableList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetsApiControllerImpl implements PetsApiDelegate {
    @Override
    public ResponseEntity<Void> createPets() {
        System.out.println("hahahahaha");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Pet>> listPets(Integer limit) {
        return ResponseEntity.of(Optional.of(ImmutableList.of(new Pet(1L, "dog"))));

    }

    @Override
    public ResponseEntity<Pet> showPetById(String petId) {
//        return ResponseEntity.of(Optional.of(new Pet(2L, "cat")));
        return ResponseEntity.of(Optional.of(new Pet(5L, "cow")));

    }
}
