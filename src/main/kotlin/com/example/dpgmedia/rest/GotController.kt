package com.example.dpgmedia.rest

import com.example.dpgmedia.model.Character
import com.example.dpgmedia.model.Continent
import com.example.dpgmedia.model.ShallowCharacter
import com.example.dpgmedia.service.GotService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/{version}")
class GotController(
    private var gotService: GotService,
) {

    @Operation(summary = "Returns all the continents", description = "Returns 200 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful Operation"),
            ApiResponse(responseCode = "500", description = "Any internal server error"),
        ]
    )
    @GetMapping("/continents", produces = ["application/json"])
    fun getAllContinents(@PathVariable version: Int): ResponseEntity<List<Continent>> {

        val allContinents = gotService.getAllContinents(version)
        return ResponseEntity.ok(allContinents)
    }

    @Operation(summary = "Returns all the characters", description = "Returns 200 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful Operation"),
            ApiResponse(responseCode = "500", description = "Any internal server error"),
        ]
    )
    @GetMapping("/characters", produces = ["application/json"])
    fun getAllCharacters(@PathVariable version: Int): ResponseEntity<List<Character>> {

        val allCharacters = gotService.getAllCharacters(version)
        return ResponseEntity.ok(allCharacters)
    }

    @Operation(summary = "Posts a new character", description = "Returns 200 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful Operation"),
            ApiResponse(responseCode = "500", description = "Any internal server error"),
        ]
    )
    @PostMapping("/characters", consumes = ["application/json"])
    fun postCharacter(@PathVariable version: Int, @RequestBody character: Character): ResponseEntity<Boolean> {

        val success = gotService.postCharacter(version, character)
        return if (success)
            ResponseEntity.ok(success)
        else
            ResponseEntity.internalServerError().build()
    }

    @Operation(summary = "Returns a character that is specified by its id", description = "Returns 200 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful Operation"),
            ApiResponse(responseCode = "401", description = "If the character does not exist"),
            ApiResponse(responseCode = "500", description = "Any internal server error"),
        ]
    )
    @GetMapping("/characters/{id}", produces = ["application/json"])
    fun findCharacterById(@PathVariable version: Int, @PathVariable id: Int): ResponseEntity<Character> {

        val character = gotService.findCharacterById(version, id)
        return ResponseEntity.ok(character)
    }

    @Operation(summary = "Returns all the characters in a shallow verison of them", description = "Returns 200 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful Operation"),
            ApiResponse(responseCode = "500", description = "Any internal server error"),
        ]
    )
    @GetMapping("/shallow/characters", produces = ["application/json"])
    fun getAllShallowCharacters(@PathVariable version: Int): ResponseEntity<List<ShallowCharacter>> {

        val allShallowCharacters = gotService.getAllShallowCharacters(version)
        return ResponseEntity.ok(allShallowCharacters)
    }

    @Operation(summary = "Returns all the characters that matches the search parameter family name", description = "Returns 200 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful Operation"),
            ApiResponse(responseCode = "500", description = "Any internal server error"),
        ]
    )
    @GetMapping("/characters/search", produces = ["application/json"])
    fun searchCharactersByFamilyName(@PathVariable version: Int,
                                     @RequestParam @Parameter(description = "The family name of a character") familyName: String): ResponseEntity<List<Character>> {

        val charactersByFamilyName = gotService.searchCharactersByFamilyName(version, familyName)
        return ResponseEntity.ok(charactersByFamilyName)
    }

}