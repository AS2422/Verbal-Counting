package com.asafin24.verbalcounting.screen.PracticSetting


import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.asafin24.verbalcounting.APP
import com.asafin24.verbalcounting.R
import com.asafin24.verbalcounting.databinding.FragmentPractingSettingBinding


class PractingSetting : Fragment() {

    lateinit var binding: FragmentPractingSettingBinding
    var typeGame: String = ""
    var complexity: String = ""
    var operations = arrayListOf<String>()
    var gameTime: Long = 1L
    var numberExamples = 0
    val bundle = Bundle()

    val sharedPreferences: SharedPreferences = APP.getSharedPreferences("SettingsPractic", MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPractingSettingBinding.inflate(layoutInflater, container, false)
        loadPreferences()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        checkRGTypeGame()
        checkRGComplexity()
        addOperations()
        startButton()
    }

    fun checkRGTypeGame() {

        binding.rgTypeGame.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = binding.rgTypeGame.findViewById(checkedId)
            typeGame = radio.text.toString()
            val checkIndTypeGame = binding.rgTypeGame.indexOfChild(radio)
            savePreferences("typeGameSave", checkIndTypeGame)

            savePreferencesString("typeGameTextSave", typeGame)
        }
    }

    fun checkRGComplexity() {
        binding.rgComplexity.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = binding.rgComplexity.findViewById(checkedId)
            complexity = radio.text.toString()

            val checkIndComplexity = binding.rgComplexity.indexOfChild(radio)
            savePreferences("complexitySave", checkIndComplexity)

            savePreferencesString("complexityTextSave", complexity)
        }
    }

    fun addOperations() {

        binding.cbAll.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.cbDecrement.isChecked = true
                binding.cbDivision.isChecked = true
                binding.cbMultiplication.isChecked = true
                binding.cbSumm.isChecked = true
                savePreferencesBoolean("cbAll", true)
            } else savePreferencesBoolean("cbAll", false)
        }

        binding.cbDecrement.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                operations.add("-")
                savePreferencesBoolean("cbDecrement", true)
            } else {
                if (operations.isNotEmpty()) operations.removeAt(operations.indexOf("-"))
                binding.cbAll.isChecked = false
                savePreferencesBoolean("cbDecrement", false)
            }
        }

        binding.cbDivision.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                operations.add(":")
                savePreferencesBoolean("cbDivision", true)
            } else {
                if (operations.isNotEmpty()) operations.removeAt(operations.indexOf(":"))
                binding.cbAll.isChecked = false
                savePreferencesBoolean("cbDivision", false)
            }
        }

        binding.cbSumm.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                operations.add("+")
                savePreferencesBoolean("cbSumm", true)
            } else {
                if (operations.isNotEmpty()) operations.removeAt(operations.indexOf("+"))
                binding.cbAll.isChecked = false
                savePreferencesBoolean("cbSumm", false)
            }
        }

        binding.cbMultiplication.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                operations.add("*")
                savePreferencesBoolean("cbMultiplication", true)
            } else {
                if (operations.isNotEmpty()) operations.removeAt(operations.indexOf("*"))
                binding.cbAll.isChecked = false
                savePreferencesBoolean("cbMultiplication", false)
            }
        }
    }

    fun startButton() {
        binding.btnStart.setOnClickListener {

            if (typeGame == "На время") {
                if (binding.etMinutes.text.toString() == "0") {
                    Toast.makeText(context, "не указано время", Toast.LENGTH_SHORT).show()
                } else {
                    try {
                        gameTime = binding.etMinutes.text.toString().toLong()
                        bundle.putSerializable("gameTime", gameTime)
                        savePreferences("gameTimeSave", gameTime.toInt())
                        bundle()

                    } catch (e: Exception) {
                        Toast.makeText(context, "некорректный формат времени", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else if (typeGame == "На количество") {
                if (binding.etNumbers.text.toString() == "0") {
                    Toast.makeText(context, "не указано число примеров", Toast.LENGTH_SHORT).show()
                } else {
                    try {
                        numberExamples = binding.etNumbers.text.toString().toInt()
                        bundle.putSerializable("numberExamples", numberExamples)
                        savePreferences("numberExamplesSave", numberExamples)
                        bundle()
                    } catch (e: Exception) {
                        Toast.makeText(context, "некорректное число примеров", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else Toast.makeText(context, "не выбран тип игры", Toast.LENGTH_SHORT).show()
        }
    }

    fun bundle() {
        if (complexity == "") {
            Toast.makeText(context, "не выбрана сложность", Toast.LENGTH_SHORT).show()
        } else {
            bundle.putSerializable("complexity", complexity)

            if (operations.isEmpty()) {
                Toast.makeText(context, "не выбраны операции", Toast.LENGTH_SHORT).show()
            } else {
                bundle.putSerializable("operations", operations)
                bundle.putSerializable("typeGame", typeGame)
                APP.navController.navigate(
                    R.id.action_practingSetting_to_practic, bundle)
            }
        }
    }

    private fun savePreferences(key: String, value: Int) {
        editor.putInt(key, value)
        editor.apply()
    }

    private fun savePreferencesBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    private fun savePreferencesString(key: String, value: String) {
        editor.putString(key, value)
        editor.apply()
    }

    private fun loadPreferences() {

        val sharedPreferences: SharedPreferences = APP.getSharedPreferences("SettingsPractic", MODE_PRIVATE)

        val savedTypeGame = sharedPreferences.getInt("typeGameSave", 0)
        val savedCheckedTypeGame = binding.rgTypeGame.getChildAt(savedTypeGame) as RadioButton
        savedCheckedTypeGame.isChecked = true
        typeGame = sharedPreferences.getString("typeGameTextSave", "")!!

        val savedGameTime = sharedPreferences.getInt("gameTimeSave", 0)
        binding.etMinutes.setText(savedGameTime.toString())

        val savedNumerExamples = sharedPreferences.getInt("numberExamplesSave", 0)
        binding.etNumbers.setText(savedNumerExamples.toString())


        val savedComplexity = sharedPreferences.getInt("complexitySave", 0)
        val savedCheckedComplexity = binding.rgComplexity.getChildAt(savedComplexity) as RadioButton
        savedCheckedComplexity.isChecked = true
        complexity = sharedPreferences.getString("complexityTextSave", "")!!


        binding.cbAll.isChecked = sharedPreferences.getBoolean("cbAll", false)
        if (binding.cbAll.isChecked) {
            binding.cbDecrement.isChecked = true
            binding.cbDivision.isChecked = true
            binding.cbSumm.isChecked = true
            binding.cbMultiplication.isChecked = true
        }

        binding.cbDecrement.isChecked = sharedPreferences.getBoolean("cbDecrement", false)
        if (binding.cbDecrement.isChecked) operations.add("-")

        binding.cbDivision.isChecked = sharedPreferences.getBoolean("cbDivision", false)
        if (binding.cbDivision.isChecked) operations.add(":")

        binding.cbSumm.isChecked = sharedPreferences.getBoolean("cbSumm", false)
        if (binding.cbSumm.isChecked) operations.add("+")

        binding.cbMultiplication.isChecked = sharedPreferences.getBoolean("cbMultiplication", false)
        if (binding.cbMultiplication.isChecked) operations.add("*")
    }
}