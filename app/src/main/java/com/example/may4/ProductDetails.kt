package com.example.may4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class ProductDetails : AppCompatActivity() {
    var available: TextView? = null
    var photo: ImageView? = null

    private var productName:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_details)

        productName = findViewById(R.id.product_name)
        available = findViewById(R.id.availability)
        photo = findViewById(R.id.photo)


        val title = intent.getStringExtra("title")
        val photoUrl = intent.getStringExtra("photo_url")
        productName!!.text = title
        Picasso.get().load(photoUrl).into(photo)


        available!!.setOnClickListener {
            android.app.AlertDialog.Builder(this)
                .setMessage("Hi, $title is Available!")
                .setPositiveButton("OK"
                ) { _, _ ->
                }
                .create()
                .show()
        }


    }

}