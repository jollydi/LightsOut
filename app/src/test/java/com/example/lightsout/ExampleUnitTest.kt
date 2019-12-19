package com.example.lightsout

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun arraysAreCorrect() {
        assert(true)
    }

    @Test
    fun lightsChange() {
        val game= LightsOutGame(AppCompatActivity())
        for (i in 0 until LightsOutGame.NUM_LIGHTS) {
            var num = game.numAtPosition(i)
            game.changeLightAt(i)
            assertEquals(num.xor(1), game.numAtPosition(i))
        }
    }
}
