package com.example.samsquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton : Button = findViewById(R.id.startBtn)
        val textBox: EditText = findViewById(R.id.etName)

        startButton.setOnClickListener {
            if(textBox.text.isNotEmpty()){

                val intent: Intent = Intent(this,QuizQuestionsActivity::class.java)
                intent.putExtra("user_name",textBox.text.toString())
                startActivity(intent)

            }
            else{
                Toast.makeText(this,"Please enter a name",Toast.LENGTH_LONG).show()
            }
        }
    }
}