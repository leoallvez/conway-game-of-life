package io.github.leoallvez.conway.gol

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    //TODO: use data binding
    private lateinit var gameOfLifeView: GameOfLifeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameOfLifeView = findViewById(R.id.game_of_life)
    }

    override fun onResume() {
        super.onResume()
        gameOfLifeView.start()
    }

    override fun onPause() {
        super.onPause()
        gameOfLifeView.stop()
    }
}