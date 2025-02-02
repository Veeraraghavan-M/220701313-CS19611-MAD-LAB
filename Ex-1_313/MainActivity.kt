package com.example.exercise1

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ex1_313.R

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var btnFontSize: Button
    private lateinit var btnFontColor: Button
    private lateinit var btnBgCOlor: Button
    private lateinit var layout: LinearLayout
    private var fontSize = 18f

    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)
        btnFontSize = findViewById(R.id.btnFontSize)
        btnFontColor = findViewById(R.id.btnFontColor)
        btnBgCOlor = findViewById(R.id.btnBgColor)
        layout = findViewById<LinearLayout?>(android.R.id.content).rootView as LinearLayout

        btnFontSize.setOnClickListener {
            fontSize += 5
            textView.textSize = fontSize
        }
        btnFontColor.setOnClickListener{
            textView.setTextColor(Color.RED)
        }
        btnBgCOlor.setBackgroundColor(Color.BLUE)
    }
}