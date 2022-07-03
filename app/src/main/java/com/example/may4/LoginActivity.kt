package com.example.may4

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.may4.cart.CartActivity
import com.example.may4.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var actionBar: ActionBar
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "Login"
        actionBar.setDisplayShowHomeEnabled(true)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.tvMember.setOnClickListener{
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
            finish()
        }
  binding.btnLogin.setOnClickListener {

      validateData()
  }

}
    private fun validateData() {
            email = binding.edtName.text.toString().trim()
            password = binding.edtPwd.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtName.error = "Invalid email"

        }else if (TextUtils.isEmpty(password)) {
            binding.edtPwd.error = "Enter password"
        }else{
            firebaseLogin()
        }
    }
    private fun firebaseLogin() {
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
            progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "logged in as $email",
                    Toast.LENGTH_SHORT).show()

                startActivity(Intent(this,MainActivity::class.java))
                finish()
        }
            .addOnFailureListener{ e->
                progressDialog.dismiss()
                Toast.makeText(this, "login failed! ${e.message}",
                    Toast.LENGTH_SHORT)
                    .show()
            }
    }
    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }

    }


