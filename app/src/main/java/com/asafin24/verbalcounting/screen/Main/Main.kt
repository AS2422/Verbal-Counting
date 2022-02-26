package com.asafin24.verbalcounting.screen.Main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asafin24.verbalcounting.APP
import com.asafin24.verbalcounting.R
import com.asafin24.verbalcounting.databinding.FragmentMainBinding


class Main : Fragment() {

    lateinit var binding: FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.ibStart.setOnClickListener {
            APP.navController.navigate(R.id.action_main_to_practingSetting)
        }

        binding.ibSetting.setOnClickListener {
            APP.navController.navigate(R.id.action_menuMainFragment_to_setting)
        }
    }

}