package com.example.navigation.ui

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.flask_1.R
import com.example.flask_1.ui.login.ExamData
import com.example.flask_1.ui.login.ProblemData
import com.example.flask_1.ui.login.RetrofitClient
import com.example.flask_1.ui.login.SaveExamResponse
import com.example.navigation.ui.problem.Problem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProblemAdapter(private val problems: List<Problem>, private val onCheckAnswersClick: () -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_PROBLEM = 0
    private val TYPE_BUTTON = 1
    private var showResults = false

    class ProblemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionTextView: TextView = itemView.findViewById(R.id.text_question)
        val optionsContainer: LinearLayout = itemView.findViewById(R.id.options_container)
        val resultTextView: TextView = itemView.findViewById(R.id.text_result)
    }

    class ButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkAnswersButton: Button = itemView.findViewById(R.id.button_check_answers)
        val saveProblemsButton: Button = itemView.findViewById(R.id.button_save_problems)
        val finishQuizButton: Button = itemView.findViewById(R.id.button_finish_quiz)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_PROBLEM) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_problem, parent, false)
            ProblemViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_button_submit, parent, false)
            ButtonViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == TYPE_PROBLEM) {
            val problem = problems[position]
            val problemHolder = holder as ProblemViewHolder
            problemHolder.questionTextView.text = problem.question

            problemHolder.optionsContainer.removeAllViews()
            problem.options.forEachIndexed { index, option ->
                val checkBox = CheckBox(holder.itemView.context).apply {
                    text = option
                    isChecked = problem.selectedOption == index
                    setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            problem.selectedOption = index
                            notifyItemChanged(position) // Update only the current item to ensure only one checkbox is checked per problem
                        }
                    }
                }
                problemHolder.optionsContainer.addView(checkBox)

                // Set color based on correctness if results are shown
                if (showResults) {
                    if (index == problem.answerIndex - 1) {
                        checkBox.setTextColor(Color.BLUE) // Correct answer highlighted
                    } else if (problem.selectedOption == index && !problem.isCorrect) {
                        checkBox.setTextColor(Color.RED) // Incorrect answer selected
                    } else {
                        checkBox.setTextColor(Color.BLACK) // Default color
                    }
                } else {
                    checkBox.setTextColor(Color.BLACK) // Default color if no selection
                }
            }

            // Update the result text view based on the correctness of the selected answer if results are shown
            if (showResults && problem.selectedOption != -1) {
                problemHolder.resultTextView.visibility = View.VISIBLE
                problemHolder.resultTextView.text = if (problem.isCorrect) "정답" else "오답"
                problemHolder.resultTextView.setTextColor(if (problem.isCorrect) Color.BLUE else Color.RED) // Set text color based on result
            } else {
                problemHolder.resultTextView.visibility = View.GONE
            }
        } else {
            val buttonHolder = holder as ButtonViewHolder
            buttonHolder.checkAnswersButton.visibility = if (showResults) View.GONE else View.VISIBLE
            buttonHolder.saveProblemsButton.visibility = if (showResults) View.VISIBLE else View.GONE
            buttonHolder.finishQuizButton.visibility = if (showResults) View.VISIBLE else View.GONE

            buttonHolder.checkAnswersButton.setOnClickListener {
                checkAnswers()
                onCheckAnswersClick()
            }

            buttonHolder.saveProblemsButton.setOnClickListener {
                saveProblemsToServer(holder)
            }

            buttonHolder.finishQuizButton.setOnClickListener {
                // Handle the "Finish Quiz" button click here
            }
        }
    }

    override fun getItemCount(): Int {
        return problems.size + 1 // Add one for the button
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < problems.size) {
            TYPE_PROBLEM
        } else {
            TYPE_BUTTON
        }
    }

    private fun checkAnswers() {
        showResults = true
        problems.forEach { problem ->
            problem.isCorrect = problem.selectedOption == problem.answerIndex - 1
        }
        notifyDataSetChanged()
    }

    private fun saveProblemsToServer(holder: RecyclerView.ViewHolder) {
        val context = holder.itemView.context
        val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", null)

        if (username == null) {
            Toast.makeText(context, "No user logged in", Toast.LENGTH_SHORT).show()
            return
        }

        val problemData = problems.map { problem ->
            ProblemData(
                question = problem.question,
                option1 = problem.options.getOrNull(0) ?: "",
                option2 = problem.options.getOrNull(1) ?: "",
                option3 = problem.options.getOrNull(2) ?: "",
                option4 = problem.options.getOrNull(3) ?: "",
                option5 = problem.options.getOrNull(4) ?: "",
                answer = problem.answerIndex.toString()
            )
        }
        val examData = ExamData(username, problemData)

        RetrofitClient.apiService.saveExam(examData).enqueue(object : Callback<SaveExamResponse> {
            override fun onResponse(call: Call<SaveExamResponse>, response: Response<SaveExamResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(holder.itemView.context, "Problems saved successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(holder.itemView.context, "Failed to save problems", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SaveExamResponse>, t: Throwable) {
                Toast.makeText(holder.itemView.context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
