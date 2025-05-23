package org.example.cashitest.di

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import io.ktor.client.HttpClient
import org.example.cashitest.backend.NetworkService
import org.example.cashitest.backend.NetworkServiceImpl
import org.example.cashitest.backend.createMockHttpClient
import org.example.cashitest.data.FirebaseService
import org.example.cashitest.data.FirebaseServiceImpl
import org.example.cashitest.domain.repository.TransactionRepository
import org.example.cashitest.domain.repository.TransactionRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val firebaseModule = module {
    single<FirebaseFirestore> { Firebase.firestore }
    singleOf(::FirebaseServiceImpl).bind<FirebaseService>()
}

val networkModule = module {
    single<HttpClient> { createMockHttpClient() }
    singleOf(::NetworkServiceImpl).bind<NetworkService>()
}

val repositoryModule = module {
    singleOf(::TransactionRepositoryImpl).bind<TransactionRepository>()
}