package com.example.razorpay

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val context: Context, private val list: List<Item>):
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val id :TextView = itemView.findViewById(R.id.userId_textView)
        val uname :TextView = itemView.findViewById(R.id.userName_textView)
        val email :TextView = itemView.findViewById(R.id.userEmail_textView)
        val contact :TextView = itemView.findViewById(R.id.userContact_textView)
//        val gstin :TextView = itemView.findViewById(R.id.usergstin_textView)
//        val entity :TextView = itemView.findViewById(R.id.userEntity_textView)
        val updateUser:ImageView = itemView.findViewById(R.id.updateImg_imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.itemlist,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.id.text =list[position].id
        holder.uname.text =list[position].name
        holder.email.text =list[position].email
        holder.contact.text =list[position].contact
//        holder.entity.text =list[position].entity
//        holder.gstin.text =""

        holder.updateUser.setOnClickListener {
            val intent = Intent(context,UpdateDataActivity::class.java)
            intent.putExtra("customer_id",list[position].id)
            intent.putExtra("name",list[position].name)
            intent.putExtra("email",list[position].email)
            intent.putExtra("contact",list[position].contact)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
      return list.size
    }
}