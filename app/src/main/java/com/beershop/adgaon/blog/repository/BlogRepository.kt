package com.beershop.adgaon.blog.repository

import com.beershop.adgaon.base.repository.DataState
import com.beershop.adgaon.blog.model.Blog
import com.beershop.adgaon.blog.repository.retrofit.BlogRetrofit
import com.beershop.adgaon.blog.repository.retrofit.NetworkMapper
import com.beershop.adgaon.blog.repository.room.BlogDao
import com.beershop.adgaon.blog.repository.room.CacheMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BlogRepository internal constructor(
    private val blogDao: BlogDao,
    private val blogRetrofit: BlogRetrofit,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
) {
    suspend fun getBlog(): Flow<DataState<List<Blog>>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val networkBlogs = blogRetrofit.get()
            val blogs = networkMapper.mapFromEntityList(networkBlogs)
            for (blog in blogs) {
                blogDao.insert(cacheMapper.mapToEntity(blog))
            }
            val cachedBlogs = blogDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedBlogs)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}