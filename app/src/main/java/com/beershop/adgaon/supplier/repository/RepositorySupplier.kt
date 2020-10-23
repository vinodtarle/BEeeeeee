package com.beershop.adgaon.supplier.repository

import com.beershop.adgaon.base.repository.BaseRepository
import com.beershop.adgaon.base.utility.constant.Path
import com.google.firebase.firestore.FirebaseFirestore

class RepositorySupplier internal constructor(
    val firestore: FirebaseFirestore
) : BaseRepository(firestore) {
    override val path: String get() = Path.supplier
}
