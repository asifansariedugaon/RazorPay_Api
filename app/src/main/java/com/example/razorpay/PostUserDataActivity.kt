package com.example.razorpay

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PostUserDataActivity : AppCompatActivity() {

    private lateinit var addName: EditText
    private lateinit var addEmail: EditText
    private lateinit var addContact: EditText
    private lateinit var addDataBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_user_data)

        addName = findViewById(R.id.addName_editText)
        addEmail = findViewById(R.id.addEmail_editText)
        addContact = findViewById(R.id.addContact_editText)
        addDataBtn = findViewById(R.id.addData_button)

        addDataBtn.setOnClickListener {
            addUser()
        }
    }

    @SuppressLint("CheckResult")
    fun addUser() {
        val name = addName.text.toString()
        val email = addEmail.text.toString()
        val contact = addContact.text.toString()
        val postData = PostModel(
            contact, email, name, "12ABCDE2356F7GH", "1",
            NotesX("Tea, Earl Grey, Hot", "Tea, Earl Grey… decaf.")
        )

        val apiKey = "rzp_test_IHo8oE7yKPbPb7"
        val apiSecret = "2kvUwgEIB8ROWYfYjuRIXTQz"
        val authenticator = "Basic " + android.util.Base64.encodeToString(
            "$apiKey:$apiSecret".toByteArray(), android.util.Base64.NO_WRAP
        )

        val headerMap = HashMap<String, String>()
        headerMap["Authorization"] = authenticator

        RazorPayApi.createRetrofit().postCustomers(headerMap,postData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                Toast.makeText(this, "${result}", Toast.LENGTH_SHORT).show()
                finish()
            }
    }
}
