package com.project.bitereg.utils

import java.util.*

object DataUtils {

    private val COURSES = listOf(
        "BTech",
        "BBA",
        "BCA"
    )

    private val BRANCHES = listOf(
        "CSE",
        "ECE",
        "EEE",
        "Mechanical",
        "Civil",
        "Production",
        "Other"
    )

    private val GENDERS = listOf("Male", "Female", "Other")

    fun getCourses(): List<String> = COURSES

    fun getBranches(): List<String> = BRANCHES

    fun getBatches(): List<String> {
        val batches = mutableListOf<String>()
        var currentYear = Calendar.getInstance().get(Calendar.YEAR)
        repeat(5) {
            batches.add("$currentYear")
            currentYear--
        }
        return batches
    }

    fun getGender(): List<String> = GENDERS

}