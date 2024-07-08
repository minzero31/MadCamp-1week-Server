package com.example.flask_1.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.flask_1.R
import com.example.flask_1.databinding.FragmentSlideshowBinding

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // View elements
        val title: TextView = binding.title
        val userIcon: ImageView = binding.userIcon
        val userId: TextView = binding.userId
        val userEmail: TextView = binding.userEmail
        val btnViewMyQuestions: Button = binding.btnViewMyQuestions
        val btnLogout: Button = binding.btnLogout

        // Set any specific properties or listeners
        btnViewMyQuestions.setOnClickListener {
            // Handle "내가 만든 문제 보러 가기" button click
        }

        btnLogout.setOnClickListener {
            // Handle "로그아웃" button click
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
