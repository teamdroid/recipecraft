package ru.teamdroid.recipecraft.ui.navigation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_favorites.toolbar
import kotlinx.android.synthetic.main.fragment_profile.*
import org.jetbrains.anko.image
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.ui.base.BaseFragment
import ru.teamdroid.recipecraft.ui.base.CircleTransform


class ProfileFragment : BaseFragment() {

    override val contentResId = R.layout.fragment_profile

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuthListner: FirebaseAuth.AuthStateListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(toolbar, false, getString(R.string.fragment_profile_title))

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(baseActivity, gso)

        mAuth = FirebaseAuth.getInstance()


        signInTextView.setOnClickListener {
            signIn()
        }

        logoutTextView.setOnClickListener {
            logout()
        }


        settingsTextView.setOnClickListener {
            baseActivity.replaceFragment(SettingsFragment.newInstance(), NavigationFragment.TAG)
        }

        favoritesTextView.setOnClickListener {
            baseActivity.replaceFragment(FavoritesFragment.newInstance(), NavigationFragment.TAG)
        }

        aboutTextView.setOnClickListener {
            baseActivity.replaceFragment(AboutFragment.newInstance(), NavigationFragment.TAG)
        }

        feedbackTextView.setOnClickListener {
            baseActivity.replaceFragment(ReportFragment.newInstance(), NavigationFragment.TAG)
        }

        mAuthListner = FirebaseAuth.AuthStateListener {
            if (it.currentUser != null) {
                usernameTextView.text = mAuth.currentUser?.displayName
                Picasso.with(context)
                        .load(mAuth.currentUser?.photoUrl)
                        .placeholder(R.drawable.ic_placeholder)
                        .transform(CircleTransform())
                        .into(profileImageView)
                signInTextView.visibility = View.GONE
                logoutTextView.visibility = View.VISIBLE
            } else {
                usernameTextView.text = "Неавторизованный"
                profileImageView.image = ContextCompat.getDrawable(context, R.drawable.ic_placeholder)
                signInTextView.visibility = View.VISIBLE
                logoutTextView.visibility = View.GONE
            }
        }

        mAuth.addAuthStateListener(mAuthListner)

    }

    private fun logout() {
        mAuth.signOut()
        mGoogleSignInClient.signOut().addOnCompleteListener {
            Toast.makeText(context, getString(R.string.user_logout_text), Toast.LENGTH_SHORT).show()
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Toast.makeText(context, "Ошибка авторизации", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(baseActivity) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Добро пожаловать, " + mAuth.currentUser?.displayName, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Ошибка авторизации", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    companion object {
        const val RC_SIGN_IN = 123
        fun newInstance() = ProfileFragment()
    }
}