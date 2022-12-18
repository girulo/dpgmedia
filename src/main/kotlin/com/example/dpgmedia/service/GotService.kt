package com.example.dpgmedia.service

import com.example.dpgmedia.model.Character
import com.example.dpgmedia.model.Continent
import com.example.dpgmedia.model.ShallowCharacter
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class GotService(
    private var restTemplate: RestTemplate

) {
    companion object {
        const val GOT_CONTINENTS_URL = "/Continents"
        const val GOT_CHARACTERS_URL = "/Characters"
        const val V2 = "/v2"
        const val V1 = "/v1"
    }

    fun getAllContinents(apiVersion: Int): List<Continent> {
        val response = restTemplate.getForEntity(generateUrl(apiVersion, GOT_CONTINENTS_URL), Array<Continent>::class.java)
        return when (response.statusCode.is2xxSuccessful) {
            true -> response.body!!.asList()
            false -> emptyList()
        }
    }

    fun getAllCharacters(apiVersion: Int): List<Character> {
        val response = restTemplate.getForEntity(generateUrl(apiVersion, GOT_CHARACTERS_URL), Array<Character>::class.java)
        return when (response.statusCode.is2xxSuccessful) {
            true -> response.body!!.asList()
            false -> emptyList()
        }
    }

    fun getAllShallowCharacters(apiVersion: Int): List<ShallowCharacter> {
        return getAllCharacters(apiVersion).map { character -> ShallowCharacter.fromCharacter(character) }.toList()

    }

    fun searchCharactersByFamilyName(apiVersion: Int, familyName: String): List<Character> {
        return getAllCharacters(apiVersion).filter { character -> character.family == familyName }.toList()
    }

    fun findCharacterById(apiVersion: Int, id: Int): Character? {
        val response = restTemplate.getForEntity(generateUrl(apiVersion, GOT_CHARACTERS_URL.plus(id)), Character::class.java)
        return when (response.statusCode.is2xxSuccessful) {
            true -> response.body!!
            false -> null
        }
    }

    fun postCharacter(apiVersion: Int, character: Character): Boolean {
        val response = restTemplate.postForEntity(generateUrl(apiVersion, GOT_CHARACTERS_URL), character, Character::class.java)
        return response.statusCode.is2xxSuccessful
    }

    private fun generateUrl(apiVersion: Int, endPoint: String): String {
        return when (apiVersion) {
            1 -> V1
            2 -> V2
            else -> V2
        }.plus(endPoint)
    }
}
