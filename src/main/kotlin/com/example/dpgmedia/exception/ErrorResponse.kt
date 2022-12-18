package com.example.dpgmedia.exception

import com.fasterxml.jackson.annotation.JsonCreator
import io.swagger.v3.oas.annotations.media.Schema

enum class ErrorId(val errorId: Int, val httpStatus: Int) {
    ILLEGAL_STATE(400, 400),
    BAD_REQUEST(401, 400),
    NOT_FOUND(404, 404),
    DEFAULT(500, 500);
}
@Schema(description = "Custom Error object to represent the Errors in a nice and simple way.")
class ErrorResponse @JsonCreator constructor(
    @Schema(description = "A unique number that identifies this error")
    var errorId: Int,
    @Schema(description = "The error message that thsi error contains")
    var errorMessage: String = "Internal server error. Check the logs for details."
)