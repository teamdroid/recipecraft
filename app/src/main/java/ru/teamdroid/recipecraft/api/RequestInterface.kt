package ru.teamdroid.recipecraft.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.teamdroid.recipecraft.base.Constants

class RequestInterface {
    companion object {
        fun <T> getRetrofitService(clazz: Class<T>, baseDomain: String = Constants.BASE_DOMAIN): T {
            return Retrofit.Builder()
                    .baseUrl(baseDomain)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(clazz)
        }
    }
}