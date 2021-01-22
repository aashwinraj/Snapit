package com.example.snapit

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class largeSnapActivity : AppCompatActivity() {
    var img : ImageView ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_large_snap)
        img =findViewById(R.id.largeSnap)

    }
}