package com.project.bitereg.models

data class User(
    override var id: String? = null,
    val name: String = "",
    val email: String = "",
    var details: UserDetails? = null,
    val profilePicUrl: String = "",
    val roles: List<String> = emptyList()
) : BaseModel()