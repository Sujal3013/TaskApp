package com.example.taskdone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskdone.R
import com.example.taskdone.modal.TodoModel
import kotlinx.android.synthetic.main.activity_task.*
import kotlinx.android.synthetic.main.item_todo.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class Todoadapter(val list :List<TodoModel>):RecyclerView.Adapter<Todoadapter.TodoViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_todo,parent,false))
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(list[position])

    }

    override fun getItemCount(): Int {
         return list.size
    }

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(todoModel: TodoModel) {
            with(itemView){
                val colors=resources.getIntArray(R.array.random_color)
                val randomcolor=colors[Random.nextInt(colors.size)]
                viewcolor.setBackgroundColor(randomcolor)
                txtShowTitle.text=todoModel.title
                txtShowTask.text=todoModel.description
                txtShowCategory.text=todoModel.category
                updateTime(todoModel.time)
                updateDate(todoModel.date)

            }

        }
        private fun updateTime(time:Long) {
            val myformat = "h:mm a"
            val sdf= SimpleDateFormat(myformat)
            itemView.txtShowTime.setText(sdf.format(Date(time)))
        }

        private fun updateDate(date:Long) {
            val myformat = "EEE, d MMM yyyy"
            val sdf= SimpleDateFormat(myformat)
            itemView.txtShowDate.setText(sdf.format(Date(date)))
        }
    }
}