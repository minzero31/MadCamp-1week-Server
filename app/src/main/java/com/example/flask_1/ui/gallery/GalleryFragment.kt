package com.example.flask_1.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flask_1.R
import com.example.flask_1.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerAdapter
    private val userList = arrayListOf<Users>()

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
        adapter = RecyclerAdapter(userList)
        recyclerView.adapter = adapter

        return root
    }

    private fun initializeData() {
        userList.add(Users("Name1", "email1@example.com", "Content1"))
        userList.add(Users("Name2", "email2@example.com", "Content2"))
        userList.add(Users("Name3", "email3@example.com", "Content3"))
        userList.add(Users("Name4", "email3@example.com", "Content4"))
        userList.add(Users("Name5", "email3@example.com", "Content5"))
        userList.add(Users("Name6", "email3@example.com", "Content6"))
        userList.add(Users("Name7", "email3@example.com", "Content7"))
        userList.add(Users("Name8", "email3@example.com", "Content8"))
        // Add more data as needed
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
