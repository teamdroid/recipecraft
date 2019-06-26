package ru.teamdroid.recipecraft.ui.navigation.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import ru.teamdroid.recipecraft.data.api.FeedbackMessage
import ru.teamdroid.recipecraft.data.repository.RecipeRepository
import ru.teamdroid.recipecraft.ui.navigation.views.FeedbackView
import ru.teamdroid.recipecraft.util.schedulers.RunOn
import ru.teamdroid.recipecraft.util.schedulers.SchedulerType
import javax.inject.Inject

@InjectViewState
class FeedbackPresenter @Inject constructor(private var repository: RecipeRepository,
                                            @RunOn(SchedulerType.IO) private var ioScheduler: Scheduler,
                                            @RunOn(SchedulerType.UI) private var uiScheduler: Scheduler) : MvpPresenter<FeedbackView>() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun sendReportMessage(name: String, email: String, message: String) {
        compositeDisposable.add(repository.sendReportMessage(FeedbackMessage(name, email, message))
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({ viewState.onSuccess() }, { viewState.onFailure() }))
    }

    override fun destroyView(view: FeedbackView?) {
        super.destroyView(view)
        compositeDisposable.dispose()
    }
}