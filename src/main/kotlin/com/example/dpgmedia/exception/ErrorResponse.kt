package com.example.dpgmedia.exception

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty


class ErrorResponse @JsonCreator constructor(
    @JsonProperty("errorId") var errorId: Int,
    @JsonProperty("errorMessage") errorMessage: String = "Internal server error. Check the logs for details."
)