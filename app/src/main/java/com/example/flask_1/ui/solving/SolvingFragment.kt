package com.example.navigation.ui.solving

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flask_1.R
import com.example.navigation.ui.problem.Problem
import com.example.navigation.ui.problem.ProblemAdapter

class SolvingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_solving, container, false)

        // Initialize RecyclerView
        val recyclerView = root.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Get data from arguments
        val examName = arguments?.getString("exam_name")
        val questions = arguments?.getStringArrayList("questions") ?: arrayListOf()
        val options = arguments?.getSerializable("options") as? ArrayList<ArrayList<String>> ?: arrayListOf()
        val answers = arguments?.getStringArrayList("answers") ?: arrayListOf()
        val examUsername = arguments?.getString("exam_username") // Add this line

        // Get current user's username from SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val currentUsername = sharedPreferences.getString("username", "") ?: ""

        // Set exam name
        val examNameTextView = root.findViewById<TextView>(R.id.text_exam_name)
        examNameTextView.text = examName

        // Convert answers to Integer list
        val intAnswers = answers.mapNotNull { it.toIntOrNull() }

        val problems = questions.mapIndexed { index, question ->
            Problem(
                question = question,
                options = options.getOrElse(index) { arrayListOf() },
                answerIndex = intAnswers.getOrElse(index) { -1 }
            )
        }

        // Pass examUsername to the adapter and set visibility of save button based on usernames
        val adapter = ProblemAdapter(problems, currentUsername, examUsername ?: "", {
            // Handle the "Check Answers" button click here
        }, {
            findNavController().navigate(R.id.action_solvingFragment_to_homeFragment)
        })

        recyclerView.adapter = adapter

        return root
    }
}
