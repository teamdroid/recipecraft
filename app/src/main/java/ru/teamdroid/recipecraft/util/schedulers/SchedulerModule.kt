package ru.teamdroid.recipecraft.util.schedulers

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import ru.teamdroid.recipecraft.util.schedulers.SchedulerType.IO
import ru.teamdroid.recipecraft.util.schedulers.SchedulerType.UI

@Module
class SchedulerModule {

    @Provides
    @RunOn(IO)
    internal fun provideIo(): Scheduler {
        return Schedulers.io()
    }

    @Provides
    @RunOn(UI)
    internal fun provideUi(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}