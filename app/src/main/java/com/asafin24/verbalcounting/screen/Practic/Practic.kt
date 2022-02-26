package com.asafin24.verbalcounting.screen.Practic


import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.asafin24.verbalcounting.APP
import com.asafin24.verbalcounting.MainActivity
import com.asafin24.verbalcounting.R
import com.asafin24.verbalcounting.databinding.FragmentPracticBinding
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random


class Practic : Fragment() {

    lateinit var binding: FragmentPracticBinding
    lateinit var complexity: String
    lateinit var typeGame: String
    lateinit var operations: ArrayList<*>
    var numberExamples: Int = 0
    var gameTime: Long = 0
    var a: Int = 0
    var b: Int = 0
    var timer: CountDownTimer? = null
    var symbol: Any = 0
    var correctAnswers = 0
    var countAnswers = 0
    var countExamples = 1
    val bundle = Bundle()
    var mistakes: ArrayList<String> = arrayListOf()

    private var isPaused = false
    private var resumeFromMillis: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPracticBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        mainBottomNavView.visibility = View.GONE

        typeGame = arguments?.getSerializable("typeGame") as String
        complexity = arguments?.getSerializable("complexity") as String
        operations = arguments?.getSerializable("operations") as ArrayList<*>



        if (typeGame == "На время") {
            gameTime = arguments?.getSerializable("gameTime") as Long

        } else {
            numberExamples = arguments?.getSerializable("numberExamples") as Int
            binding.tvTimer.text = "$countExamples из $numberExamples"
        }

        symbol = operations[Random.nextInt(0,operations.size)]
        randomNumber()
        binding.tvExample.text = randomExample()

        binding.btn0.setOnClickListener { setTVResult("0") }
        binding.btn1.setOnClickListener { setTVResult("1") }
        binding.btn2.setOnClickListener { setTVResult("2") }
        binding.btn3.setOnClickListener { setTVResult("3") }
        binding.btn4.setOnClickListener { setTVResult("4") }
        binding.btn5.setOnClickListener { setTVResult("5") }
        binding.btn6.setOnClickListener { setTVResult("6") }
        binding.btn7.setOnClickListener { setTVResult("7") }
        binding.btn8.setOnClickListener { setTVResult("8") }
        binding.btn9.setOnClickListener { setTVResult("9") }


        binding.btnDel.setOnClickListener {
            val str = binding.tvResult.text
            if (str.isNotEmpty()) binding.tvResult.text = str.substring(0, str.length - 1)
        }

        binding.btnOk.setOnClickListener {
            if (binding.tvResult.text.isEmpty()) Toast.makeText(context, "Пустой ответ", Toast.LENGTH_SHORT).show()
            else {
                mistake()
                if (checkCorrectAnswer()) correctAnswers++
                if (typeGame == "На время") countAnswers++
                else {
                    countExamples++
                    binding.tvTimer.text = "$countExamples из $numberExamples"
                    if (countExamples > numberExamples) bundle()
                }

                symbol = operations[Random.nextInt(0, operations.size)]
                randomNumber()
                binding.tvExample.text = randomExample()
                binding.tvResult.text = ""
            }
        }
    }

    fun randomExample(): String {
        return "$a $symbol $b"
    }


    fun setTVResult(number: String) {
        binding.tvResult.append(number)
    }

    fun checkCorrectAnswer(): Boolean {
        val answer = binding.tvResult.text.toString()
        if (symbol=="-") return (a-b) == answer.toInt()
        else if (symbol=="+") return (a+b) == answer.toInt()
        else if (symbol==":") return (a/b) == answer.toInt()
        else return (a*b) == answer.toInt()
    }

    fun mistake() {
        if (!checkCorrectAnswer()) mistakes.add("${binding.tvExample.text} = ${binding.tvResult.text}")
    }

    private fun countDownTimer(timeMillis: Long){
        timer?.cancel()
        timer = object : CountDownTimer(timeMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvTimer.text = getString(R.string.formatted_time, TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60, TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60)
                if (isPaused) {
                    resumeFromMillis = millisUntilFinished
                    timer?.cancel()
                }
            }
            override fun onFinish() {

                bundle()
            }
        }.start()
    }

    fun bundle() {
        bundle.putSerializable("typeGame", typeGame)
        bundle.putSerializable("numberExamples", numberExamples)
        bundle.putSerializable("countAnswers", countAnswers)
        bundle.putSerializable("correctAnswers", correctAnswers)
        bundle.putSerializable("mistakes", mistakes)
        APP.navController.navigate(R.id.action_practic_to_resultPractic, bundle)
    }

    override fun onPause() {
        isPaused = true
        super.onPause()
    }

    override fun onStart() {
        if (typeGame == "На время") {
            if (isPaused) {
                isPaused = false
                countDownTimer(resumeFromMillis)
            } else {
                countDownTimer(gameTime * 60000)
            }
        }
        super.onStart()
    }

    override fun onStop() {
        timer?.cancel()
        super.onStop()
    }

    fun randomNumber() {
        if (complexity=="Однозначные") {
            if (symbol == "-") {
                a = Random.nextInt(3, 10)
                b = Random.nextInt(1, a-1)
            } else if (symbol == ":") {
                a = Random.nextInt(3, 10)
                b = Random.nextInt(1, a-1)
                while (a % b != 0) b--
            }
            else {
                a = Random.nextInt(2, 10)
                b = Random.nextInt(2, 10)
            }
        }
        else if (complexity=="Двузначные") {
            if (symbol == "-") {
                a = Random.nextInt(12, 100)
                b = Random.nextInt(10, a-1)
            } else if (symbol == ":") {
                a = Random.nextInt(12, 100)
                b = Random.nextInt(10, a-1)
                while (a % b != 0) b--
            }
            else {
                b = Random.nextInt(10, 100)
                a = Random.nextInt(10, 100)
            }
        }
        else {
            if (symbol == "-") {
                a = Random.nextInt(102, 1000)
                b = Random.nextInt(100, a-1)
            } else if (symbol == ":") {
                a = Random.nextInt(102, 1000)
                b = Random.nextInt(100, a-1)
                while (a % b != 0) b--
            }
            else {
                b = Random.nextInt(100, 1000)
                a = Random.nextInt(100, 1000)
            }
        }
    }
}
