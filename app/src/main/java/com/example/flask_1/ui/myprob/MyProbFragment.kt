// MyProbFragment.kt
package com.example.flask_1.ui.myprob

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flask_1.R
import com.example.flask_1.databinding.FragmentMyProbBinding
import com.example.flask_1.ui.login.Exam
import com.example.flask_1.ui.login.RetrofitClient
import com.example.week2test.ui.gallery.RecyclerAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyProbFragment : Fragment() {

    private var _binding: FragmentMyProbBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerAdapter
    private val examList = arrayListOf<Exam>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyProbBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // RecyclerView 설정
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = RecyclerAdapter(examList, R.id.action_myProbFragment_to_solvingFragment) // 네비게이션 액션 ID를 전달합니다.
        recyclerView.adapter = adapter

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
                        adapter.notifyDataSetChanged()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
