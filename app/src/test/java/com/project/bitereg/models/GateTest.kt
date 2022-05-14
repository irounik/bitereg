package com.project.bitereg.models

import com.google.gson.Gson
import junit.framework.TestCase
import org.junit.Test

class GateTest : TestCase() {

    @Test
    fun testGate() {
        val gate = Gate(
            "gateId",
            "gateName",
            10.0f,
            10.2f
        )
        val gateJson = Gson().toJson(gate).toString()
        println(gateJson)
        assertTrue(1 + 1 == 2)
    }
}