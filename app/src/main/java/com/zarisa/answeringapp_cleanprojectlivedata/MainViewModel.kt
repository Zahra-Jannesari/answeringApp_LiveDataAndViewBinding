package com.zarisa.answeringapp_cleanprojectlivedata

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.*

class MainViewModel  (app: Application) : AndroidViewModel(app){
    val questionCount = MutableLiveData<Int>(1)
    val currentQuestionNumber=MutableLiveData<Int>(1)
    private var currentQuestion:Question
    private var questionAnswer= String()
    val questionTextLiveData = MutableLiveData<String>()
    init{
        QuestionRepository.initDB(app.applicationContext)
        questionCount.value=QuestionRepository.questionsCountInt()
        currentQuestion=QuestionRepository.getQuestion(1)
        questionTextLiveData.value=currentQuestion.questionText
        questionAnswer=currentQuestion.questionAnswer
    }
    val scoreLiveData= MutableLiveData(0)
    val scoreColorLiveData: LiveData<Int> = Transformations.map(scoreLiveData) {
        when (it) {
            in 0..5 -> Color.RED
            in 5..10 -> Color.YELLOW
            else -> Color.GREEN
        }
    }
    var nextEnabledLiveData = MutableLiveData(true)
    var prevEnabledLiveData = MutableLiveData(false)
    fun nextClicked() {
        currentQuestionNumber.value = currentQuestionNumber.value?.plus(1)
        update()
        checkNextPrev()
    }
    fun prevClicked() {
        currentQuestionNumber.value = currentQuestionNumber.value?.minus(1)
        update()
        checkNextPrev()
    }
    private fun checkNextPrev(){
        nextEnabledLiveData.value=when(currentQuestionNumber.value){
            questionCount.value->false
            else->true
        }
        prevEnabledLiveData.value=when(currentQuestionNumber.value){
            1->false
            else->true
        }
    }
    fun submitAnswer(userAnswer:String) {
//        checkAnswer
        if (userAnswer==questionAnswer && !currentQuestion.isAnswered) {
            scoreLiveData.value=scoreLiveData.value?.plus(1)
        }
//        if(userAnswer!=""){
//            QuestionRepository.questionList[numberLiveData.value?.minus(1)!!].isAnswered = true
//        }
    }
    fun update(){
//        questionCount.value=QuestionRepository.questionsCountLivedata().value
        questionCount.value=QuestionRepository.questionsCountInt()
        currentQuestion=QuestionRepository.getQuestion(currentQuestionNumber.value)
        questionTextLiveData.value=currentQuestion.questionText
        questionAnswer=currentQuestion.questionAnswer
    }
    fun addQuestion(){
        QuestionRepository.addNewRandom()
        update()
    }
}