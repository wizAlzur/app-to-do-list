package com.example.todolist

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var todos: MutableList<String>
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userData: EditText = binding.userData
        val addButton: Button = binding.addButton
        val listView: ListView = binding.list

        //Создание adapter для ListView
        todos = mutableListOf()
        //Проверка на наличие сохранённых items в ListView
        if (savedInstanceState != null) {
            val savedItems = savedInstanceState.getStringArrayList("todos") ?: emptyList()
            todos.addAll(savedItems)
        }
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, todos)
        listView.adapter = adapter

        //Обработка нажатия на кнопку "добавить"
        addButton.setOnClickListener {
            val inputText: String = userData.text.toString().trim()
            if (inputText != "") {
                adapter.insert(inputText, 0)
            }
            userData.text.clear()
        }

        //Обработка нажатия кнопки Enter
        userData.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {
                val inputText: String = userData.text.toString().trim()
                if (inputText != "") {
                    adapter.insert(inputText, 0)
                }
                userData.text.clear()
                true
            } else {
                false
            }
        }

        //Обработка нажатия на item в ListView
        listView.setOnItemClickListener { _, _, _, id ->
            val text = listView.getItemAtPosition(id.toInt()).toString()
            adapter.remove(text)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("todos", ArrayList(todos))
    }
}