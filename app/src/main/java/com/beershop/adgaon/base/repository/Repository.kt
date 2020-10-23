package com.beershop.adgaon.base.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
interface Repository {
    suspend fun add(data: Any): Flow<Resource<Response>>
    suspend fun set(documentId: String, data: Any): Flow<Resource<Response>>
    suspend fun update(documentId: String, key: String, value: Any): Flow<Resource<Response>>
    suspend fun delete(documentId: String): Flow<Resource<Response>>
    suspend fun document(documentId: String): Flow<Resource<Response>>
    suspend fun documentListener(documentId: String): Flow<Resource<Response>>
    suspend fun collection(): Flow<Resource<Response>>
    suspend fun collectionListener(): Flow<Resource<Response>>
}