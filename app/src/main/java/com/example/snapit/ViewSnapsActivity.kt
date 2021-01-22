package com.example.snapit

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.net.HttpURLConnection
import java.net.URL

class ViewSnapsActivity : AppCompatActivity() {

    var snapImage:ImageView? =null
    var message :TextView? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_snaps)
        snapImage=findViewById(R.id.snap_image)
        message=findViewById(R.id.snap_message)


        message?.text= intent.getStringExtra("message")

        val task = ImageDownloader()
        var myImage: Bitmap? = null
        try {
            myImage = task.execute(intent.getStringExtra("imageUrl")).get()
            snapImage?.setImageBitmap(myImage)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }



    }

      inner class ImageDownloader : AsyncTask<String,Void,Bitmap>() {
         override fun doInBackground(vararg urls: String): Bitmap? {
             return try{
                 val url = URL(urls[0])
                 val connection = url.openConnection() as HttpURLConnection
                 connection.connect()
                 val `in` = connection.inputStream
                 BitmapFactory.decodeStream(`in`)
             } catch (e: Exception) {
                 e.printStackTrace()
                 null
             }
        }
    }
    val mAuth=FirebaseAuth.getInstance()

    override fun onBackPressed() {
        super.onBackPressed()
        FirebaseDatabase.getInstance().reference.child("users").child(mAuth.currentUser?.uid!!)
                .child("snaps").child(intent.getStringExtra("snapKey")).removeValue()


    }


}