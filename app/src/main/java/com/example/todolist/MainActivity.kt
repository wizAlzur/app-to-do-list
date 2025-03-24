package com.example.todolist

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userData: EditText = binding.userData
        val addButton: Button = binding.addButton
        val listView: ListView = binding.list

        val todos: MutableList<String> = mutableListOf()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, todos)
        listView.adapter = adapter

        addButton.setOnClickListener {
            val text: String = userData.text.toString().trim()

            if (text != "") {
                adapter.insert(text, 0)
            }

            userData.text.clear()
        }

        listView.setOnItemClickListener { _, _, _, id ->
            val text = listView.getItemAtPosition(id.toInt()).toString()
            adapter.remove(text)
        }
    }
}