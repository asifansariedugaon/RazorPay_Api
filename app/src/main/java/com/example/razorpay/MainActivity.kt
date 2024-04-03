package com.example.razorpay

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val userList = ArrayList<Item>()
    private lateinit var userAdapter: UserAdapter
    private lateinit var floatButton: FloatingActionButton

    @SuppressLint("MissingInflatedId", "CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.userList_recyclerView)
        floatButton = findViewById(R.id.floatButton)

        floatButton.setOnClickListener {
            startActivity(Intent(this, PostUserDataActivity::class.java))
        }


    }

    @SuppressLint("CheckResult")
    fun getItem() {
        val apiKey = "rzp_test_IHo8oE7yKPbPb7"
        val apiPassword = "2kvUwgEIB8ROWYfYjuRIXTQz"

        val authHeader =
            "Basic" + Base64.encodeToString("$apiKey:$apiPassword".toByteArray(), Base64.NO_WRAP)
        val headerMap = HashMap<String, String>()
        headerMap["Authorization"] = authHeader

        RazorPayApi.createRetrofit().getCustomers(headerMap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                val allData = result.items
                userList.clear()
                if (allData != null) {
                    userList.addAll(allData)
                }
                Toast.makeText(this, "${result.count}", Toast.LENGTH_SHORT).show()
                userAdapter = UserAdapter(this, userList)
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = userAdapter

            }
    }

    override fun onResume() {
        super.onResume()
        getItem()
    }
}