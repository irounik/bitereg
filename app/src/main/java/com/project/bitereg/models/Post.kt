package com.project.bitereg.models

data class Post(
    override var id: String? = null,
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val createdBy: String = "",
    var createdAt: Long = -1,
    val userProfileImgUrl: String = "https://instagram.fixr3-1.fna.fbcdn.net/v/t51.2885-19/118403741_2553417368304355_1102007459970074185_n.jpg?stp=dst-jpg_s320x320&_nc_ht=instagram.fixr3-1.fna.fbcdn.net&_nc_cat=107&_nc_ohc=iZ58vfhi4isAX-rGUiV&edm=AOQ1c0wBAAAA&ccb=7-5&oh=00_AT-35Yu8578_e6bYIBFzVP96bhqjPvo0kEvZjOanAq4VSA&oe=63555C72&_nc_sid=8fd12b"
) : BaseModel()
