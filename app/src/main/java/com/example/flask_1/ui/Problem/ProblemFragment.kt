package com.example.navigation.ui.problem

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flask_1.R
import com.example.navigation.ui.ProblemAdapter

class ProblemFragment : Fragment() {

    private lateinit var callback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the callback for back press
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Navigate to HomeFragment when back is pressed
                findNavController().navigate(R.id.action_problemFragment_to_homeFragment)
            }
        }

        // Add the callback to the OnBackPressedDispatcher
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_problem, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.recycler_view)

        val questions = arguments?.getStringArrayList("questions") ?: arrayListOf()
        val options = arguments?.getSerializable("options") as? ArrayList<ArrayList<String>> ?: arrayListOf()
        val answers = arguments?.getStringArrayList("answers") ?: arrayListOf()

        // Log data to ensure it's correct
        Log.d("ProblemFragment", "Questions: $questions")
        Log.d("ProblemFragment", "Options: $options")
        Log.d("ProblemFragment", "Answers: $answers")

        // Convert answers to Integer list
        val intAnswers = answers.mapNotNull { it.toIntOrNull() }

        val problems = questions.mapIndexed { index, question ->
            Problem(
                question = question,
                options = options.getOrElse(index) { arrayListOf() },
                answerIndex = intAnswers.getOrElse(index) { -1 }
            )
        }

        val adapter = ProblemAdapter(problems) {
            // Handle the "Check Answers" button click here
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Remove the callback when the view is destroyed
        callback.remove()
    }
}
