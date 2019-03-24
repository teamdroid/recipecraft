package ru.teamdroid.recipecraft.ui.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleRegistry

abstract class BaseActivity : AppCompatActivity() {

    private val lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }
}