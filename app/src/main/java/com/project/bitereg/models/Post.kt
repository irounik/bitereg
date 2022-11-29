package com.project.bitereg.models

open class Post(
    val type: String,
    var creatorId: String = "",
    var creatorName: String = "",
    var creatorProfilePic: String = "",
    var createdAt: Long = -1
) : BaseModel() {
    fun setCreationDetails(
        creatorId: String,
        creatorProfilePic: String,
        creatorName: String,
        createdAt: Long = System.currentTimeMillis(),
    ) {
        this.creatorId = creatorId
        this.creatorProfilePic = creatorProfilePic
        this.creatorName = creatorName
        this.createdAt = createdAt
    }
}

class Notice(
    val title: String = "",
    val noticeBody: String = ""
) : Post(type = TYPE) {
    companion object {
        const val TYPE = "notice"
    }
}

class Event(
    val imageUrl: String = "",
    val registrationLink: String = ""
) : Post(type = TYPE) {
    companion object {
        const val TYPE = "event"
    }
}

class Quote(val message: String = "") : Post(type = TYPE) {
    companion object {
        const val TYPE = "quote"
    }
}

class JobOrIntern(
    val title: String = "",
    val jobDescription: String = "",
    val registrationLink: String = "",
    val companyName: String = ""
) : Post(type = TYPE) {
    companion object {
        const val TYPE = "job|intern"
    }
}