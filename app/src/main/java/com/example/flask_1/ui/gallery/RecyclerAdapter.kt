// RecyclerAdapter.kt
package com.example.week2test.ui.gallery

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.flask_1.R
import com.example.flask_1.databinding.ItemRecyclerBinding
import com.example.flask_1.databinding.DialogDetailBinding
import com.example.flask_1.ui.login.Exam

class RecyclerAdapter(
    private val examList: ArrayList<Exam>,
    private val navActionId: Int // 네비게이션 액션 ID를 추가합니다.
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return examList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, navActionId)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(examList[position])
    }

    class ViewHolder(
        private val binding: ItemRecyclerBinding,
        private val navActionId: Int // 네비게이션 액션 ID를 추가합니다.
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindItems(data: Exam) {
            binding.nameTv.text = data.exam_name

            itemView.setOnClickListener {
                // 다이얼로그 레이아웃을 인플레이트
                val dialogBinding = DialogDetailBinding.inflate(LayoutInflater.from(itemView.context))

                // 다이얼로그 빌더 생성
                val builder = AlertDialog.Builder(itemView.context)
                builder.setView(dialogBinding.root)

                // 다이얼로그 텍스트 설정
                dialogBinding.dialogMessage.text = "'${data.exam_name}'를 풀어보시겠습니까?"

                // 다이얼로그 보여주기
                val dialog = builder.create()

                // 네 버튼 클릭 리스너 설정
                dialogBinding.dialogButtonYes.setOnClickListener {
                    val bundle = Bundle().apply {
                        putString("exam_name", data.exam_name)
                        putStringArrayList("questions", ArrayList(data.questions))
                        putSerializable("options", ArrayList(data.options))
                        putStringArrayList("answers", ArrayList(data.answers))
                    }
                    itemView.findNavController().navigate(navActionId, bundle)
                    dialog.dismiss()
                }

                // 아니오 버튼 클릭 리스너 설정
                dialogBinding.dialogButtonNo.setOnClickListener {
                    // 다이얼로그 닫기
                    dialog.dismiss()
                }

                dialog.show()
            }
        }
    }
}
