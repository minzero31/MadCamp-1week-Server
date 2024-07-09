package com.example.flask_1.ui.slideshow

import android.app.AlertDialog
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
import com.example.flask_1.databinding.DialogLogoutConfirmationBinding

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

        val builder = AlertDialog.Builder(context)
        builder.setView(dialogBinding.root)

        dialogBinding.dialogMessage.text = message

        dialogBinding.dialogButtonYes.setOnClickListener {
            // TODO: "예" 버튼 클릭 시의 동작 정의
            dialogBinding.root.parent?.let {
                (it as? AlertDialog)?.dismiss()
            }
        }

        dialogBinding.dialogButtonNo.setOnClickListener {
            // 다이얼로그 닫기
            dialogBinding.root.parent?.let {
                (it as? AlertDialog)?.dismiss()
            }
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
