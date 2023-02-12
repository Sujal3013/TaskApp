package com.example.taskdone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskdone.Database.todoDatabase
import com.example.taskdone.adapter.Todoadapter
import com.example.taskdone.modal.TodoModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val list= arrayListOf<TodoModel>()
    var adapter=Todoadapter(list)

    val db by lazy{
        todoDatabase.getDatabase(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        todoRv.apply {
            layoutManager=LinearLayoutManager(this@MainActivity)
            adapter=this@MainActivity.adapter
        }
        db.todoDao().getTask().observe(this, Observer {
            if(it.isNullOrEmpty()){
                list.clear()
                list.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.history->{
                startActivity(Intent(this,Historyacitivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

}