package com.ansimtouchserver.domain.taxi.dto.reqeust

data class RequestTaxiRequest (
    val wardUserId: Long,
    val latitude: Double,
    val longitude: Double
) {}