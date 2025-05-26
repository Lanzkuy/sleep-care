package com.lans.sleep_care.data.source.network.dto.response.logbook

import com.lans.sleep_care.domain.model.logbook.LogbookQuestion
import com.squareup.moshi.Json

data class LogbookQuestionResponse(
    val id: Int,
    val question : String,
    @field:Json(name = "is_parent")
    val isParent: Int,
    @field:Json(name = "parent_id")
    val parentId: Int?,
    val type: String,
    @field:Json(name = "record_type")
    val recordType: String,
    val note: String,
    @field:Json(name = "deleted_at")
    val deletedAt: String?
)

fun LogbookQuestionResponse.toDomain() = LogbookQuestion(
    id = id,
    question = question,
    isParent = isParent,
    parentId = parentId ?: -1,
    type = type,
    recordType =recordType,
    note = note,
    isDeleted = deletedAt?.isNotEmpty() ?: false
)