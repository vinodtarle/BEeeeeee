package com.beershop.adgaon.shop.repository

import com.beershop.adgaon.base.repository.BaseRepository
import com.beershop.adgaon.base.utility.constant.Path
import com.google.firebase.firestore.FirebaseFirestore

class RepositoryShop internal constructor(
    val firestore: FirebaseFirestore
) : BaseRepository(firestore) {
    override val path: String get() = Path.shop
}
