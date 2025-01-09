package com.ansimtouchserver.domain.taxi.dto.response

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoGeoResponse(
    val meta: Meta,
    val documents: List<Document>
)

data class Meta(
    val total_count: Int
)

data class Document(
    val road_address: RoadAddress? = null,  // null 값 처리
    val address: Address? = null           // null 값 처리
)

data class RoadAddress(
    val address_name: String,
    val region_1depth_name: String,
    val region_2depth_name: String,
    val region_3depth_name: String,
    val road_name: String,
    val underground_yn: String,
    val main_building_no: String,
    val sub_building_no: String? = null,  // null 값 처리
    val building_name: String? = null,    // null 값 처리
    val zone_no: String
)

data class Address(
    val address_name: String,
    val region_1depth_name: String,
    val region_2depth_name: String,
    val region_3depth_name: String,
    val mountain_yn: String,
    val main_address_no: String,
    val sub_address_no: String? = null,  // null 값 처리
    val zip_code: String? = null         // null 값 처리
)
