package com.project.bitereg.models

data class Issue(
    override var id: String? = null,
    val title: String = "",
    val description: String = "",
    val createdBy: String = "",
    var createdAt: Long = -1
) : BaseModel()