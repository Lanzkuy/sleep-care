package com.lans.sleep_care.data

import com.lans.sleep_care.domain.model.CommittedAction
import com.lans.sleep_care.domain.model.DiaryAnswer
import com.lans.sleep_care.domain.model.EmotionRecord
import com.lans.sleep_care.domain.model.ThoughtRecord
import com.lans.sleep_care.domain.model.ValueArea

object DATA {
    var savedAnswers: Map<Pair<String, Int>, DiaryAnswer> = mutableMapOf()
    var savedValueArea: Map<String, ValueArea> = mutableMapOf()
    var savedThoughtRecord: List<ThoughtRecord> = mutableListOf()
    var savedEmotionRecord: List<EmotionRecord> = mutableListOf()
    var savedCommitedAction: List<CommittedAction> = mutableListOf()
}