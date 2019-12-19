package com.example.lightsout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var buttons = arrayOfNulls<Button>(LightsOutGame.NUM_LIGHTS)
    private var game = LightsOutGame(this)
    private var turns = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            turns = savedInstanceState.getInt("turns")
            game.setLights(savedInstanceState.getIntArray("lightsArray"))
            game.setState(savedInstanceState.getString("state"))
            updateView()
        }

        setContentView(R.layout.activity_main)

        newGameButton?.setOnClickListener {
            game.resetGame()
            buttons.forEach { it?.setEnabled(true) }
            turns = 0
            updateView()
        }

        for (index in 0 until LightsOutGame.NUM_LIGHTS) {
            val id = resources.getIdentifier("button$index", "id", packageName)
            buttons[index] = findViewById(id)
            buttons[index]?.setOnClickListener {
                game.changeLightAt(index)
                turns++
                updateView()
                Log.d(Constants.TAG, "Pressed button at index $index")
            }

        }
        updateView()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val a = IntArray(LightsOutGame.NUM_LIGHTS)
        for (i in 0 until LightsOutGame.NUM_LIGHTS) {
            a[i] = game.numAtPosition(i)
        }
        outState.run {
            putInt("turns", turns)
            putIntArray("lightsArray", a)
            putString("state", game.getGameState())
        }
    }

    private fun updateView() {
        gameStateText?.text = when (game.getGameState()) {
            "NEW GAME" -> resources.getString(R.string.instructions)
            "SOLVING" -> resources.getQuantityString(R.plurals.message_plurals, turns, turns)
            "WIN" -> {
                buttons.forEach {
                    it?.setEnabled(false)
                }
                resources.getString(R.string.winString)
            }
            else -> {" "}
        }
        for (index in 0 until LightsOutGame.NUM_LIGHTS) {
            buttons[index]?.text = game.numAtPosition(index).toString()
        }
    }
}
