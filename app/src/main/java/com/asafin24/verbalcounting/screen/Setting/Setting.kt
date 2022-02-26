package com.asafin24.verbalcounting.screen.Setting


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatDelegate
import com.asafin24.verbalcounting.APP
import com.asafin24.verbalcounting.R
import com.asafin24.verbalcounting.databinding.FragmentSettingBinding


class Setting : Fragment() {
    lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(layoutInflater, container, false)

        //системная кнопка "назад"
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                APP.navController.navigate(R.id.action_setting_to_menuMainFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
        //

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //"Тёмная" тема
        val appSettingPrefs: SharedPreferences = requireActivity().getSharedPreferences("AppSettingPrefs", Context.MODE_PRIVATE)
        val sharedPrefsEdit: SharedPreferences.Editor = appSettingPrefs.edit()
        val savedSwitch = appSettingPrefs.getBoolean("SwitchKey", false)
        binding.switch1.isChecked = savedSwitch
        binding.switch1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPrefsEdit.apply {
                    putBoolean("NightMode", true)
                    putBoolean("SwitchKey", true)
                }.apply()

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPrefsEdit.apply {
                    putBoolean("NightMode", false)
                    putBoolean("SwitchKey", false)
                }.apply()
            }
        }
        //

        binding.ibBack.setOnClickListener {
            APP.navController.navigate(R.id.action_setting_to_menuMainFragment)
        }

        binding.tvAboutApp.setOnClickListener {
           //val fragmentAboutApp = AboutAppFragment()
          //  fragmentAboutApp.show(((activity as FragmentActivity).supportFragmentManager),"aboutAppDialog")
        }

        binding.tvFeedback.setOnClickListener {
         //   val fragmentFeedback = FeedbackFragment()
          //  fragmentFeedback.show(((activity as FragmentActivity).supportFragmentManager),"feedbackDialog")
        }
    }



}