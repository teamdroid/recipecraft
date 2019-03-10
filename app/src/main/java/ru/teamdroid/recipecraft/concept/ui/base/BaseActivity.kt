package ru.teamdroid.recipecraft.concept.ui.base

import android.arch.lifecycle.LifecycleRegistry
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    private val lifecycleRegistry = LifecycleRegistry(this)


    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }
}