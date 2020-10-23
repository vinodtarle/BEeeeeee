package com.beershop.adgaon.base.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

@ExperimentalCoroutinesApi
abstract class BaseRepository(private val firestore: FirebaseFirestore) : Repository {
    internal abstract val path: String

    override suspend fun add(data: Any): Flow<Resource<Response>> =
        flow {
            emit(Resource.Loading())
            firestore.collection(path).add(data).await()
            emit(Resource.Success(Response()))
        }.catch {
            emit(Resource.Error(Response(exception = it)))
        }.flowOn(Dispatchers.IO)

    override suspend fun set(documentId: String, data: Any): Flow<Resource<Response>> =
        flow {
            emit(Resource.Loading())
            firestore.collection(path).document(documentId).set(data).await()
            emit(Resource.Success(Response()))
        }.catch {
            emit(Resource.Error(Response(exception = it)))
        }.flowOn(Dispatchers.IO)

    override suspend fun update(
        documentId: String,
        key: String,
        value: Any
    ): Flow<Resource<Response>> =
        flow {
            emit(Resource.Loading())
            firestore.collection(path).document(documentId).update(key, value).await()
            emit(Resource.Success(Response()))
        }.catch {
            emit(Resource.Error(Response(exception = it)))
        }.flowOn(Dispatchers.IO)

    override suspend fun delete(documentId: String): Flow<Resource<Response>> =
        flow {
            emit(Resource.Loading())
            firestore.collection(path).document(documentId).delete().await()
            emit(Resource.Success(Response()))
        }.catch {
            emit(Resource.Error(Response(exception = it)))
        }.flowOn(Dispatchers.IO)

    override suspend fun document(documentId: String): Flow<Resource<Response>> =
        flow {
            emit(Resource.Loading())
            val data = firestore.collection(path).document(documentId).get().await()
            emit(
                if (data.exists()) {
                    Resource.Success(Response(document = data))
                } else {
                    Resource.Error(Response(exception = Throwable("Not found")))
                }
            )
        }.catch {
            emit(Resource.Error(Response(exception = it)))
        }.flowOn(Dispatchers.IO)

    override suspend fun documentListener(documentId: String): Flow<Resource<Response>> =
        callbackFlow {
            offer(Resource.Loading())
            val subscription = firestore.collection(path)
                .document(documentId)
                .addSnapshotListener { data, e ->
                    if (e != null) {
                        offer(Resource.Error(Response(exception = e)))
                        return@addSnapshotListener
                    }
                    offer(Resource.Success(Response(document = data)))
                }
            awaitClose { subscription.remove() }
        }

    override suspend fun collection(): Flow<Resource<Response>> =
        flow {
            emit(Resource.Loading())
            val data = firestore.collection(path).get().await()
            emit(Resource.Success(Response(collection = data)))
        }.catch {
            emit(Resource.Error(Response(exception = it)))
        }.flowOn(Dispatchers.IO)

    override suspend fun collectionListener(): Flow<Resource<Response>> =
        callbackFlow {
            offer(Resource.Loading())
            val subscription = firestore.collection(path).addSnapshotListener { data, e ->
                if (e != null) {
                    offer(Resource.Error(Response(exception = e)))
                    return@addSnapshotListener
                }
                offer(Resource.Success(Response(collection = data)))
            }
            awaitClose { subscription.remove() }
        }.flowOn(Dispatchers.IO)

    suspend fun collection(query: Query): Flow<Resource<Response>> =
        flow {
            emit(Resource.Loading())
            val data = query.get().await()
            emit(Resource.Success(Response(collection = data)))
        }.catch {
            emit(Resource.Error(Response(exception = it)))
        }.flowOn(Dispatchers.IO)

    suspend fun collectionListener(query: Query): Flow<Resource<Response>> =
        callbackFlow {
            offer(Resource.Loading())
            val subscription = query.addSnapshotListener { data, e ->
                if (e != null) {
                    offer(Resource.Error(Response(exception = e)))
                    return@addSnapshotListener
                }
                offer(Resource.Success(Response(collection = data)))
            }
            awaitClose { subscription.remove() }
        }.flowOn(Dispatchers.IO)
}