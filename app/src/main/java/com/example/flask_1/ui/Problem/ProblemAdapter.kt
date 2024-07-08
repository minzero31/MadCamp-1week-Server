package com.example.navigation.ui.problem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flask_1.R

class ProblemAdapter(private val problems: List<Problem>) : RecyclerView.Adapter<ProblemAdapter.ProblemViewHolder>() {

    class ProblemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionTextView: TextView = itemView.findViewById(R.id.text_question)
        val optionsTextView: TextView = itemView.findViewById(R.id.text_options)
        val answerTextView: TextView = itemView.findViewById(R.id.text_answer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProblemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_problem, parent, false)
        return ProblemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProblemViewHolder, position: Int) {
        val problem = problems[position]
        holder.questionTextView.text = problem.question
        holder.optionsTextView.text = problem.options.joinToString(separator = "\n")
        holder.answerTextView.text = problem.answer
    }

    override fun getItemCount(): Int {
        return problems.size
    }
}
