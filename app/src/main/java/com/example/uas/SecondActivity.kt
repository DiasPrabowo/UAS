package com.example.uas

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uas.MainActivity.Companion.EXTRA_EMAIL
import com.example.uas.MainActivity.Companion.EXTRA_PASSWORD
import com.example.uas.MainActivity.Companion.EXTRA_PHONE
import com.example.uas.MainActivity.Companion.EXTRA_USERNAME
import com.example.uas.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usernameReg = intent.getStringExtra(EXTRA_USERNAME)
        val passwordReg = intent.getStringExtra(EXTRA_PASSWORD)
        val emailReg = intent.getStringExtra(EXTRA_EMAIL)
        val phoneReg = intent.getStringExtra(EXTRA_PHONE)

        with(binding) {
            btnLogin.setOnClickListener {
                val usernameLogin = editUsernameLogin.text.toString().trim()
                val passwordLogin = editPasswordLogin.text.toString().trim()

                // Hide keyboard after login button is clicked
                hideKeyboard()

                // Check for empty fields
                if (usernameLogin.isEmpty() || passwordLogin.isEmpty()) {
                    Toast.makeText(this@SecondActivity, "Please fill in both username and password!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Compare the entered credentials with registered ones
                if (usernameReg == usernameLogin && passwordReg == passwordLogin) {
                    val intentToThirdActivity = Intent(this@SecondActivity, ThirdActivity::class.java).apply {
                        putExtra(EXTRA_USERNAME, usernameReg)
                        putExtra(EXTRA_EMAIL, emailReg)
                        putExtra(EXTRA_PHONE, phoneReg)
                    }
                    startActivity(intentToThirdActivity)
                } else {
                    txtWarning.text = "Incorrect username or password!"
                }
            }
        }
    }

    // Function to hide the keyboard after the user clicks the login button
    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val view = currentFocus ?: binding.root
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
