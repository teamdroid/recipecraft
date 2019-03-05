package ru.teamdroid.recipecraft.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.teamdroid.recipecraft.api.ReportApi
import ru.teamdroid.recipecraft.api.RequestInterface
import ru.teamdroid.recipecraft.data.ReportMessage
import ru.teamdroid.recipecraft.views.ReportView

@InjectViewState
class ReportPresenter : MvpPresenter<ReportView>() {

    private var compositeDisposable = CompositeDisposable()

    fun sendReportMessage(name: String, email: String, message: String) {
        compositeDisposable.add(RequestInterface.getRetrofitService(ReportApi::class.java).sendReportMessage(ReportMessage(name, email, message))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(({ viewState.onSuccess() }), ({ viewState.onFailure() })
                ))
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}