package com.progress.photos.delivery.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.progress.photos.delivery.ui.theme.AppTheme
import androidx.compose.ui.unit.sp
import com.progress.photos.delivery.R
import kotlinx.coroutines.delay

class FirstActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent: Intent = when (isFirstLaunch()) {
            true -> Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            false -> Intent(this, HomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        }

        val x: () -> Unit = {
            startActivity(intent)
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        {
            installSplashScreen()
            x()
        }
        else{
            setContent {
                AppTheme {
                    SplashScreen { x() }
                }
            }
        }
    }

    private fun isFirstLaunch(): Boolean{
        val sharedPreferences : SharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        val isFirstTime = sharedPreferences.getBoolean("isFirstTime", true)
        if(isFirstTime){
            sharedPreferences.edit().putBoolean("isFirstTime",false).apply()
        }
        return isFirstTime
    }
}

@Composable
fun SplashScreen(x:()->Unit){
    LaunchedEffect(Unit) {
        delay(3000)
        x()
    }
    val textColor = Color.Black
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 225.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = null,modifier = Modifier.size(150.dp))
            Spacer(modifier = Modifier.height(40.dp))
            Text(text = "Stylize", fontSize = 40.sp, color = textColor, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Redefine Your Fashion Journey", fontSize = 20.sp
                , color = textColor)
        }
        Row(modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 40.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("Designed by ", color = textColor)
            Text("ASHRAF ALI", color = textColor, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Show(){
    SplashScreen {    }
}