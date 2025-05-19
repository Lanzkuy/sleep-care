package com.lans.sleep_care.data.source.network.dto.request.psychologist

data class PsychologistListRequest(
    val orderBy: String = "registered_year",
    val sort: String = "asc",
    val paginate: Int = 10,
    var page: Int = 1
)

fun PsychologistListRequest.toQueryMap(): Map<String, Any> {
    return mapOf(
        "order_by" to orderBy,
        "sort" to sort,
        "paginate" to paginate,
        "page" to page
    )
}
