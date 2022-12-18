package com.example.dpgmedia.exception

enum class ErrorId(val errorId: Int, val httpStatus: Int) {
    ILLEGAL_STATE(400, 400),
    BAD_REQUEST(401, 400),
    NOT_FOUND(404, 404),
    DEFAULT(500, 500);

}