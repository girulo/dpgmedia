package com.example.dpgmedia.rest

import com.example.dpgmedia.model.Character
import com.example.dpgmedia.model.Continent
import com.example.dpgmedia.model.ShallowCharacter
import com.example.dpgmedia.service.GotService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/{version}")
class GotController(
    private var gotService: GotService,
) {

    @GetMapping("/continents")
    fun getAllContinents(@PathVariable version: Int): ResponseEntity<List<Continent>> {

        val allContinents = gotService.getAllContinents(version)
        return ResponseEntity.ok(allContinents)
    }

    @GetMapping("/characters")
    fun getAllCharacters(@PathVariable version: Int): ResponseEntity<List<Character>> {

        val allCharacters = gotService.getAllCharacters(version)
        return ResponseEntity.ok(allCharacters)
    }

    @PostMapping("/characters")
    fun postCharacter(@PathVariable version: Int, @RequestBody character: Character): ResponseEntity<Boolean> {

        val success = gotService.postCharacter(version, character)
        return if (success)
            ResponseEntity.ok(success)
        else
            ResponseEntity.internalServerError().build()
    }

    @GetMapping("/characters/{id}")
    fun findCharacterById(@PathVariable version: Int, @PathVariable id: Int): ResponseEntity<Character> {

        val character = gotService.findCharacterById(version, id)
        return ResponseEntity.ok(character)
    }

    @GetMapping("/shallow/characters")
    fun getAllShallowCharacters(@PathVariable version: Int): ResponseEntity<List<ShallowCharacter>> {

        val allShallowCharacters = gotService.getAllShallowCharacters(version)
        return ResponseEntity.ok(allShallowCharacters)
    }

    @GetMapping("/characters/search")
    fun searchCharactersByFamilyName(@PathVariable version: Int, @RequestParam(required = false) familyName: String): ResponseEntity<List<Character>> {

        val charactersByFamilyName = gotService.searchCharactersByFamilyName(version, familyName)
        return ResponseEntity.ok(charactersByFamilyName)
    }

}