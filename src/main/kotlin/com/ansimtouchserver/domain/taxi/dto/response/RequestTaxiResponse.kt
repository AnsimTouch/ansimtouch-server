package com.ansimtouchserver.domain.taxi.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.annotation.Nullable

@JsonInclude(JsonInclude.Include.NON_NULL)
data class RequestTaxiResponse (
    val success: Boolean,
    @Nullable
    val message: String? = null,
)

