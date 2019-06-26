package ru.teamdroid.recipecraft.ui.navigation.views

import android.content.Intent
import android.net.Uri
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ProfileView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun showUserSignIn(displayName: String?, photoUrl: Uri?)

    @StateStrategyType(SkipStrategy::class)
    fun showUserSignOut()

    @StateStrategyType(SkipStrategy::class)
    fun signInWithGoogle(signInIntent: Intent)
}