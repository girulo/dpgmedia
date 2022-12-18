package com.example.dpgmedia.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Character(
    val id: String?,
    @JsonProperty("firstName")val firstName: String?,
    val lastName: String?,
    val fullName: String?,
    val title: String?,
    val family: String?,
    val image: String,
    val imageUrl: String)

data class ShallowCharacter(
    val id: String?,
    val fullName: String?,
    val imageUrl: String) {
    companion object {
        fun fromCharacter(character: Character) = ShallowCharacter(character.id, character.fullName, character.imageUrl)
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Continent (
    val id: String,
    val name: String)