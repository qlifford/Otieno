package com.example.may4

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.may4.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

    class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var actionBar: ActionBar
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""
    var tvLog: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tvLog = findViewById(R.id.tvLog)
         actionBar = supportActionBar!!
          actionBar.title = "Login"
         actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Creating account In...")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.tvLog.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()

        }
        binding.btnReg.setOnClickListener {
            validateData()
        }
    }
    private fun validateData() {
        email = binding.edtName.text.toString().trim()
        password = binding.edtPwd.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.edtName.error = "Invalid email"

        }else if (TextUtils.isEmpty(password)) {
            binding.edtPwd.error = "Enter password"

        }else if (password.length <2){
            binding.edtPwd.error = "Enter at least two characters"

        }else{
            firebaseSignUp()
        }
    }
    private fun firebaseSignUp() {
      progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
              //  Toast.makeText(this,"Account created with $email",
                //    Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener{ e->
                progressDialog.dismiss()
                Toast.makeText(this,"SignUp failed ! ${e.message}",
                    Toast.LENGTH_SHORT).show()

            }
    }
            override fun onBackPressed() {
                super.onBackPressed()
            }

}
