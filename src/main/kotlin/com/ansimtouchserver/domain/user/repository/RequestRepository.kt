package com.ansimtouchserver.domain.user.repository

import com.ansimtouchserver.domain.user.entity.Request
import org.springframework.data.repository.CrudRepository

interface RequestRepository: CrudRepository<Request, String> {
}