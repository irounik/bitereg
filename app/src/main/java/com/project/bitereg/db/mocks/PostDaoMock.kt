package com.project.bitereg.db.mocks

import com.project.bitereg.db.PostDao
import com.project.bitereg.models.Event
import com.project.bitereg.models.Notice
import com.project.bitereg.models.Post
import kotlinx.coroutines.delay

class PostDaoMock : PostDao {

    private val posts: MutableList<Post> = mutableListOf(
        Event(
            imageUrl = "https://instagram.fixr3-1.fna.fbcdn.net/v/t51.2885-15/244350002_393052299130188_9007615357128285077_n.webp?stp=dst-jpg_e35_s720x720&_nc_ht=instagram.fixr3-1.fna.fbcdn.net&_nc_cat=100&_nc_ohc=h9lBJ95ZQQAAX-pzMiZ&edm=ALQROFkBAAAA&ccb=7-5&ig_cache_key=MjY3NzgyMDAxMTEyNTY4MzE0Mg%3D%3D.2-ccb7-5&oh=00_AT9C_8JzzIgtI0hwAxDNMIiwrT2nKLoZW5wsmQUkjjH90A&oe=63563EF9&_nc_sid=30a2ef",
            registrationLink = "https://forms.gle/yw8qs9jDzPEK2Mz16"
        ).apply {
            creatorName = "GFG BIT'D"
            creatorProfilePic =
                "https://instagram.fixr3-1.fna.fbcdn.net/v/t51.2885-19/118403741_2553417368304355_1102007459970074185_n.jpg?stp=dst-jpg_s320x320&_nc_ht=instagram.fixr3-1.fna.fbcdn.net&_nc_cat=107&_nc_ohc=iZ58vfhi4isAX-rGUiV&edm=AOQ1c0wBAAAA&ccb=7-5&oh=00_AT-35Yu8578_e6bYIBFzVP96bhqjPvo0kEvZjOanAq4VSA&oe=63555C72&_nc_sid=8fd12b"
        },
        Notice(
            title = "Induction session for Coding Society",
            noticeBody = "CodeSoc BIT Deoghar is going to organize the induction session for 2022 batch on:1st December of this month.\n\nAll the stakeholders are adviced to be present there.\n\nDr. SP\nFaculty Advisor\nCodeSoc BIT’D"
        ).apply {
            creatorName = "CodeSoc BIT'D"
            creatorProfilePic =
                "https://instagram.fixr2-1.fna.fbcdn.net/v/t51.2885-15/146067107_460430888319214_8862456676765423029_n.jpg?stp=dst-jpg_e35_s320x320&_nc_ht=instagram.fixr2-1.fna.fbcdn.net&_nc_cat=110&_nc_ohc=KF1S2x5NteEAX9ye8wg&edm=AOQ1c0wBAAAA&ccb=7-5&oh=00_AfCUhuqqhi4fryoEU55VQvZCbo6T2gF95sRgTXlutpkSqg&oe=638936BC&_nc_sid=8fd12b"
        },
        Notice(
            title = "Induction session for Coding Society",
            noticeBody = "CodeSoc BIT Deoghar is going to organize the induction session for 2022 batch on:1st December of this month.\n\nAll the stakeholders are adviced to be present there.\n\nDr. SP\nFaculty Advisor\nCodeSoc BIT’D"
        ).apply {
            creatorName = "CodeSoc BIT'D"
        },
        Notice(
            title = "Induction session for Coding Society",
            noticeBody = "CodeSoc BIT Deoghar is going to organize the induction session for 2022 batch on:1st December of this month.\n\nAll the stakeholders are adviced to be present there.\n\nDr. SP\nFaculty Advisor\nCodeSoc BIT’D"
        ).apply {
            creatorName = "CodeSoc BIT'D"
        },
    )

    override suspend fun createPost(post: Post): Result<Boolean> {
        posts.add(post)
        return Result.success(true)
    }

    override suspend fun getPosts(userId: String): Result<List<Post>> {
        delay(500)
        return Result.success(posts)
    }

}