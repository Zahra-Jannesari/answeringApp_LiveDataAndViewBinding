package com.zarisa.answeringapp_cleanprojectlivedata

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class MainViewModel  : ViewModel(){

    val scoreLiveData= MutableLiveData(0)
    val scoreColorLiveData: LiveData<Int> = Transformations.map(scoreLiveData
    ) {
        when (it) {
            in 0..(questionCount /3) -> Color.RED
            in (questionCount /3)+1..(questionCount*2/3) -> Color.YELLOW
            else -> Color.GREEN
        }
    }

    private var questionAnswer=QuestionRepository.questionList[0].questionAnswer
    val numberLiveData = MutableLiveData(1)
    val hintLiveData: LiveData<String> = Transformations.map(numberLiveData
    ) {
        when (it) {
            in 1..(questionCount / 2) -> "hurry up!"
            in (questionCount / 2) + 1..questionCount -> "almost there!"
            else -> ":)"
        }
    }
    val questionLiveData = MutableLiveData(
        QuestionRepository.questionList[0].questionText
    )
    val questionCount = QuestionRepository.questionList.size

    var nextEnabledLiveData = MutableLiveData(true)
    var prevEnabledLiveData = MutableLiveData(false)

    fun nextClicked() {
        numberLiveData.value = numberLiveData.value?.plus(1)
        numberLiveData.value?.let{ number ->
            questionLiveData.value = QuestionRepository.questionList[number-1].questionText
            questionAnswer=QuestionRepository.questionList[number-1].questionAnswer
        }
        checkNextPrev()
    }

    fun prevClicked() {
        numberLiveData.value = numberLiveData.value?.minus(1)
        numberLiveData.value?.let{ number ->
            questionLiveData.value = QuestionRepository.questionList[number-1].questionText
            questionAnswer=QuestionRepository.questionList[number-1].questionAnswer
        }
        checkNextPrev()
    }
    private fun checkNextPrev(){
        nextEnabledLiveData.value=when(numberLiveData.value){
            questionCount->false
            else->true
        }
        prevEnabledLiveData.value=when(numberLiveData.value){
            1->false
            else->true
        }
    }
    private fun checkAnswer(userAnswer:String){
        if (userAnswer==questionAnswer && !QuestionRepository.questionList[numberLiveData.value?.minus(1)!!].isAnswered) {
            scoreLiveData.value=scoreLiveData.value?.plus(1)
        }
        if(userAnswer!=""){
            QuestionRepository.questionList[numberLiveData.value?.minus(1)!!].isAnswered = true
        }

    }

    fun submitAnswer(userAnswer:String) {
        checkAnswer(userAnswer)
    }
}