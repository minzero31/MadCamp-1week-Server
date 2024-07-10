package com.example.flask_1.ui.myprob

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.flask_1.R
import com.example.flask_1.ui.login.Exam

class MyProbAdapter(private val exams: List<Exam>, private val navActionId: Int) : RecyclerView.Adapter<MyProbAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return ViewHolder(view, navActionId)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exam = exams[position]
        holder.examName.text = exam.exam_name

        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putString("exam_name", exam.exam_name)
                putStringArrayList("questions", ArrayList(exam.questions))
                putSerializable("options", ArrayList(exam.options))
                putStringArrayList("answers", ArrayList(exam.answers))
                putString("exam_username", exam.username)  // Add exam username to the bundle
            }
            it.findNavController().navigate(navActionId, bundle)
        }
    }

    override fun getItemCount(): Int {
        return exams.size
    }

    class ViewHolder(itemView: View, private val navActionId: Int) : RecyclerView.ViewHolder(itemView) {
        val examName: TextView = itemView.findViewById(R.id.name_tv)
    }
}
