package com.mariii.readdiary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mariii.readdiary.navigation.AppNavigation
import com.mariii.readdiary.ui.theme.ReadDiaryTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            // enableEdgeToEdge()

        setContent {

            ReadDiaryTheme {

                AppNavigation()
            }
        }
    }
}