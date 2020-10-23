package com.beershop.adgaon.base.di

import com.beershop.adgaon.blog.repository.BlogRepository
import com.beershop.adgaon.blog.repository.retrofit.BlogRetrofit
import com.beershop.adgaon.blog.repository.retrofit.NetworkMapper
import com.beershop.adgaon.blog.repository.room.BlogDao
import com.beershop.adgaon.blog.repository.room.CacheMapper
import com.beershop.adgaon.item.repository.RepositoryItem
import com.beershop.adgaon.sales.repository.RepositorySale
import com.beershop.adgaon.shop.repository.RepositoryShop
import com.beershop.adgaon.stock.repository.RepositoryStock
import com.beershop.adgaon.supplier.repository.RepositorySupplier
import com.beershop.adgaon.unit.repository.RepositoryUnit
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ModuleRepository {

    @Singleton
    @Provides
    fun repositoryShop(firestore: FirebaseFirestore) = RepositoryShop(firestore)

    @Singleton
    @Provides
    fun repositoryUnit(firestore: FirebaseFirestore) = RepositoryUnit(firestore)

    @Singleton
    @Provides
    fun repositoryItem(firestore: FirebaseFirestore) = RepositoryItem(firestore)

    @Singleton
    @Provides
    fun repositorySupplier(firestore: FirebaseFirestore) = RepositorySupplier(firestore)

    @Singleton
    @Provides
    fun repositoryStock(firestore: FirebaseFirestore) = RepositoryStock(firestore)

    @Singleton
    @Provides
    fun repositorySale(firestore: FirebaseFirestore) = RepositorySale(firestore)


    @Singleton
    @Provides
    fun provideBlogRepository(
        blogDao: BlogDao,
        blogRetrofit: BlogRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): BlogRepository =
        BlogRepository(
            blogDao = blogDao,
            blogRetrofit = blogRetrofit,
            cacheMapper = cacheMapper,
            networkMapper = networkMapper
        )
}