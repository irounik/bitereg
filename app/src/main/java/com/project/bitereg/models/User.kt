package com.project.bitereg.models

data class User(
    override var id: String? = null,
    val name: String = "",
    val email: String = "",
    var details: UserDetails? = null
) : BaseModel()