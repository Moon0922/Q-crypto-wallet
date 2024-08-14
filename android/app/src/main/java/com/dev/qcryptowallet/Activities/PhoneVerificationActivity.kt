package com.dev.qcryptowallet.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dev.qcryptowallet.Api.CheckCode
import com.dev.qcryptowallet.Api.Phone
import com.dev.qcryptowallet.Api.RetrofitInstance
import com.dev.qcryptowallet.Api.User
import com.dev.qcryptowallet.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("DEPRECATION")
class PhoneVerificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_phone_verification)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var user = intent.getSerializableExtra("user") as User
        Log.d("PhoneVerificationActivity", "user: ${user}")

        val btnVerify = findViewById<Button>(R.id.btn_verify)
        val edtCode = findViewById<EditText>(R.id.code)


        btnVerify.setOnClickListener {
            if(edtCode.text.toString().isEmpty()){
                edtCode.error = "Code cannot be empty"
                return@setOnClickListener
            }
            val intent = Intent(this, MainActivity::class.java).apply {}
            var flag = false
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val checkCode = CheckCode(phone = user.phone, code = edtCode.text.toString())
                    val res = RetrofitInstance.api.checkCode(checkCode)
                    withContext(Dispatchers.Main) {
                        Log.d("PhoneVerificationActivity", "res: ${res.res}")
                        if(res.res == "success")
                        {
                            GlobalScope.launch(Dispatchers.IO) {
                                try {
                                    val u = RetrofitInstance.api.register(user)
                                    withContext(Dispatchers.Main) {
                                        Log.d("PhoneVerificationActivity", "res: ${u.res}")
                                        if(u.res == "success")
                                        {
                                            startActivity(intent)
                                        }
                                    }

                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }else{
                            //Toast.makeText(this, "The verification code is not correct.", Toast.LENGTH_SHORT).show().
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}