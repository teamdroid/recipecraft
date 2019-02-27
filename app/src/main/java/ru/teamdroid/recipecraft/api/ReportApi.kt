package ru.teamdroid.recipecraft.api

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import ru.teamdroid.recipecraft.data.ReportMessage

interface ReportApi {
    @POST("sendReportMessage")
    fun sendReportMessage(@Body reportMessage: ReportMessage): Observable<Response>
}
