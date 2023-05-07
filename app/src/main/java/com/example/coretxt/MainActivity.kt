package com.example.coretxt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnAi: Button = findViewById(R.id.btnAi)
        val intent = Intent(this, MainActivity::class.java)
         btnAi.setOnClickListener {
             startActivity(intent)
         }

        var btnExit: Button = findViewById(R.id.exit)

        btnExit.setOnClickListener {
            System.exit(-1)
        }

    }
}