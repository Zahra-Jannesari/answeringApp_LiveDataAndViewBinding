package com.zarisa.answeringapp_cleanprojectlivedata

import android.content.Context
import androidx.lifecycle.LiveData
import java.util.*

object QuestionRepository {
    var db: AppDatabase? = null
    var questionDao: QuestionDao? = null
    fun initDB(context: Context) {
        db = AppDatabase.getAppDataBase(context)
        questionDao = db?.questionDao()
        addTestData()
    }
    private fun addTestData() {
//        questionDao?.deleteAll()
        questionDao?.insertAll(
            newRandomQuestion(),
            newRandomQuestion(),
            newRandomQuestion(),
            newRandomQuestion(),
            newRandomQuestion(),
            newRandomQuestion(),
            newRandomQuestion(),
            newRandomQuestion(),
            newRandomQuestion()
        )
    }

    fun getQuestion(n:Int?) : Question {
        return db!!.questionDao().getQuestionLiveData(n) }

    fun addQuestion(q: Question) {
        db!!.questionDao().insertAll(q) }


    fun newRandomQuestion(): Question {
        var randB = Random().nextInt(500) + 1
        var randA = Random().nextInt(500) + randB
        return Question(
            db!!.questionDao().getTotalNumberOfQuestionsInt(),
            "$randA-$randB",
            "${randA - randB}",
            false)
    }

    fun addNewRandom() {
        addQuestion(newRandomQuestion())
    }
    fun questionsCountInt():Int{
        return db!!.questionDao().getTotalNumberOfQuestionsInt()
    }

    fun questionsCountLivedata(): LiveData<Int> {
        return db!!.questionDao().getTotalNumberOfQuestionsLiveData()
    }
}