package com.example.minesweeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.minesweeper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.clearBtn.setOnClickListener() {
            binding.mineSweeperView.resetGame()
        }
    }

    public fun isFlagModeOn(): Boolean {
        return binding.flagMode.isChecked
    }
    public fun setWinStatus(statusID: Int) {
        if (statusID == 0) binding.clearBtn.setImageResource(R.drawable.gameinplay)
        else if (statusID == 1) binding.clearBtn.setImageResource(R.drawable.gamelost)
        else if (statusID == 2) binding.clearBtn.setImageResource(R.drawable.gamewon)
    }
}