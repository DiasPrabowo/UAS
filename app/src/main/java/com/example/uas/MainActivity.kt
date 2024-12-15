package com.example.uas

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_EMAIL = "extra_email"
        const val EXTRA_PHONE = "extra_phone"
        const val EXTRA_PASSWORD = "extra_password"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupEdgeToEdge()
        with(binding) {
            btnRegister.setOnClickListener {
                if (validateInput()) {

                    // Create intent and pass the data
                    val intentToSecondActivity = Intent(this@MainActivity, SecondActivity::class.java)
                    intentToSecondActivity.putExtra(EXTRA_USERNAME, editUsername.text.toString())
                    intentToSecondActivity.putExtra(EXTRA_EMAIL, editEmail.text.toString())
                    intentToSecondActivity.putExtra(EXTRA_PHONE, editPhone.text.toString())
                    intentToSecondActivity.putExtra(EXTRA_PASSWORD, editPassword.text.toString())
                    startActivity(intentToSecondActivity)
                }
            }
        }
    }

    // Function to handle Edge-to-Edge UI
    private fun setupEdgeToEdge() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemInsets.left, systemInsets.top, systemInsets.right, systemInsets.bottom)
            WindowInsetsCompat.CONSUMED
        }
    }

    // Function to validate input fields
    private fun validateInput(): Boolean {
        with(binding) {
            when {
                editUsername.text.isNullOrEmpty() -> {
                    Toast.makeText(this@MainActivity, "Please enter a username", Toast.LENGTH_SHORT).show()
                    return false
                }
                editEmail.text.isNullOrEmpty() -> {
                    Toast.makeText(this@MainActivity, "Please enter an email", Toast.LENGTH_SHORT).show()
                    return false
                }
                editPhone.text.isNullOrEmpty() -> {
                    Toast.makeText(this@MainActivity, "Please enter a phone number", Toast.LENGTH_SHORT).show()
                    return false
                }
                editPassword.text.isNullOrEmpty() -> {
                    Toast.makeText(this@MainActivity, "Please enter a password", Toast.LENGTH_SHORT).show()
                    return false
                }
                else -> return true
            }
        }
    }
}
