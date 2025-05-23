package org.example.cashitest.di

val featureModules = listOf(
    transactionsFeatureModule,
    sendFeatureModule
)

val dataModules = listOf(
    firebaseModule,
    networkModule,
    repositoryModule
)

val appModules = featureModules + dataModules