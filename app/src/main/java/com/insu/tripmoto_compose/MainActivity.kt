package com.insu.tripmoto_compose

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.insu.tripmoto_compose.common.composable.BackPressCheck
//import com.insu.tripmoto_compose.model.service.NotificationService
//import com.insu.tripmoto_compose.model.service.ReceiverService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        initViews()

        setContent {
            MyApp()
            BackPressCheck()
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        val intent = Intent(this@MainActivity, ReceiverService::class.java)
//        baseContext.stopService(intent)
//    }
//
//    private fun initViews() {
//        val intent = Intent(this@MainActivity, ReceiverService::class.java)
//        baseContext.startService(intent)
//    }
}
