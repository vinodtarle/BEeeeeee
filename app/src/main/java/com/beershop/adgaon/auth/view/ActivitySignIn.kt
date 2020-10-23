package com.beershop.adgaon.auth.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.beershop.adgaon.R
import com.beershop.adgaon.base.view.ActivityMain
import com.beershop.adgaon.base.view.BaseActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class ActivitySignIn : BaseActivity(layoutResourceId = R.layout.fragment_sign_in) {
    override fun className(): String = this.javaClass.simpleName

    private lateinit var googleApiClient: GoogleApiClient
    private val SIGN_IN_RESULT = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        initListener()
        //setTitle(R.string.titleSignIn)
    }

    private fun init() {
        this.googleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this) {
                Toast.makeText(this, "Fail to login", Toast.LENGTH_SHORT).show()
            }
            .addApi(
                Auth.GOOGLE_SIGN_IN_API,
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
            ).build()
    }

    private fun initListener() {
        buttonSignIn.setOnClickListener { signIn() }
    }

    private fun signIn() {
        progressBar.visibility = View.VISIBLE
        buttonSignIn.isEnabled = false
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(this.googleApiClient)
        startActivityForResult(signInIntent, SIGN_IN_RESULT)
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        this.firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->

                buttonSignIn.isEnabled = true
                progressBar.visibility = View.GONE

                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    this.firebaseAuth.currentUser?.let {
                        // successfully user login
                        getShared().edit().apply {
                            putString("appName", getAppName())
                            apply()
                        }
                        initNavigation()
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Fail to login", Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == SIGN_IN_RESULT) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                account?.let {
                    firebaseAuthWithGoogle(it)
                }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (hasSignIn()) initNavigation()
    }

    private fun initNavigation() {
        startActivity(
            Intent(
                this@ActivitySignIn,
                ActivityMain::class.java
            )
        )
        this@ActivitySignIn.finish()
    }
}