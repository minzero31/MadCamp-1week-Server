package com.example.flask_1.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flask_1.R
import com.example.flask_1.ui.login.Exam
import com.example.flask_1.ui.login.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var textCreateQuestion: TextView
    private lateinit var textMyQuestionsTitle: TextView
    private lateinit var recyclerMyQuestions: RecyclerView
    private lateinit var iconAdd: ImageView
    private val examList = arrayListOf<Exam>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        textCreateQuestion = root.findViewById(R.id.text_create_question)
        textMyQuestionsTitle = root.findViewById(R.id.text_my_questions_title)
        recyclerMyQuestions = root.findViewById(R.id.recycler_my_questions)
        iconAdd = root.findViewById(R.id.icon_add)

        // 리사이클러 뷰 설정
        recyclerMyQuestions.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerMyQuestions.adapter = MyQuestionsAdapter(examList) { exam ->
            val bundle = Bundle().apply {
                putString("exam_name", exam.exam_name)
                putStringArrayList("questions", ArrayList(exam.questions))
                putSerializable("options", ArrayList(exam.options))
                putStringArrayList("answers", ArrayList(exam.answers))
            }
            findNavController().navigate(R.id.action_homeFragment_to_solvingFragment, bundle)
        }

        // 플러스 아이콘 클릭 리스너 설정
        iconAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_uploadImgFragment)
        }

        // 사용자 데이터 로드
        loadExamData()

        return root
    }

    private fun loadExamData() {
        val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "") ?: return

        RetrofitClient.apiService.getMyExams(mapOf("username" to username)).enqueue(object : Callback<List<Exam>> {
            override fun onResponse(call: Call<List<Exam>>, response: Response<List<Exam>>) {
                if (response.isSuccessful) {
                    response.body()?.let { exams ->
                        examList.clear()
                        examList.addAll(exams)
                        recyclerMyQuestions.adapter?.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(context, "Failed to load exam data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Exam>>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
