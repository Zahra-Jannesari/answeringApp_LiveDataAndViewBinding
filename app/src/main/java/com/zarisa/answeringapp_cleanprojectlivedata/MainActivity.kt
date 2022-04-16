package com.zarisa.answeringapp_cleanprojectlivedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels

class MainActivity : AppCompatActivity() {
    private val vmodel : MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setLiveDatas()
        initViews()
    }

    private fun setLiveDatas() {
        vmodel.questionLiveData.observe(this) { question ->
            binding.tvQuestion.text = question
        }
        vmodel.nextEnabledLiveData.observe(this) { enabled ->
            binding.buttonNext.isEnabled = enabled
        }
        vmodel.prevEnabledLiveData.observe(this) { enabled ->
            binding.buttonPrev.isEnabled = enabled
        }
        vmodel.numberLiveData.observe(this) { number ->
            binding.tvNumber.text = number.toString()
            binding.progressBar.progress = number
        }
        vmodel.hintLiveData.observe(this) {
            binding.textViewHint.text = it
        }
        vmodel.scoreLiveData.observe(this){
            binding.textViewScore.text=it.toString()
        }
        vmodel.scoreColorLiveData.observe(this) {
            binding.textViewScore.setTextColor(it)
        }
    }

    private fun initViews() {
        binding.progressBar.max = vmodel.questionCount
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
    }
}