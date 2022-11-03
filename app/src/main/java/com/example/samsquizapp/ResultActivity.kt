package com.example.samsquizapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {

    var finishButton: Button ?= null
    var tvNameText : TextView ?= null
    var tvScoreText : TextView ?= null

    var mUserName: String ?= null
    var mCorrectAnswers:String ?= null
    var mTotalQuestions: String ?= null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        finishButton = findViewById(R.id.btnFinish)
        tvNameText = findViewById(R.id.tvName)
        tvScoreText = findViewById(R.id.tvScore)

        mUserName = intent.getStringExtra(Constants.USER_NAME)
        mCorrectAnswers = intent.getStringExtra(Constants.CORRECT_ANSWERS)
        mTotalQuestions = intent.getStringExtra(Constants.TOTAL_QUESTIONS)

        tvNameText?.text = mUserName
        tvScoreText?.text = "Your score is $mCorrectAnswers out of $mTotalQuestions"

        finishButton?.setOnClickListener{
            val intentRestart : Intent = Intent(this,MainActivity::class.java)
            startActivity(intentRestart)
        }




    }
}