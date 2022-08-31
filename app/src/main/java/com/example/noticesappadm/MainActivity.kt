package com.example.noticesappadm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.noticesappadm.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        binding.editNoticeButton.setOnClickListener{
            val title = binding.editNoticeTitle.text.toString()
            val notice = binding.editNoticeText.text.toString()
            val date = binding.editNoticeDate.text.toString()
            val author = binding.editNoticeAutor.text.toString()

            if (title.isEmpty() || notice.isEmpty() || date.isEmpty() || author.isEmpty()){
                Toast.makeText(this,"Write an notice !",Toast.LENGTH_SHORT).show()
            }else{
                saveNotice(title,notice,date,author)
            }
        }
    }

    private fun saveNotice(title: String, notice: String, date: String, author: String){

        val mapNotices = hashMapOf(
            "title" to title,
            "notice" to notice,
            "date" to date,
            "author" to author
        )

        db.collection("notices").document("notice")
            .set(mapNotices).addOnCompleteListener{ task ->
                if (task.isSuccessful){
                    Toast.makeText(this,"Published with success!", Toast.LENGTH_SHORT).show()
                    cleanFields()
                }
            }.addOnFailureListener{

            }
    }

    private fun cleanFields(){
        binding.editNoticeAutor.setText("")
        binding.editNoticeTitle.setText("")
        binding.editNoticeText.setText("")
        binding.editNoticeDate.setText("")
    }
}