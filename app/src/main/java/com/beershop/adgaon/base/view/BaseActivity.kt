package com.beershop.adgaon.base.view

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.beershop.adgaon.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

abstract class BaseActivity(val layoutResourceId: Int) : AppCompatActivity() {
    abstract fun className(): String

    val TAG = className()

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResourceId)
    }

    fun getAppName(): String = getString(R.string.app_name)

    fun getShared() = sharedPreferences

    fun hasSignIn() = firebaseAuth.currentUser != null

    fun getUser() = firebaseAuth.currentUser

    fun signOut() = firebaseAuth.signOut()
}