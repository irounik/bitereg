package com.project.bitereg.db.mocks

import com.project.bitereg.db.PostDao
import com.project.bitereg.models.Post
import kotlinx.coroutines.delay

class PostDaoMock : PostDao {

    private val posts: MutableList<Post> = mutableListOf(
        Post(
            title = "A Mock Post for GFG Student Chapter",
            description = "A sample text for description",
            imageUrl = "https://instagram.fixr3-1.fna.fbcdn.net/v/t51.2885-15/244350002_393052299130188_9007615357128285077_n.webp?stp=dst-jpg_e35_s720x720&_nc_ht=instagram.fixr3-1.fna.fbcdn.net&_nc_cat=100&_nc_ohc=h9lBJ95ZQQAAX-pzMiZ&edm=ALQROFkBAAAA&ccb=7-5&ig_cache_key=MjY3NzgyMDAxMTEyNTY4MzE0Mg%3D%3D.2-ccb7-5&oh=00_AT9C_8JzzIgtI0hwAxDNMIiwrT2nKLoZW5wsmQUkjjH90A&oe=63563EF9&_nc_sid=30a2ef"
        ), Post(
            title = "A Mock Post for GFG Student Chapter",
            description = "A sample text for description",
            imageUrl = "https://instagram.fixr3-1.fna.fbcdn.net/v/t51.2885-15/244350002_393052299130188_9007615357128285077_n.webp?stp=dst-jpg_e35_s720x720&_nc_ht=instagram.fixr3-1.fna.fbcdn.net&_nc_cat=100&_nc_ohc=h9lBJ95ZQQAAX-pzMiZ&edm=ALQROFkBAAAA&ccb=7-5&ig_cache_key=MjY3NzgyMDAxMTEyNTY4MzE0Mg%3D%3D.2-ccb7-5&oh=00_AT9C_8JzzIgtI0hwAxDNMIiwrT2nKLoZW5wsmQUkjjH90A&oe=63563EF9&_nc_sid=30a2ef"
        ), Post(
            title = "A Mock Post for GFG Student Chapter",
            description = "A sample text for description",
            imageUrl = "https://instagram.fixr3-1.fna.fbcdn.net/v/t51.2885-15/244350002_393052299130188_9007615357128285077_n.webp?stp=dst-jpg_e35_s720x720&_nc_ht=instagram.fixr3-1.fna.fbcdn.net&_nc_cat=100&_nc_ohc=h9lBJ95ZQQAAX-pzMiZ&edm=ALQROFkBAAAA&ccb=7-5&ig_cache_key=MjY3NzgyMDAxMTEyNTY4MzE0Mg%3D%3D.2-ccb7-5&oh=00_AT9C_8JzzIgtI0hwAxDNMIiwrT2nKLoZW5wsmQUkjjH90A&oe=63563EF9&_nc_sid=30a2ef"
        ), Post(
            title = "A Mock Post for GFG Student Chapter",
            description = "A sample text for description",
            imageUrl = "https://instagram.fixr3-1.fna.fbcdn.net/v/t51.2885-15/244350002_393052299130188_9007615357128285077_n.webp?stp=dst-jpg_e35_s720x720&_nc_ht=instagram.fixr3-1.fna.fbcdn.net&_nc_cat=100&_nc_ohc=h9lBJ95ZQQAAX-pzMiZ&edm=ALQROFkBAAAA&ccb=7-5&ig_cache_key=MjY3NzgyMDAxMTEyNTY4MzE0Mg%3D%3D.2-ccb7-5&oh=00_AT9C_8JzzIgtI0hwAxDNMIiwrT2nKLoZW5wsmQUkjjH90A&oe=63563EF9&_nc_sid=30a2ef"
        ), Post(
            title = "A Mock Post for GFG Student Chapter",
            description = "A sample text for description",
            imageUrl = "https://instagram.fixr3-1.fna.fbcdn.net/v/t51.2885-15/244350002_393052299130188_9007615357128285077_n.webp?stp=dst-jpg_e35_s720x720&_nc_ht=instagram.fixr3-1.fna.fbcdn.net&_nc_cat=100&_nc_ohc=h9lBJ95ZQQAAX-pzMiZ&edm=ALQROFkBAAAA&ccb=7-5&ig_cache_key=MjY3NzgyMDAxMTEyNTY4MzE0Mg%3D%3D.2-ccb7-5&oh=00_AT9C_8JzzIgtI0hwAxDNMIiwrT2nKLoZW5wsmQUkjjH90A&oe=63563EF9&_nc_sid=30a2ef"
        ), Post(
            title = "A Mock Post for GFG Student Chapter",
            description = "A sample text for description",
            imageUrl = "https://instagram.fixr3-1.fna.fbcdn.net/v/t51.2885-15/244350002_393052299130188_9007615357128285077_n.webp?stp=dst-jpg_e35_s720x720&_nc_ht=instagram.fixr3-1.fna.fbcdn.net&_nc_cat=100&_nc_ohc=h9lBJ95ZQQAAX-pzMiZ&edm=ALQROFkBAAAA&ccb=7-5&ig_cache_key=MjY3NzgyMDAxMTEyNTY4MzE0Mg%3D%3D.2-ccb7-5&oh=00_AT9C_8JzzIgtI0hwAxDNMIiwrT2nKLoZW5wsmQUkjjH90A&oe=63563EF9&_nc_sid=30a2ef"
        )
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