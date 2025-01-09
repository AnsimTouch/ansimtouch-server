package com.ansimtouchserver.domain.taxi.service

import com.ansimtouchserver.domain.taxi.dto.reqeust.RequestTaxiRequest
import com.ansimtouchserver.domain.taxi.dto.response.KakaoGeoResponse
import com.ansimtouchserver.domain.taxi.dto.response.RequestTaxiResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.View
import org.springframework.web.util.UriComponentsBuilder
import java.security.Principal




@Service
class TaxiService(
    @Value("\${map.api.key}")
    private val apiKey: String,
    private val restTemplate: RestTemplate,
) {
    fun requestTaxiByCoords(principal: Principal, requestTaxi: RequestTaxiRequest) : RequestTaxiResponse {
        val url = UriComponentsBuilder.fromHttpUrl("https://dapi.kakao.com/v2/local/geo/coord2address.json")
            .queryParam("x", requestTaxi.latitude)
            .queryParam("y", requestTaxi.longitude)
            .queryParam("input_coord", "WGS84")
            .toUriString()

        val headers = HttpHeaders().apply {
            set("Authorization", "KakaoAK $apiKey")
        }

        val entity = HttpEntity<Any>(null, headers)

        val response = restTemplate.exchange(url, HttpMethod.GET, entity, String::class.java)

        val objectMapper = jacksonObjectMapper()

        val kakaoGeoResponse: KakaoGeoResponse = objectMapper.readValue(response.body!!)

        val roadAddressName = kakaoGeoResponse.documents.firstOrNull()?.road_address?.address_name

        val buildingName = kakaoGeoResponse.documents.firstOrNull()?.road_address?.building_name

        return RequestTaxiResponse(
            success = true,
            message = "$roadAddressName ${buildingName} 위치로 택시를 성공적으로 호출 하였습니다.",
        )
    }
}

