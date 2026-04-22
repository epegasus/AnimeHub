package com.sohaib.animehub.feature.home.intent

sealed class HomeIntent {
    object FetchData : HomeIntent()
}