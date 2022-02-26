package com.asafin24.verbalcounting.screen.ResultPractic


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.asafin24.verbalcounting.APP
import com.asafin24.verbalcounting.R
import com.asafin24.verbalcounting.databinding.FragmentResultPracticBinding
import com.asafin24.verbalcounting.model.PracticModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ResultPractic : Fragment() {

    lateinit var binding: FragmentResultPracticBinding
    var correctAnswers = 0
    var countAnswers = 0
    lateinit var typeGame: String
    var numberExamples = 0
    lateinit var mistakes: ArrayList<String>

    @SuppressLint("SimpleDateFormat")
    val date = SimpleDateFormat("dd.M.yyyy").format(Date())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultPracticBinding.inflate(layoutInflater, container, false)

        //системная кнопка "назад"
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                APP.navController.navigate(R.id.action_resultPractic_to_menuMainFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
        //

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        typeGame = arguments?.getSerializable("typeGame") as String
        correctAnswers = arguments?.getSerializable("correctAnswers") as Int
        binding.tvCorrectAnswers.text = correctAnswers.toString()

        if (typeGame == "На время") {
            countAnswers = arguments?.getSerializable("countAnswers") as Int
            binding.tvCountAnswers.text = countAnswers.toString()
        } else {
            numberExamples = arguments?.getSerializable("numberExamples") as Int
            binding.tvCountAnswers.text = numberExamples.toString()
        }

        showMistakes()

        binding.button.setOnClickListener {
            init()
        }

    }

    fun showMistakes() {
        mistakes = arguments?.getSerializable("mistakes") as ArrayList<String>
        if (mistakes.isNotEmpty()) {
            for (i in mistakes) {
                binding.tvMistake.append(i)
                binding.tvMistake.append("\n")
            }
        }
    }

    private fun init() {
        val allAnswers = if (typeGame == "На время") countAnswers else numberExamples

        val viewModel = ViewModelProvider(this).get(ResultPracticViewModel::class.java)
        viewModel.initDataBase()
        viewModel.insert(PracticModel(date = date, typeGame = typeGame, result = "$correctAnswers из $allAnswers")){}
    }



}