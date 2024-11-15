package com.progress.photos.delivery.screens


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import com.progress.photos.delivery.R
import com.progress.photos.delivery.activities.LoginActivity
import com.progress.photos.delivery.apiPack.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ProfileScreen(navController: NavHostController) {

    val context = LocalContext.current

    val sharedPreferences : SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    var loggedIn by remember {
        mutableStateOf(sharedPreferences.getBoolean("loggedIn", false))
    }
    fun updateLoginStatus(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean("loggedIn", isLoggedIn).apply()
        loggedIn = isLoggedIn
    }

    val isExpanded = remember { mutableStateOf(false) }
    val cardHeight by animateDpAsState(targetValue = if (isExpanded.value) 180.dp else 100.dp,
        label = "abc"
    )

    var email by remember {
        mutableStateOf("")
    }
    var phone by remember {
        mutableStateOf("")
    }
    var userId by remember {
        mutableStateOf("")
    }
    var username by remember {
        mutableStateOf("")
    }
    var loading by remember {
        mutableStateOf(true)
    }



    LaunchedEffect(Dispatchers.IO) {
        try {
            val result = sharedPreferences.getString("email","")
                ?.let { RetrofitInstance.api.getUserDetails(email = it) }
            if (result != null) {
                if (result.isSuccessful && result.body() != null) {
                    withContext(Dispatchers.Main) {
                        email = result.body()!!.user?.email.toString()
                        username = result.body()!!.user?.name.toString()
                        phone= result.body()!!.user?.phone.toString()
                        userId = result.body()!!.user?.userId.toString()
                        loading = false
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            "something went error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Error : " + e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if(loggedIn) {
            if(loading){
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
            else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .height(cardHeight)
                            .clickable { isExpanded.value = !isExpanded.value },
                        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.green))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                imageVector = Icons.Filled.AccountCircle, contentDescription = null,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(100.dp)
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .weight(1f), verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = username,
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    modifier = Modifier.padding(4.dp)
                                )
                                Text(
                                    text = "ID : $userId",
                                    fontSize = 15.sp,
                                    color = Color.Black,
                                    modifier = Modifier.padding(4.dp)
                                )
                            }
                        }
                        if (isExpanded.value) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Email     : $email",
                                color = Color.DarkGray,
                                modifier = Modifier.padding(4.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Phone no. : $phone ",
                                color = Color.DarkGray,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                    Text(
                        text = "Recent Order",
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(10.dp)
                    )
                    Text(
                        text = "Order History",
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(10.dp)
                    )
                    Text(
                        text = "Log out",
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable { updateLoginStatus(false) }
                    )
                }
            }
        }
        else{
            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.White), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "Log in to see Profile", color = Color.DarkGray, fontSize = 20.sp)
                Button(onClick = { val intent = Intent(context, LoginActivity::class.java)
                                context.startActivity(intent) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    modifier = Modifier.padding(top = 30.dp), shape = RoundedCornerShape(corner = CornerSize(12.dp))
                ) {
                    Text(
                        text = "Log in",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 20.dp),
                        color = Color.White
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Shower(){
    ProfileScreen(navController = rememberNavController())
}