package com.example.musicstream

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.musicstream.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    lateinit var  binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.loginBtn.setOnClickListener {
            val email = binding.emailEdittext.text.toString()
            val password = binding.passwordEdittext.text.toString()

            if(!Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(),email)){
                binding.emailEdittext.setError("Invalid email")
                return@setOnClickListener
            }

            if(password.length < 6){
                binding.passwordEdittext.setError("Length should be 6 char")
                return@setOnClickListener
            }


            loginWithFirebase(email,password)
        }

        binding.gotoSignupBtn.setOnClickListener {
            startActivity(Intent(this,SignupActivity::class.java))
        }

    }
    fun loginWithFirebase(email : String,password: String){
        setInProgress(true)
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                setInProgress(false)
                startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                finish()
            }.addOnFailureListener {
                setInProgress(false)
                Toast.makeText(applicationContext,"Login account failed", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onResume() {
        super.onResume()
        FirebaseAuth.getInstance().currentUser?.apply {
            startActivity(Intent(this@LoginActivity,MainActivity::class.java))
            finish()
        }
    }

    fun setInProgress(inProgress : Boolean){
        if(inProgress){
            binding.loginBtn.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.loginBtn.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }
}