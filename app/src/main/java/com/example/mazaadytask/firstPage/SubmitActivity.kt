package com.example.mazaadytask.firstPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.mazaadytask.R

class SubmitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit)
        val bundle: Bundle? = intent.extras
        val myHashMap = HashMap<String, String>()
        val t = findViewById<TextView>(R.id.textView2)

        bundle?.let {
            for (key in it.keySet()) {
                myHashMap[key] = it.getString(key) ?: ""

                t.append(key +": \t"+ it.getString(key) + "\n")
            }
        }
    }

}