package com.example.dpgmedia.service

import com.example.dpgmedia.model.Character
import com.example.dpgmedia.model.Continent
import com.example.dpgmedia.model.ShallowCharacter
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import org.testng.Assert.*
import org.testng.annotations.BeforeClass
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class GotServiceTest {

    private lateinit var restTemplate: RestTemplate

    private lateinit var service: GotService

    companion object {
        const val GOT_CONTINENTS_URL = "/v2/Continents"
        const val GOT_CHARACTERS_URL = "/v2/Characters"
        const val URL_SEPARATOR = "/"
        const val API_VERSION = 2
    }

    @BeforeMethod
    fun setUp() {
        restTemplate = mock {
            on { getForEntity(GOT_CONTINENTS_URL, Array<Continent>::class.java) } doReturn continentsList()
            on { getForEntity(GOT_CHARACTERS_URL, Array<Character>::class.java) } doReturn charactersList()
            on { getForEntity(GOT_CHARACTERS_URL.plus(URL_SEPARATOR).plus(1), Character::class.java) } doReturn characterResult()
            on { postForEntity(GOT_CHARACTERS_URL, createCharacterBram(), Character::class.java) } doReturn characterResult()
        }
        service = GotService(restTemplate)
    }

    @Test
    fun `When ResponseEntity is ok, get all continents should return a list of size 2`() {
        val allCharacters = service.getAllCharacters(API_VERSION)
        assertEquals(allCharacters.size, 2)
        assertTrue(allCharacters[0].javaClass == Character::class.java)
    }

    @Test
    fun `When the restTemplate response for Characters is not 2xx, then get an empty list`() {
        whenever(restTemplate.getForEntity(GOT_CHARACTERS_URL, Array<Character>::class.java)).thenReturn(wrongCharacterRequest())
        val allCharacters = service.getAllCharacters(API_VERSION)
        assertTrue(allCharacters.isEmpty())
    }

    @Test
    fun `When ResponseEntity is ok, get all continents should return a list of size 3`() {
        val allContinents = service.getAllContinents(API_VERSION)
        assertEquals(allContinents.size, 3)
        assertTrue(allContinents[0].javaClass == Continent::class.java)
    }

    @Test
    fun `When the restTemplate response for Continent is not 2xx, then get an empty list`() {
        whenever(restTemplate.getForEntity(GOT_CONTINENTS_URL, Array<Continent>::class.java)).thenReturn(wrongContinentRequest())
        val allContinents = service.getAllContinents(API_VERSION)
        assertTrue(allContinents.isEmpty())
    }

    @Test
    fun `Test that when we request for a shallow version of Characters, we actually get that class as type`() {
        val shallowCharacters = service.getAllShallowCharacters(API_VERSION)
        assertEquals(shallowCharacters.size, 2)
        assertTrue(shallowCharacters[0].javaClass == ShallowCharacter::class.java)
    }

    @Test
    fun `When we search by family name we get filtered results by family`() {
        val charactersByFamilyName = service.searchCharactersByFamilyName(API_VERSION, "MD")
        assertEquals(charactersByFamilyName.size, 1)
        assertTrue(charactersByFamilyName[0].firstName == "Hugo")
    }

    @Test
    fun `When we search by family name that does not exist for any character, then we get and empty result`() {
        val charactersByFamilyName = service.searchCharactersByFamilyName(API_VERSION, "Non existing family name")
        assertEquals( charactersByFamilyName.size,0 )
    }

    @Test
    fun `When we find by id that exist we should get 1 result`() {
        val character = service.findCharacterById(API_VERSION, 1)
        assertTrue(character!!.firstName == "Bram")
        assertTrue(character.family == "DPG")
    }

    @Test
    fun `When we post a character, we receive true if everything went well`() {
        val success = service.postCharacter(API_VERSION, createCharacterBram())
        assertTrue(success)
    }

    @Test
    fun `When we post a character, we receive false if something went wrong`() {
        whenever(restTemplate.postForEntity(GOT_CHARACTERS_URL, createCharacterBram(), Character::class.java)).thenReturn(characterResultBadRequest())
        val success = service.postCharacter(API_VERSION, createCharacterBram())
        assertFalse(success)
    }


    private fun charactersList(): ResponseEntity<Array<Character>> {
        val characters = arrayOf(
            Character(0, "Hugo", "Novajarque", "Hugo Novajarque", "Developer", "MD", "image1.jpeg", "http://dpgmedia.com/image1.jpeg"),
            Character(1, "Bram", "De Fries", "Bram De Fries", "AI", "DPG", "image2.jpeg", "http://dpgmedia.com/image2.jpeg")
        )
        return ResponseEntity.ok(characters)
    }

    private fun continentsList(): ResponseEntity<Array<Continent>> {
        val continents = arrayOf(Continent(0, "Spain"), Continent(1, "Netherlands"), Continent(2, "Denmark"))
        return ResponseEntity.ok(continents)
    }

    private fun wrongCharacterRequest(): ResponseEntity<Array<Character>> {
        return ResponseEntity.badRequest().build()
    }

    private fun wrongContinentRequest(): ResponseEntity<Array<Continent>> {
        return ResponseEntity.badRequest().build()
    }

    private fun characterResult(): ResponseEntity<Character> {
        val character = createCharacterBram()
        return ResponseEntity.ok(character)
    }

    private fun createCharacterBram(): Character {
        return Character(1, "Bram", "De Fries", "Bram De Fries", "AI", "DPG", "image2.jpeg", "http://dpgmedia.com/image2.jpeg")
    }

    private fun characterResultBadRequest() : ResponseEntity<Character> {
        return ResponseEntity.badRequest().build()
    }
}