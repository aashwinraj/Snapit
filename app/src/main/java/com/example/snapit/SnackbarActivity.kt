package com.example.snapit

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

open class SnackbarActivity : AppCompatActivity() {


    private lateinit var mProgressDialog: Dialog
//    fun showSnackBar(message: String, errorMessage: Boolean){
//
//        var snackbar = Snackbar.make(this,findViewById(android.R.id.content),message,Snackbar.LENGTH_LONG)
//        val snackBarView= snackbar.view
//
//        if(errorMessage){
//            snackBarView.setBackgroundColor(ContextCompat.getColor(this,R.color.pink))
//        }
//
//        else{
//            snackBarView.setBackgroundColor(ContextCompat.getColor(this,R.color.pink))
//        }
//        snackbar.show()
//
//
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snackbar)
    }
    fun showProressDialogue(){
        mProgressDialog = Dialog(this)

        mProgressDialog.setContentView(R.layout.dialouge_progress)
        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()

    }
    fun hideProgressDialogue(){
        mProgressDialog.dismiss()
    }
}