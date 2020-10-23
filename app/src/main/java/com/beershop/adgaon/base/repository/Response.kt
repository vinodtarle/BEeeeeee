package com.beershop.adgaon.base.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

class Response(
    val loading: Boolean = true,
    val status: Boolean = true,
    val document: DocumentSnapshot? = null,
    val exception: Throwable? = null,
    val collection: QuerySnapshot? = null,
    val firestoreException: FirebaseFirestoreException? = null
)