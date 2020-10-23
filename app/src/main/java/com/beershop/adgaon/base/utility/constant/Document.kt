package com.beershop.adgaon.base.utility.constant

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.io.Serializable

interface BaseModule : Serializable {
    var id: String
}

inline fun <reified T : BaseModule> DocumentSnapshot.toObject(): T {
    return this.toObject(T::class.java)!!.also {
        it.id = this.id
    }
}

inline fun <reified T : BaseModule> QuerySnapshot.toObjects(): List<T> {
    return this.documents.map {
        it.toObject<T>()
    }
}
