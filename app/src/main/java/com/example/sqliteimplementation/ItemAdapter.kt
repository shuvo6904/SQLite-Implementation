package com.example.sqliteimplementation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(val context : Context, val items : ArrayList<EmpModelClass>) :  RecyclerView.Adapter<ItemAdapter.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items.get(position)
        holder.tvName.text = item.name
        holder.tvEmail.text = item.email
        holder.symbol.text = ":"

        if(position % 2 == 0) {
            holder.llMain.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorLightGray
                )
            )
        } else {
            holder.llMain.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite))
        }

        holder.ivDelete.setOnClickListener { view ->

            if (context is MainActivity) {
                context.deleteRecordAlertDialog(item)
            }
        }
        holder.ivEdit.setOnClickListener { view ->

            if (context is MainActivity) {
                context.updateRecordDialog(item)
            }
        }


    }

    override fun getItemCount(): Int {
        return items.size
    }


    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        val llMain = view.findViewById<LinearLayout>(R.id.llMain)
        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvEmail = view.findViewById<TextView>(R.id.tvEmail)
        val ivEdit = view.findViewById<ImageView>(R.id.ivEdit)
        val ivDelete = view.findViewById<ImageView>(R.id.ivDelete)
        val symbol = view.findViewById<TextView>(R.id.symbolId)

    }
}




























