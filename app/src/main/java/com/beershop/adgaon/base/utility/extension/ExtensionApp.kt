package com.beershop.adgaon.base.utility.extension

import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.beershop.adgaon.R

class NonNullMediatorLiveData<T> : MediatorLiveData<T>()

fun NavController.navigateWithAnim(directions: NavDirections) {
    try {
        this.navigate(
            directions.actionId,
            directions.arguments,
            NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .build()
        )
    } catch (e: IllegalArgumentException) {
        Log.e("${this.javaClass.simpleName} in navigateWithAnim", "${e.message}")
    }
}

fun NavController.navigateWithAnim(@IdRes actionId: Int, args: Bundle? = null) {
    try {
        this.navigate(
            actionId,
            args,
            NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .build()
        )
    } catch (e: IllegalArgumentException) {
        Log.e("${this.javaClass.simpleName} in navigateWithAnim", "${e.message}")
    }
}

fun <T> LiveData<T>.nonNull(): NonNullMediatorLiveData<T> {
    val mediator: NonNullMediatorLiveData<T> =
        NonNullMediatorLiveData()
    mediator.addSource(this, Observer { result ->
        result?.let { mediator.value = result }
    })
    return mediator
}

fun <T> NonNullMediatorLiveData<T>.observe(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, Observer { result ->
        result?.let(observer)
    })
}