package com.apps.youtube.api

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import kotlin.coroutines.cancellation.CancellationException

private const val TAG = "GoogleAuthClient"

class GoogleAuthClient(
    private val context: Context,
    private val oneTapClient: SignInClient,
) {
    private val auth = Firebase.auth

    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(buildSignInRequest).await()
        } catch (e: Exception) {
            e.printStackTrace()
            Timber.tag(TAG).d("signIn():: ${e.message}")
            if (e is CancellationException) throw e
            null
        }

        return result?.pendingIntent?.intentSender
    }

    suspend fun signInWithIntent(intent: Intent): SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)

        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        userName = displayName,
                        profilePictureUrl = photoUrl?.toString()
                    )
                }, errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()

            if (e is CancellationException) throw e
            SignInResult(
                data = null, errorMessage = e.message
            )
        }
    }

    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    val getSignedInUser: UserData?
        get() {
            return auth.currentUser?.run {
                UserData(
                    userId = uid, userName = displayName, profilePictureUrl = photoUrl?.toString()
                )
            }
        }

    private val buildSignInRequest: BeginSignInRequest
        get() {
            return BeginSignInRequest.Builder().setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder().setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.google_client_id)).build()
            ).setAutoSelectEnabled(true).build()
        }
}

data class SignInResult(
    val data: UserData?, val errorMessage: String?
)

data class UserData(
    val userId: String, val userName: String?, val profilePictureUrl: String?
)