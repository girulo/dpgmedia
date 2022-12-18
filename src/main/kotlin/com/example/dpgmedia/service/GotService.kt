package com.example.dpgmedia.service

import com.example.dpgmedia.model.Character
import com.example.dpgmedia.model.Continent
import com.example.dpgmedia.model.ShallowCharacter
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class GotService(
    private var restTemplate: RestTemplate

) {
    private val LOG = LogManager.getLogger(GotService::class)

    companion object {
        const val GOT_CONTINENTS_URL = "/Continents"
        const val GOT_CHARACTERS_URL = "/Characters"
        const val V2 = "/v2"
        const val V1 = "/v1"
        const val URL_SEPARATOR = "/"
    }

    fun getAllContinents(apiVersion: Int): List<Continent> {
        val url = generateUrl(apiVersion, GOT_CONTINENTS_URL)
        LOG.info("Requesting all Continents from {}", url)
        val response = restTemplate.getForEntity(url, Array<Continent>::class.java)
        return when (response.statusCode.is2xxSuccessful) {
            true -> {
                LOG.info("Fetched {} Continents", response.body!!.size)
                response.body!!.asList()
            }
            false -> {
                LOG.error("The request to GOT API failed")
                emptyList()
            }
            
        }
    }

    fun getAllCharacters(apiVersion: Int): List<Character> {
        val url = generateUrl(apiVersion, GOT_CHARACTERS_URL)
        LOG.info("Requesting all Characters from {}", url)
        val response = restTemplate.getForEntity(url, Array<Character>::class.java)
        return when (response.statusCode.is2xxSuccessful) {
            true -> {
                LOG.info("Fetched {} Characters", response.body!!.size)
                response.body!!.asList()
            }
            false -> {
                LOG.error("The request to GOT API failed")
                emptyList()
            }
        }
    }

    fun getAllShallowCharacters(apiVersion: Int): List<ShallowCharacter> {
        LOG.info("Requesting a shallow version of Characters")
        return getAllCharacters(apiVersion).map { character -> ShallowCharacter.fromCharacter(character) }

    }

    fun searchCharactersByFamilyName(apiVersion: Int, familyName: String): List<Character> {
        val filteredCharacters = getAllCharacters(apiVersion).filter { character -> character.family == familyName }
        LOG.info("Found {} member for the family {}", filteredCharacters.size, familyName)
        return filteredCharacters
    }

    fun findCharacterById(apiVersion: Int, id: Int): Character? {
        val url = generateUrl(apiVersion, GOT_CHARACTERS_URL.plus(URL_SEPARATOR).plus(id))
        LOG.info("Requesting Character with id: {} from {}", id, url)
        val response = restTemplate.getForEntity(url, Character::class.java)
        return when (response.statusCode.is2xxSuccessful) {
            true -> response.body!!
            false -> null
        }
    }

    fun postCharacter(apiVersion: Int, character: Character): Boolean {
        val url = generateUrl(apiVersion, GOT_CHARACTERS_URL)
        LOG.info("Posting Character {} to {}", character, url)
        val response = restTemplate.postForEntity(url, character, Character::class.java)
        val success = response.statusCode.is2xxSuccessful
        if (success)
            LOG.info("New character posted successfully")
        else
            LOG.error("Some error happened when posting the new characters")
        return success
    }

    private fun generateUrl(apiVersion: Int, endPoint: String): String {
        return when (apiVersion) {
            1 -> V1
            2 -> V2
            else -> V2
        }.plus(endPoint)
    }
}
