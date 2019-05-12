package ru.teamdroid.recipecraft.ui.navigation.modules

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.ui.base.ActivityScope
import ru.teamdroid.recipecraft.ui.navigation.contracts.ProfileContract

@Module
class ProfilePresenterModule(private val view: ProfileContract.View) {

    @Provides
    fun provideView(): ProfileContract.View {
        return view
    }

    @Provides
    @ActivityScope
    internal fun provideGoogleSignInClient(context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        return GoogleSignIn.getClient(context, gso)
    }

    @Provides
    @ActivityScope
    internal fun provideFirebaseAuth() = FirebaseAuth.getInstance()

}