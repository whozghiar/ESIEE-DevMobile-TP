package fr.unilasalle.androidtp.API

import fr.unilasalle.androidtp.beans.Product
import fr.unilasalle.androidtp.services.RetrofitService
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAPI {

    private const val BASE_URL = "https://fakestoreapi.com/"

    fun getService():RetrofitService{
        val retrofitBuilder = Retrofit.Builder()
        val okHttp = OkHttpClient.Builder().build()

        retrofitBuilder.client(okHttp)

        val retrofit = retrofitBuilder
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(RetrofitService::class.java)

    }




}