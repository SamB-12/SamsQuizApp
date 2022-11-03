package com.example.samsquizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.*
import androidx.core.content.ContextCompat

class QuizQuestionsActivity : AppCompatActivity() , View.OnClickListener {

    var mCurrentPosition : Int = 1
    var mQuestionsList : ArrayList<Question> ?= null
    var mSelectedOption : Int = 0
    var mCorrectAnswers: Int = 0

    var mUserName : String ?= null

    var progessbar : ProgressBar ?= null
    var tvProgress : TextView ?= null
    var tvQuestion : TextView ?= null
    var ivImage : ImageView ?= null

    var option1: TextView ?= null
    var option2: TextView ?= null
    var option3: TextView ?= null
    var option4: TextView ?= null
    var submit : Button ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mUserName = intent.getStringExtra("user_name")

        progessbar = findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tvProgress)
        tvQuestion = findViewById(R.id.tvQuestion)
        ivImage = findViewById(R.id.ivImage)

        option1 = findViewById(R.id.optionOne)
        option2 = findViewById(R.id.optionTwo)
        option3 = findViewById(R.id.optionThree)
        option4 = findViewById(R.id.optionFour)

        option1?.setOnClickListener(this)
        option2?.setOnClickListener(this)
        option3?.setOnClickListener(this)
        option4?.setOnClickListener(this)


        submit = findViewById(R.id.submitBtn)
        submit?.setOnClickListener(this)

        mQuestionsList = Constants.getQuestions()

        setQuestion()
    }

    private fun setQuestion() {

        val question: Question = mQuestionsList!![mCurrentPosition - 1]
        defaultOptionsView()

        ivImage?.setImageResource(question.image)

        progessbar?.progress = mCurrentPosition
        tvProgress?.text = "$mCurrentPosition/${progessbar?.max}"
        tvQuestion?.text = question.question

        option1?.text = question.optionOne
        option2?.text = question.optionTwo
        option3?.text = question.optionThree
        option4?.text = question.optionFour

        submit?.text = "SUBMIT"

        if(mCurrentPosition == mQuestionsList!!.size-1){
            submit?.text = "FINISH"
        }

    }

    private fun defaultOptionsView(){
        var options = ArrayList<TextView>()

        option1?.let {
            options.add(0, it)
        }
        option2?.let {
            options.add(1,it)
        }
        option3?.let {
            options.add(2,it)
        }
        option4?.let {
            options.add(3, it)
        }

        for(option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_options_border_ig
            )
        }
    }

    private fun selectedOptionView(tv : TextView, selectedOptionNum : Int){
        defaultOptionsView()

        mSelectedOption = selectedOptionNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.optionOne -> {
                option1?.let {
                    selectedOptionView(it,1)
                }
            }
            R.id.optionTwo -> {
                option2?.let {
                    selectedOptionView(it,2)
                }
            }
            R.id.optionThree -> {
                option3?.let {
                    selectedOptionView(it,3)
                }
            }
            R.id.optionFour -> {
                option4?.let {
                    selectedOptionView(it,4)
                }
            }
            R.id.submitBtn ->{
                if(mSelectedOption == 0){
                    mCurrentPosition+=1

                    when{
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            val intent:Intent = Intent(this,ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME,mUserName.toString())
                            intent.putExtra(Constants.TOTAL_QUESTIONS,mQuestionsList?.size.toString())
                            intent.putExtra(Constants.CORRECT_ANSWERS,mCorrectAnswers.toString())
                            startActivity(intent)
                            finish()
                        }
                    }
                }
                else{
                    val question = mQuestionsList?.get(mCurrentPosition - 1)

                    if(question!!.correctOption != mSelectedOption){
                        answerView(mSelectedOption,R.drawable.wrong_option_border_ig)
                    }else{
                        mCorrectAnswers += 1
                    }
                    answerView(question.correctOption,R.drawable.correct_option_border_ig)

                    mSelectedOption = 0

                    if(mCurrentPosition == mQuestionsList?.size){
                        submit?.text = "FINISH"
                    }
                    else{
                        submit?.text = "GO TO NEXT QUESTION"
                    }

                }

            }
        }
    }

    private fun answerView(answer:Int,drawableView : Int){
        when(answer){
            1 -> option1?.background = ContextCompat.getDrawable(this,drawableView)
            2 -> option2?.background = ContextCompat.getDrawable(this,drawableView)
            3 -> option3?.background = ContextCompat.getDrawable(this,drawableView)
            4 -> option4?.background = ContextCompat.getDrawable(this,drawableView)
        }
    }
}