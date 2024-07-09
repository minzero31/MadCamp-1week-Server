package com.example.flask_1.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flask_1.R
import com.example.flask_1.ui.login.Exam

class MyQuestionsAdapter(
    private val questions: List<Exam>,
    private val onItemClick: (Exam) -> Unit
) : RecyclerView.Adapter<MyQuestionsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View, private val onItemClick: (Exam) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val textQuestion: TextView = itemView.findViewById(R.id.text_question)

        fun bind(exam: Exam) {
            textQuestion.text = exam.exam_name
            itemView.setOnClickListener {
                onItemClick(exam)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_question_card, parent, false)
        return ViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(questions[position])
    }

    override fun getItemCount(): Int {
        return questions.size
    }
}
