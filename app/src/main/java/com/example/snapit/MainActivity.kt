package com.example.snapit

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    var emailText: EditText? = null
    var passwordText: EditText? = null

    override fun getSupportActionBar(): ActionBar? {
        if (supportActionBar != null)
            supportActionBar?.hide()
        return super.getSupportActionBar()
    }

    private val mAuth = FirebaseAuth.getInstance()

    private fun login() {

        val intent = Intent(this, SnapActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        emailText = findViewById(R.id.edit_text_email)
        passwordText = findViewById(R.id.edit_text_password)
        val register: Button = findViewById(R.id.button2)
        val button1: Button = findViewById(R.id.button_login)

        register.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        if (mAuth.currentUser != null)
            login()

        button1.setOnClickListener {
            mAuth.signInWithEmailAndPassword(emailText?.text.toString(), passwordText?.text.toString()).addOnCompleteListener(this)
            { task ->
                    if (task.isSuccessful) {
                        login()
                    } else {
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    // ...
                }
        }


    }


}