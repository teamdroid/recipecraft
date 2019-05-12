package ru.teamdroid.recipecraft.ui.navigation.presenters

import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import io.reactivex.disposables.CompositeDisposable
import ru.teamdroid.recipecraft.ui.navigation.contracts.ProfileContract
import ru.teamdroid.recipecraft.ui.navigation.fragments.ProfileFragment
import javax.inject.Inject

class ProfilePresenter @Inject constructor(private var googleSignInClient: GoogleSignInClient,
                                           private var firebaseAuth: FirebaseAuth,
                                           private var view: ProfileContract.View) : ProfileContract.Presenter {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var firebaseAuthListener: FirebaseAuth.AuthStateListener = FirebaseAuth.AuthStateListener {
        if (it.currentUser != null) {
            view.showUserSignIn(it.currentUser?.displayName, it.currentUser?.photoUrl)
        } else {
            view.showUserSignOut()
        }
    }

    override fun signOut() {
        firebaseAuth.signOut()
        googleSignInClient.signOut().addOnCompleteListener { view.showUserSignOut() }
    }

    override fun signIn() {
        view.signInWithGoogle(googleSignInClient.signInIntent)
    }

    override fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(ProfileFragment.TAG, it.result.toString())
            } else {
                Log.d(ProfileFragment.TAG, it.result.toString())
            }
        }
    }

    override fun onAttachView() {
        firebaseAuth.addAuthStateListener(firebaseAuthListener)
    }

    override fun onDetachView() {
        firebaseAuth.removeAuthStateListener(firebaseAuthListener)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}