package com.sohaib.animehub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sohaib.animehub.navigation.MainNavGraph
import com.sohaib.animehub.ui.theme.AnimeHubTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimeHubTheme {
                MainNavGraph()
            }
        }
    }
}