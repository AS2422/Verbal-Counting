package com.asafin24.verbalcounting.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asafin24.verbalcounting.R
import com.asafin24.verbalcounting.databinding.HistoryPracticsItemBinding
import com.asafin24.verbalcounting.model.PracticModel
import kotlinx.android.synthetic.main.history_practics_item.view.*

class ProgressAdapter : RecyclerView.Adapter<ProgressAdapter.ViewHolder>() {

    var practicsList = emptyList<PracticModel>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val binding = HistoryPracticsItemBinding.bind(view)
//
//        fun bind(practic: PracticModel) = with(binding) {
//          //  tvData.text = practic.data.toString()
//            tvTypeGame.text = practic.typeGame
//            tvResultHistory.text = practic.result
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_practics_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tv_typeGame.text = practicsList[position].typeGame
        holder.itemView.tv_result_history.text = practicsList[position].result
        holder.itemView.tv_data.text = practicsList[position].date
    }

    override fun getItemCount(): Int {
        return practicsList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(practicSetList: List<PracticModel>) {
        practicsList = practicSetList
        notifyDataSetChanged()
    }
}