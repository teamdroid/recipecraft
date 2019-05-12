package ru.teamdroid.recipecraft.ui.navigation.presenters

import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import ru.teamdroid.recipecraft.data.api.ReportMessage
import ru.teamdroid.recipecraft.data.repository.RecipeRepository
import ru.teamdroid.recipecraft.ui.navigation.contracts.ReportContract
import ru.teamdroid.recipecraft.util.schedulers.RunOn
import ru.teamdroid.recipecraft.util.schedulers.SchedulerType
import javax.inject.Inject

class ReportPresenter @Inject constructor(private var repository: RecipeRepository,
                                          private var view: ReportContract.View,
                                          @RunOn(SchedulerType.IO) private var ioScheduler: Scheduler,
                                          @RunOn(SchedulerType.UI) private var uiScheduler: Scheduler) : ReportContract.Presenter {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun sendReportMessage(name: String, email: String, message: String) {
        compositeDisposable.add(repository.sendReportMessage(ReportMessage(name, email, message))
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({ view.onSuccess() }, { view.onFailure() }))
    }

    override fun onAttachView() { }

    override fun onDetachView() { }

    override fun onDestroy() {
        compositeDisposable.clear()
    }
}