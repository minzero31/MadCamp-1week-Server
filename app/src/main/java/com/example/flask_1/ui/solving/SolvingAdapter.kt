package com.example.navigation.ui.solving

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flask_1.R

class SolvingAdapter(
    private val questions: List<String>,
    private val options: List<List<String>>,
    private val answers: List<String>
) : RecyclerView.Adapter<SolvingAdapter.SolvingViewHolder>() {

    class SolvingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionTextView: TextView = itemView.findViewById(R.id.text_question)
        val optionsContainer: LinearLayout = itemView.findViewById(R.id.options_container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolvingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_solving, parent, false)
        return SolvingViewHolder(view)
    }

    override fun onBindViewHolder(holder: SolvingViewHolder, position: Int) {
        holder.questionTextView.text = questions[position]

        holder.optionsContainer.removeAllViews()
        options[position].forEachIndexed { index, option ->
            val checkBox = CheckBox(holder.itemView.context).apply {
                text = option
            }
            holder.optionsContainer.addView(checkBox)
        }
    }

    override fun getItemCount(): Int = questions.size
}
