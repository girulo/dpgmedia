package com.example.dpgmedia.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.swagger.v3.oas.annotations.media.Schema

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema (description = "Game of Thrones API Character Object")
data class Character(
    @Schema(description = "A unique number that identifies this character")
    val id: Int,
    @Schema(description = "The character's first name")
    val firstName: String?,
    @Schema(description = "The character's last name")
    val lastName: String?,
    @Schema(description = "The First + Last name of the character")
    val fullName: String?,
    @Schema(description = "The character's formal title")
    val title: String?,
    @Schema(description = "The character's family name")
    val family: String?,
    @Schema(description = "The character's picture filename")
    val image: String,
    @Schema(description = "The character's picture url")
    val imageUrl: String)

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema (description = "Game of Thrones API Character Object. Shallow version")
data class ShallowCharacter(
    @Schema(description = "A unique number that identifies this character")
    val id: Int?,
    @Schema(description = "The First + Last name of the character")
    val fullName: String?,
    @Schema(description = "The character's picture url")
    val imageUrl: String?) {
    companion object {
        fun fromCharacter(character: Character) = ShallowCharacter(character.id, character.fullName, character.imageUrl)
    }
}
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema (description = "Game of Thrones API Continent Object")
data class Continent (
    @Schema(description = "Identifier for a Continent")
    val id: Int,
    @Schema(description = "Name of a Continent")
    val name: String?)

//@Schema(description = "Model for a dealer's view of a car.")
//data class DealerCar(
//     Other fields
//    @field:Schema(
//        description = "A year when this car was made",
//        example = "2021",
//        type = "int",
//        minimum = "1900",
//        maximum = "2500"
//    )
//    val year: Int,
//     More fields
//)