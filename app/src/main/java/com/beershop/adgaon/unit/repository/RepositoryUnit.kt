package com.beershop.adgaon.unit.repository

import com.beershop.adgaon.base.repository.BaseRepository
import com.beershop.adgaon.base.repository.Resource
import com.beershop.adgaon.base.repository.Response
import com.beershop.adgaon.base.utility.constant.Path
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.Flow

class RepositoryUnit internal constructor(
    val firestore: FirebaseFirestore
) : BaseRepository(firestore) {
    override val path: String get() = Path.unit

    suspend fun orderBy(): Flow<Resource<Response>> =
        super.collectionListener(
            firestore.collection(path)
                .orderBy("name", Query.Direction.ASCENDING)
        )
}
