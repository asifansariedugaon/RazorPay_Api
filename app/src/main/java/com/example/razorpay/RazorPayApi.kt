package com.example.razorpay

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RazorPayApi {

    @GET("customers")
    fun getCustomers(@HeaderMap header: HashMap<String, String>): Observable<RazorPayModel>

    @POST("customers")
    fun postCustomers(@HeaderMap header: HashMap<String, String>, @Body post: PostModel): Observable<PostModel>

    @PUT("customers/{id}")
    fun updateCustomers(
        @HeaderMap header: HashMap<String, String>,
        @Body updateModel: UpdateModel,
        @Path("id") id: String): Observable<UpdateModel>

    companion object Factory {
        fun createRetrofit(): RazorPayApi {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.razorpay.com/v1/")
                .build()
                .create(RazorPayApi::class.java)
            return retrofit
        }
    }
}