package com.example.snapit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import java.util.ArrayList

class ChooseUserActivity : AppCompatActivity() {
    var listViewUsers:ListView?=null
     var emails:ArrayList<String> = ArrayList()
    var keys:ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_user)
        val ab=supportActionBar
        ab?.hide()
        listViewUsers=findViewById(R.id.list_view_users)
        val adapter=ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,emails)
        listViewUsers?.adapter=adapter
        FirebaseDatabase.getInstance().reference.child("users").addChildEventListener(object :ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val email=snapshot?.child("email").value as String
                emails?.add(email)
                keys.add(snapshot.key!!)
                adapter.notifyDataSetChanged()
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}


        })

        listViewUsers?.onItemClickListener=AdapterView.OnItemClickListener { parent, view, position, id ->

         val snapMap:Map<String,String> = mapOf("from" to FirebaseAuth.getInstance().currentUser?.email.toString(),"imageName" to intent.getStringExtra("imageName"),"ImageUrl" to intent.getStringExtra("imageURL"), "message" to intent.getStringExtra("message"))
            FirebaseDatabase.getInstance().reference.child("users").child(keys.get(position)).child("snaps").push().setValue(snapMap)
            Toast.makeText(this, "Your snap was sent successfully", Toast.LENGTH_SHORT).show()
finish()
        }
    }
}