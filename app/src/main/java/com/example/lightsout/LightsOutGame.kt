package com.example.lightsout

import android.content.Context
import kotlin.random.Random

class LightsOutGame {

    private var lights = Array(NUM_LIGHTS) { Random.nextInt(0, 2) }
    private var state: State = State.NEW_GAME
    private var context: Context

    companion object {
        const val NUM_LIGHTS = 7
    }

    enum class State {
        NEW_GAME,
        SOLVING,
        WIN
    }

    constructor(context: Context) {
        this.context = context
    }

    init {
        resetGame()
    }

    fun resetGame() {
        lights = Array(NUM_LIGHTS) { Random.nextInt(0, 2) }
        state = State.NEW_GAME
    }

    fun numAtPosition(index: Int): Int {
        return lights[index]
    }

    fun changeLightAt(index: Int) {
        if (state != State.WIN) {
            if (index in 1 until NUM_LIGHTS) {
                lights[index - 1] = lights[index - 1].xor(1)
            }
            if (index in 0 until (NUM_LIGHTS - 1)) {
                lights[index + 1] = lights[index + 1].xor(1)
            }
            lights[index] = lights[index].xor(1)
            state = State.SOLVING
            gameWin()
        }
    }

    private fun gameWin(): Boolean {
        for (i in 1 until NUM_LIGHTS) {
            if (lights[i] !=  lights[i - 1]) {
                return false
            }
        }
        state = State.WIN
        return true
    }

    fun getGameState (): String {
        return when (state) {
            State.NEW_GAME -> "NEW GAME"
            State.SOLVING -> "SOLVING"
            State.WIN -> "WIN"
        }
    }

    fun setLights(lightsArray: IntArray?) {
        lights = lightsArray!!.toTypedArray()
    }

    fun setState(stateString: String?) {
        state = when(stateString) {
            "NEW GAME" -> State.NEW_GAME
            "SOLVING" -> State.SOLVING
            "WIN" -> State.WIN
            else -> State.NEW_GAME
        }
    }
}