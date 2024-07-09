package com.example.flask_1.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flask_1.databinding.FragmentGalleryBinding
import com.example.flask_1.ui.gallery.RecyclerAdapter

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerAdapter
    private val userList = arrayListOf<Users>()
    private val filteredList = arrayListOf<Users>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // 데이터 초기화 (임시 데이터 추가)
        initializeData()

        // RecyclerView 설정
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = RecyclerAdapter(filteredList)
        recyclerView.adapter = adapter

        // 검색 기능 설정
        binding.searchButton.setOnClickListener {
            val searchText = binding.searchEditText.text.toString()
            filterList(searchText)
        }

        // RecyclerView 초기화
        filterList("")

        return root
    }

    private fun initializeData() {
        userList.add(Users("데이터구조", "email1@example.com"))
        userList.add(Users("컴퓨터 아키텍처", "email2@example.com"))
        userList.add(Users("한국사", "email3@example.com"))
        userList.add(Users("사랑과 헌법", "email3@example.com"))
        userList.add(Users("비만의 생물학", "email3@example.com"))
        userList.add(Users("영교필", "email3@example.com"))
        userList.add(Users("프로그래밍입문", "email3@example.com"))
        userList.add(Users("객체지향", "email3@example.com"))
        // Add more data as needed
    }

    private fun filterList(searchText: String) {
        filteredList.clear()
        if (searchText.isEmpty()) {
            filteredList.addAll(userList)
        } else {
            for (user in userList) {
                if (user.name.contains(searchText, ignoreCase = true)) {
                    filteredList.add(user)
                }
            }
        }
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
