package com.example.dpgmedia.exception

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty


class ErrorResponse @JsonCreator constructor(
    var errorId: Int,
    var errorMessage: String = "Internal server error. Check the logs for details."
)