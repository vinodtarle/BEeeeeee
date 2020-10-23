package com.beershop.adgaon.sales.repository

import com.beershop.adgaon.base.repository.BaseRepository
import com.google.firebase.firestore.FirebaseFirestore

class RepositorySale internal constructor(
    val firestore: FirebaseFirestore
) : BaseRepository(firestore) {
    override val path: String get() = "sale"

}
