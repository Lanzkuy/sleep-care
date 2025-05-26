package com.lans.sleep_care.domain.model.logbook

data class LogbookQuestion(
    val id: Int = 0,
    val question: String = "",
    val isParent: Int = 0,
    val parentId: Int = -1,
    val type: String = "",
    val recordType: String = "",
    val note: String = "",
    val isDeleted: Boolean = false
)
