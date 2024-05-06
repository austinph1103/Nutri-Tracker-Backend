package com.java.csncl.nutritracker;

import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;

@Tag(name = "Meals", description = "the Meals Api")
public interface TestMealsAPI {
    @Operation(
            summary = "Fetch all plants",
            description = "fetches all plant entities and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    ResponseEntity<List<Meals>> getAllMeals();

    @Operation(
            summary = "adds a plant",
            description = "Adds a plant to the list of plants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successfully added a plnt"),
            @ApiResponse(responseCode = "409", description = "duplicate plant")
    })
    ResponseEntity<Void> addMeals(@RequestBody Meals meal);
}
