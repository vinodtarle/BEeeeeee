package com.beershop.adgaon.stock.repository

import com.beershop.adgaon.base.repository.BaseRepository
import com.beershop.adgaon.base.repository.Resource
import com.beershop.adgaon.base.repository.Response
import com.beershop.adgaon.base.utility.constant.Path
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.Flow

class RepositoryStock internal constructor(
    val firestore: FirebaseFirestore
) : BaseRepository(firestore) {
    override val path: String get() = Path.stock

    suspend fun orderBy(): Flow<Resource<Response>> =
        super.collectionListener(
            firestore.collection(path)
                .orderBy("name", Query.Direction.ASCENDING)
        )
}
