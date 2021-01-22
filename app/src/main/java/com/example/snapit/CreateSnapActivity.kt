package com.example.snapit

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.net.URI
import java.util.*


class CreateSnapActivity : AppCompatActivity() {

    private  val PICK_IMAGE = 100
    var imageUri: Uri? = null
    private val imageName= UUID.randomUUID().toString()+".jpg"

    private var imageView: ImageView?=null
    private var editText: EditText?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_snap)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        imageView = findViewById(R.id.imageSnap)
        val button: Button =  findViewById(R.id.buttonAdd)
        editText=findViewById(R.id.editTextAddText)

        button.setOnClickListener {

            openGallery()
        }

    }

    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            if (data != null) {
                imageUri = data.data
            };
            imageView?.setImageURI(imageUri);
        }


        val nextButton: Button =findViewById(R.id.nextButton)
        nextButton.setOnClickListener {

            // Get the data from an ImageView as bytes
//           imageView!!.isDrawingCacheEnabled = true
//           imageView!!.buildDrawingCache()
            val bitmap = (imageView?.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()
            FirebaseStorage.getInstance().reference.child("images").child(imageName)
            var uploadTask = FirebaseStorage.getInstance().reference.child("images").child(imageName)
                .putBytes(data)
            uploadTask.addOnFailureListener {
                // Handle unsuccessful uploads
                Toast.makeText(this, "upload Failed", Toast.LENGTH_LONG).show()
            }.addOnSuccessListener { taskSnapshot ->
                // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                // ...
                //val downloadUrl=taskSnapshot.d
                //val downloadUrl = taskSnapshot.storage.downloadUrl
                val task = taskSnapshot.metadata!!.reference!!.downloadUrl
                task.addOnSuccessListener { uri ->
                    val photoLink = uri.toString()


                    val intent = Intent(this, ChooseUserActivity::class.java)
                    intent.putExtra("imageURL", photoLink)
                    intent.putExtra("imageName", imageName)
                    intent.putExtra("message", editText?.text.toString())
                    startActivity(intent)
                }
            }

        }
    }
}