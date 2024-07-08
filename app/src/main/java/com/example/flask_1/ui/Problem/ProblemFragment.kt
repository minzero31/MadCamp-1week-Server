package com.example.navigation.ui.problem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flask_1.R

class ProblemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_problem, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.recycler_view)

        val problems = arguments?.let {
            val questions = it.getStringArrayList("questions") ?: arrayListOf()
            val options = it.getSerializable("options") as? List<List<String>> ?: listOf()
            val answers = it.getStringArrayList("answers") ?: arrayListOf()

            questions.mapIndexed { index, question ->
                Problem(
                    question = question,
                    options = options.getOrElse(index) { listOf() },
                    answer = answers.getOrElse(index) { "" }
                )
            }
        } ?: listOf()

        val adapter = ProblemAdapter(problems)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        return root
    }
}
