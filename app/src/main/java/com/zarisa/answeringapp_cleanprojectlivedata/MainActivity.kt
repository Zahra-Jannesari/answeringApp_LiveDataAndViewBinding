package com.zarisa.answeringapp_cleanprojectlivedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.zarisa.answeringapp_cleanprojectlivedata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
//    private val vmodel : MainViewModel by viewModels()
    private lateinit var vmodel : MainViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vmodel = ViewModelProvider(this)[MainViewModel::class.java]
        setLiveDatas()
        initViews()
    }

    private fun setLiveDatas() {
        vmodel.questionCount.observe(this) { number ->
            binding.tvTotal.text = number.toString()
            binding.progressBar.max = number
        }
        vmodel.currentQuestionNumber.observe(this){number->
            binding.tvCurrentNumber.text=number.toString()
            binding.progressBar.progress = number
        }
        vmodel.scoreLiveData.observe(this){
            binding.textViewScore.text=it.toString()
        }
        vmodel.questionTextLiveData.observe(this) { question ->
            binding.tvQuestion.text = question
        }
        vmodel.nextEnabledLiveData.observe(this) { enabled ->
            binding.buttonNext.isEnabled = enabled
        }
        vmodel.prevEnabledLiveData.observe(this) { enabled ->
            binding.buttonPrev.isEnabled = enabled
        }
        vmodel.scoreColorLiveData.observe(this) {
            binding.textViewScore.setTextColor(it)
        }
    }

    private fun initViews() {
        binding.buttonNext.setOnClickListener {
            vmodel.nextClicked()
            binding.editTextAnswer.setText("")
        }
        binding.buttonPrev.setOnClickListener {
            vmodel.prevClicked()
            binding.editTextAnswer.setText("")
        }
        binding.buttonSubmitAnswer.setOnClickListener {
            vmodel.submitAnswer(binding.editTextAnswer.text.toString())
        }
        binding.buttonAddQuestion.setOnClickListener {
            vmodel.addQuestion()
        }
    }
}