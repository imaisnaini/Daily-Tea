package com.imaisnaini.dailytea

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import com.imaisnaini.dailytea.ui.DailyTeaApp
import com.imaisnaini.dailytea.ui.theme.MyTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyTheme() {
                DailyTeaApp()
            }
        }
    }
}