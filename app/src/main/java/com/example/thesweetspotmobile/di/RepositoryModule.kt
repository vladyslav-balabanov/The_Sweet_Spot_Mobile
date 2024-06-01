package com.example.thesweetspotmobile.di

import android.content.Context
import com.example.thesweetspotmobile.Network.ApiService
import com.example.thesweetspotmobile.Network.Repository.AuthRepository
import com.example.thesweetspotmobile.Network.Repository.CartRepository
import com.example.thesweetspotmobile.Network.Repository.OrderRepository
import com.example.thesweetspotmobile.Network.Repository.ProductRepository
import com.example.thesweetspotmobile.Network.Repository.ReviewRepository
import com.example.thesweetspotmobile.Network.Repository.UserRepository
import com.example.thesweetspotmobile.Utils.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(apiService: ApiService): AuthRepository {
        return AuthRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideProductRepository(apiService: ApiService): ProductRepository {
        return ProductRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideUserRepository(apiService: ApiService): UserRepository {
        return UserRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideReviewRepository(apiService: ApiService): ReviewRepository {
        return ReviewRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideCartRepository(apiService: ApiService): CartRepository {
        return CartRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideOrderRepository(apiService: ApiService): OrderRepository {
        return OrderRepository(apiService)
    }

}