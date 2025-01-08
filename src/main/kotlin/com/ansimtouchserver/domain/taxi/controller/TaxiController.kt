package com.ansimtouchserver.domain.taxi.controller

import com.ansimtouchserver.domain.taxi.dto.reqeust.RequestTaxiRequest
import com.ansimtouchserver.domain.taxi.dto.response.RequestTaxiResponse
import com.ansimtouchserver.domain.taxi.service.TaxiService
import com.ansimtouchserver.domain.user.dto.response.GetMeResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Tag(name = "택시 관련 API", description = "택시 관련 api")
@RestController
@RequestMapping("/taxi")
class TaxiController (
    private val taxiService: TaxiService
) {
    @Operation(summary = "택시 호출")
    @PostMapping("/request")
    fun requestTaxiByCoords(principal: Principal, @RequestBody requestTaxiRequest: RequestTaxiRequest): RequestTaxiResponse {
        return taxiService.requestTaxiByCoords(principal, requestTaxiRequest)
    }
}