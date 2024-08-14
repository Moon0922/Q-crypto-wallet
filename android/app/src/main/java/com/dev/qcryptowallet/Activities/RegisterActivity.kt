package com.dev.qcryptowallet.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dev.qcryptowallet.Api.Phone
import com.dev.qcryptowallet.Api.RetrofitInstance
import com.dev.qcryptowallet.Api.User
import com.dev.qcryptowallet.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnNext = findViewById<Button>(R.id.btn_next)
        val edtFirstName = findViewById<EditText>(R.id.first_name)
        val edtSurname = findViewById<EditText>(R.id.surname)
        val edtEmail = findViewById<EditText>(R.id.email)
        val edtCountry = findViewById<EditText>(R.id.country)
        val edtZipcode = findViewById<EditText>(R.id.zipcode)
        val edtPhone = findViewById<EditText>(R.id.phone)
        val edtCountryCode = findViewById<EditText>(R.id.country_code)

        btnNext.setOnClickListener {
            if (edtFirstName.text.toString().isEmpty()) {
                edtFirstName.error = "Firstname cannot be empty"
                return@setOnClickListener
            }

            if (edtSurname.text.toString().isEmpty()) {
                edtSurname.error = "Surname cannot be empty"
                return@setOnClickListener
            }

            if (edtEmail.text.toString().isEmpty()) {
                edtEmail.error = "Email cannot be empty"
                return@setOnClickListener
            }

            if (edtCountry.text.toString().isEmpty()) {
                edtCountry.error = "Country cannot be empty"
                return@setOnClickListener
            }

            if (edtZipcode.text.toString().isEmpty()) {
                edtZipcode.error = "Zipcode cannot be empty"
                return@setOnClickListener
            }

            if (edtPhone.text.toString().isEmpty()) {
                edtPhone.error = "Phone number cannot be empty"
                return@setOnClickListener
            }

            val user = User(
                first_name = edtFirstName.text.toString(),
                last_name = edtSurname.text.toString(),
                phone = edtCountryCode.text.toString() + edtPhone.text.toString(),
                country = edtCountry.text.toString(),
                zipcode = edtZipcode.text.toString(),
                email = edtEmail.text.toString())
            val intent = Intent(this, PhoneVerificationActivity::class.java).apply {
                putExtra("user", user)
            }
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val phone = Phone(phone = edtCountryCode.text.toString() + edtPhone.text.toString())
                    val res = RetrofitInstance.api.phoneVerify(phone)
                    withContext(Dispatchers.Main) {
                        Log.d("MainActivity", "Created User: ${res.res}")
                        if(res.res == "success")
                            startActivity(intent)
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}