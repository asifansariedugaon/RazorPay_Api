package com.example.razorpay

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UpdateDataActivity : AppCompatActivity() {

    private lateinit var updateName: EditText
    private lateinit var updateEmail: EditText
    private lateinit var updateContact: EditText
    private lateinit var updateDataBtn: Button
    private var customerId: String? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)

        updateName = findViewById(R.id.updateName_editText)
        updateEmail = findViewById(R.id.updateEmail_editText)
        updateContact = findViewById(R.id.updateContact_editText)
        updateDataBtn = findViewById(R.id.updateBtn_button)

        customerId = intent.getStringExtra("customer_id")
        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val contact = intent.getStringExtra("contact")

        updateName.setText(name)
        updateEmail.setText(email)
        updateContact.setText(contact)

        updateDataBtn.setOnClickListener {
            updateDta()
        }

    }

    @SuppressLint("CheckResult")
    private fun updateDta() {
        val name = updateName.text.toString()
        val email = updateEmail.text.toString()
        val contact = updateContact.text.toString()

        val userKey = "rzp_test_IHo8oE7yKPbPb7"
        val userPassword = "2kvUwgEIB8ROWYfYjuRIXTQz"

        val authorized = "Basic " + android.util.Base64.encodeToString(
            "$userKey:$userPassword".toByteArray(),
            android.util.Base64.NO_WRAP
        )

        val headerMap = HashMap<String, String>()
        headerMap["Authorization"] = authorized

        val updateCustomerItem = UpdateModel(contact, email, name)

        RazorPayApi.createRetrofit()
            .updateCustomers(headerMap, updateCustomerItem, customerId.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                // Success block
                Toast.makeText(this, "Customer Item Updated", Toast.LENGTH_SHORT).show()
                finish()
            }, { error ->
                // Error block
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                Log.e("API Error", "Error updating customer item", error)
            })
        
    }

}