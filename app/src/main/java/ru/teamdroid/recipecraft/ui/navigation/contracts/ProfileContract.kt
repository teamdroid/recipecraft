package ru.teamdroid.recipecraft.ui.navigation.contracts

import android.content.Intent
import android.net.Uri
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import ru.teamdroid.recipecraft.ui.base.BasePresenter

interface ProfileContract {
    interface View {
        fun showUserSignIn(displayName: String?, photoUrl: Uri?)
        fun showUserSignOut()
        fun signInWithGoogle(signInIntent : Intent)
    }

    interface Presenter : BasePresenter<View> {
        fun signIn()
        fun signOut()
        fun firebaseAuthWithGoogle(account: GoogleSignInAccount?)
    }
}