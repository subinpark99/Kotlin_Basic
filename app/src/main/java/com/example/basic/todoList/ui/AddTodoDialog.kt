package com.example.basic.todoList.ui


import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.basic.databinding.DialogTodoAddBinding
import com.example.kotlin.bottom.todo.DialogInterface


@RequiresApi(Build.VERSION_CODES.O)
class AddTodoDialog(context: Context,todoInterface: DialogInterface):
    Dialog(context){

    // 액티비티에서 인터페이스를 받아옴
    private var dialogInterface: DialogInterface = todoInterface
    private lateinit var binding : DialogTodoAddBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogTodoAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView(){

        // 배경 투명하게 바꿔줌
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            binding.todoSaveBtn.setOnClickListener {
            val content =binding.addContentEt.text.toString()

            // 입력하지 않았을 때
            if ( TextUtils.isEmpty(content)){
                Toast.makeText(context, "메모를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }

            // 입력 창이 비어 있지 않을 때
            else{
                // 메모를 추가해줌
                dialogInterface.onAcceptClicked(content)
                dismiss()
            }
        }


        // 취소 버튼 클릭 시 종료
        binding.todoCalcelBtn.setOnClickListener { dismiss()}
    }
}