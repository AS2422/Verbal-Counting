package com.asafin24.verbalcounting.screen.Progress

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.asafin24.verbalcounting.adapter.ProgressAdapter
import com.asafin24.verbalcounting.databinding.FragmentProgressBinding
import com.asafin24.verbalcounting.screen.HistoryPractic.HistoryPracticViewModel


class Progress : Fragment() {

    lateinit var binding: FragmentProgressBinding
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ProgressAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProgressBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val viewModel = ViewModelProvider(this).get(ProgressViewModel::class.java)
        viewModel.initDataBase()
        recyclerView = binding.recyclerView
        adapter = ProgressAdapter()
        recyclerView.adapter = adapter

        viewModel.getAllPractics().observe(viewLifecycleOwner, { listPractics ->
            adapter.setList(listPractics.reversed())
        })
    }

}