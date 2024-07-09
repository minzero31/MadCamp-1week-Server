package com.example.flask_1.ui.slideshow

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.flask_1.R
import com.example.flask_1.databinding.DialogLogoutConfirmationBinding
import com.example.flask_1.databinding.FragmentSlideshowBinding
import com.example.flask_1.ui.login.LoginActivity

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
            showConfirmationDialog("내가 만든 퀴즈를 보러가시겠습니까?")
        }

        btnLogout.setOnClickListener {
            showConfirmationDialog("로그아웃 하시겠습니까?")
        }

        return root
    }

    private fun showConfirmationDialog(message: String) {
        val dialogBinding = DialogLogoutConfirmationBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogBinding.root)

        dialogBinding.dialogMessage.text = message

        val dialog = builder.create()

        dialogBinding.dialogButtonYes.setOnClickListener {
            if (message.contains("로그아웃")) {
                handleLogout()
            } else if (message.contains("내가 만든 퀴즈를 보러가시겠습니까?")) {
                findNavController().navigate(R.id.action_slideshowFragment_to_myProbFragment)
            }
            dialog.dismiss()
        }

        dialogBinding.dialogButtonNo.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun handleLogout() {
        // 세션 정보 초기화
        clearSession()

        // 로그인 화면으로 이동
        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun clearSession() {
        // SharedPreferences를 사용하여 세션 정보를 초기화합니다.
        val sharedPref = activity?.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        sharedPref?.edit()?.clear()?.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
