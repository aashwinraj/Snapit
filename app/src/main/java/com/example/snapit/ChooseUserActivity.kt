package com.example.snapit

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class ChooseUserActivity : AppCompatActivity(), UserAdapter.OnItemClickListener {

    var emails: ArrayList<String> = ArrayList()
    var keys: ArrayList<String> = ArrayList()
    var emailEntries: ArrayList<User> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_user)
        val ab = supportActionBar
        ab?.hide()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_user)
        val adapter = UserAdapter(emailEntries, this)
        recyclerView.adapter = adapter
        FirebaseDatabase.getInstance().reference.child("users").addChildEventListener(object :
            ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val email = snapshot?.child("email").value as String
                //emails?.add(email)
                emailEntries.add(User(email))
                keys.add(snapshot.key!!)
                adapter.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}


        })


    }

    override fun onClick(position: Int) {

//        Toast.makeText(this,"item $position clicked",Toast.LENGTH_LONG).show()
        val snapMap: Map<String, String> = mapOf(
            "from" to FirebaseAuth.getInstance().currentUser?.email.toString(),
            "imageName" to intent.getStringExtra("imageName"),
            "ImageUrl" to intent.getStringExtra("imageURL"),
            "message" to intent.getStringExtra("message")
        )
        FirebaseDatabase.getInstance().reference.child("users").child(keys.get(position))
            .child("snaps").push().setValue(snapMap)
        Toast.makeText(this, "Your snap was sent successfully", Toast.LENGTH_SHORT).show()
        finish()
    }

}

