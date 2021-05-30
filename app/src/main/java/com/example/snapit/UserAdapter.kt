package com.example.snapit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(val emails: ArrayList<User>,val Listener : OnItemClickListener
) :
    RecyclerView.Adapter<UserAdapter.Viewholder>()
{





   inner  class Viewholder( view: View ): RecyclerView.ViewHolder(view), View.OnClickListener
   {

       var emailText: TextView = view.findViewById(R.id.textView_userEmail)
       val userImage: ImageView = view.findViewById(R.id.user_dp)

       init {
           view.setOnClickListener(this)
       }

       override fun onClick(v: View?) {
           val position = adapterPosition
           if(position!= RecyclerView.NO_POSITION)
           Listener.onClick(position)
       }
   }

       interface OnItemClickListener
       {
           fun onClick(position:Int)
       }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_entry, parent, false)

        return Viewholder(view)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {

holder.emailText?.text= emails[position].toString()
    }

    override fun getItemCount(): Int {
return emails.size    }


}