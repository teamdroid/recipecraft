package ru.teamdroid.recipecraft.data

import android.os.Looper
import java.util.concurrent.Executor
import java.util.logging.Handler

class MainRecipeThreadExecutor : Executor {

    val mHandler = android.os.Handler(Looper.getMainLooper())

    override fun execute(command: Runnable) {
        mHandler.post(command);
    }
}