package com.sohaib.animehub.di

import com.sohaib.animehub.core.common.di.coreCommonModule
import com.sohaib.animehub.core.network.di.coreNetworkModule
import com.sohaib.animehub.data.di.dataSourceModule
import com.sohaib.animehub.data.di.repositoriesModule
import com.sohaib.animehub.domain.di.domainModule
import com.sohaib.animehub.feature.home.di.homeFeatureModule

class KoinModules {

    private val coreList = listOf(
        coreCommonModule,
        coreNetworkModule
    )
    private val dataList = listOf(
        dataSourceModule,
        repositoriesModule
    )
    private val domainList = listOf(
        domainModule,
    )
    private val featureList = listOf(
        homeFeatureModule
    )

    val list = coreList + dataList + domainList + featureList
}