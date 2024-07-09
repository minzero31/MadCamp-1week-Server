package com.example.week2test.ui.gallery

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flask_1.databinding.ItemRecyclerBinding
import com.example.flask_1.databinding.DialogDetailBinding
import com.example.flask_1.ui.gallery.Users

class RecyclerAdapter(private val userList: ArrayList<Users>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(userList[position])
    }

    class ViewHolder(private val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItems(data: Users) {
            binding.nameTv.text = data.name
            binding.emailTv.text = data.email

            itemView.setOnClickListener {
                // 다이얼로그 레이아웃을 인플레이트
                val dialogBinding = DialogDetailBinding.inflate(LayoutInflater.from(itemView.context))

                // 다이얼로그 빌더 생성
                val builder = AlertDialog.Builder(itemView.context)
                builder.setView(dialogBinding.root)

                // 다이얼로그 텍스트 설정
                dialogBinding.dialogMessage.text = "'${data.name}'를 풀어보시겠습니까?"

                // 네 버튼 클릭 리스너 설정
                dialogBinding.dialogButtonYes.setOnClickListener {
                    // TODO: "네" 버튼 클릭 시의 동작 정의
                }

                // 아니오 버튼 클릭 리스너 설정
                dialogBinding.dialogButtonNo.setOnClickListener {
                    // 다이얼로그 닫기
                    dialogBinding.root.parent?.let {
                        (it as? AlertDialog)?.dismiss()
                    }
                }

                // 다이얼로그 보여주기
                val dialog = builder.create()
                dialog.show()
            }
        }
    }
}
