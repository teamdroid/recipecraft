package ru.teamdroid.recipecraft.ui.navigation.presenters

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import io.reactivex.disposables.CompositeDisposable
import ru.teamdroid.recipecraft.ui.navigation.fragments.ProfileFragment
import ru.teamdroid.recipecraft.ui.navigation.views.ProfileView
import javax.inject.Inject

@InjectViewState
class ProfilePresenter @Inject constructor(private var googleSignInClient: GoogleSignInClient,
                                           private var firebaseAuth: FirebaseAuth) : MvpPresenter<ProfileView>() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var firebaseAuthListener: FirebaseAuth.AuthStateListener = FirebaseAuth.AuthStateListener {
        if (it.currentUser != null) {
            viewState.showUserSignIn(it.currentUser?.displayName, it.currentUser?.photoUrl)
        } else {
            viewState.showUserSignOut()
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
        googleSignInClient.signOut().addOnCompleteListener { viewState.showUserSignOut() }
    }

    fun signIn() {
        viewState.signInWithGoogle(googleSignInClient.signInIntent)
    }

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(ProfileFragment.TAG, it.result.toString())
            } else {
                Log.d(ProfileFragment.TAG, it.result.toString())
            }
        }
    }

    override fun attachView(view: ProfileView?) {
        super.attachView(view)
        firebaseAuth.also {
            if (it.currentUser != null) {
                viewState.showUserSignIn(it.currentUser?.displayName, it.currentUser?.photoUrl)
            } else {
                viewState.showUserSignOut()
            }
        }
        firebaseAuth.addAuthStateListener(firebaseAuthListener)
    }

    override fun destroyView(view: ProfileView?) {
        super.destroyView(view)
        firebaseAuth.removeAuthStateListener(firebaseAuthListener)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}