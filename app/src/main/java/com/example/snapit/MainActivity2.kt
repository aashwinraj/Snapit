package com.example.snapit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity2 : AppCompatActivity() {
    var nameText: EditText? = null
    var email_Text: EditText? = null
    var password_Text: EditText? = null
    var Cnfpassword_Text: EditText? = null
    val mAuth = FirebaseAuth.getInstance()
    fun login() {
        val intent = Intent(this, SnapActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        nameText = findViewById(R.id.edit_text_name)
        email_Text = findViewById(R.id.edit_text_email)
        password_Text = findViewById(R.id.edit_text_password)
        Cnfpassword_Text = findViewById(R.id.edit_text_cnfpassword)
        val registerButton: Button = findViewById(R.id.registration)


        registerButton.setOnClickListener {

if(password_Text?.text.toString()==Cnfpassword_Text?.text.toString()) {
    mAuth.createUserWithEmailAndPassword(
        email_Text?.text.toString(), password_Text?.text.toString()
    ).addOnCompleteListener(this)
    { task ->
        if (task.isSuccessful) {
            task.result?.user?.let { it1 ->
                FirebaseDatabase.getInstance().reference.child("users").child(it1.uid)
                    .child("email")
                    .setValue(email_Text?.text.toString())
            }

            login()
        } else {
            // If sign in fails, display a message to the user.

            Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_LONG).show()

        }

        // ...
    }
}else{Toast.makeText(this,"Passwords don't match",Toast.LENGTH_LONG).show()}

        }


    }


}
