package com.example.snapit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class SnapActivity : AppCompatActivity() {
    private val mAuth = FirebaseAuth.getInstance()
    var list_view_snaps: ListView? = null
    var snaps: ArrayList<DataSnapshot>? = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snap)
        var emails: ArrayList<String> = ArrayList()
        list_view_snaps = findViewById(R.id.list_view_snaps)
        val adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, emails)
        list_view_snaps?.adapter = adapter
        FirebaseDatabase.getInstance().reference.child("users").child(mAuth.currentUser?.uid!!).child("snaps")
                .addChildEventListener(object : ChildEventListener {
                    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                        emails.add(snapshot.child("from").value as String)
                        snaps?.add(snapshot)
                        adapter.notifyDataSetChanged()
                    }

                    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
                    override fun onChildRemoved(snapshot: DataSnapshot) {
                        for((index, snap:DataSnapshot) in snaps!!.withIndex())
                        {
                            if (snap.key == snapshot.key) {
                                snaps?.removeAt(index)
                                emails.removeAt(index)

                            }
                        }
                        adapter.notifyDataSetChanged()


                    }
                    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
                    override fun onCancelled(error: DatabaseError) {}
                })
        list_view_snaps?.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            val snapshot = snaps?.get(position)
            val intent = Intent(this, ViewSnapsActivity::class.java)
            intent.putExtra("imageName", snapshot?.child("imageName")?.value as String)
            intent.putExtra("imageUrl", snapshot?.child("ImageUrl")?.value as String)
            intent.putExtra("message", snapshot?.child("message")?.value as String)
            intent.putExtra("snapKey", snapshot?.key)
            startActivity(intent)


        }
        val sendButton: FloatingActionButton = findViewById(R.id.button_send_snap)
        sendButton.setOnClickListener {
            val intent = Intent(this, CreateSnapActivity::class.java)
            startActivity(intent)

        }


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.snaps, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.createSnaps) {

            val intent = Intent(this, CreateSnapActivity::class.java)
            startActivity(intent)

        } else if (item.itemId == R.id.logout) {
            mAuth.signOut()
            finish()

        }

        return super.onOptionsItemSelected(item)
    }
}

//    override fun onBackPressed() {
//        super.onBackPressed()
//        mAuth.signOut()
//    }

