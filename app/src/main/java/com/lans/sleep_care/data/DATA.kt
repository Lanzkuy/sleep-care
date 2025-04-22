package com.lans.sleep_care.data

import com.lans.sleep_care.domain.model.DiaryAnswer

object DATA {
    var savedAnswers: Map<Pair<String, Int>, DiaryAnswer> = mutableMapOf()
}