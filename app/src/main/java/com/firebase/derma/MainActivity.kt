package com.firebase.derma

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.firebase.derma.LoginActivity
import com.firebase.derma.R
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var textFullName: TextView
    private lateinit var textEmail: TextView
    private lateinit var btnLogout: Button
    private lateinit var imageView: ImageView

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imageView.setImageURI(uri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textFullName = findViewById(R.id.full_name)
        textEmail = findViewById(R.id.email)
        btnLogout = findViewById(R.id.btn_logout)
        imageView = findViewById(R.id.image_view)

        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            textFullName.text = firebaseUser.displayName
            textEmail.text = firebaseUser.email
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Mengaktifkan pemilihan foto dari galeri saat tombol dipilih
        findViewById<Button>(R.id.btn_select_photo).setOnClickListener {
            pickImage.launch("image/*")
        }
    }
}
