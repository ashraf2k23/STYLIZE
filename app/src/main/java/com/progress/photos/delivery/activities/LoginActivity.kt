package com.progress.photos.delivery.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.progress.photos.delivery.ui.theme.AppTheme
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.progress.photos.delivery.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.progress.photos.delivery.apiPack.RetrofitInstance
import kotlinx.coroutines.withContext

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentSignUp = Intent(this, SignupActivity::class.java)
        val gotoSignUp : () -> Unit = {
            startActivity(intentSignUp)
        }

        val intentHome = Intent(this, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val gotoHome : () -> Unit = {
            startActivity(intentHome)
        }

        setContent {
            AppTheme {
                Login({ gotoSignUp() }, { gotoHome()} )
            }
        }
    }
}


@Composable
fun Login(x: () -> Unit, gotoHome: () -> Unit) {

    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().putBoolean("isFirstTime", true).apply()
    sharedPreferences.edit().putBoolean("loggedIn", false).apply()


    val textColor = Color.Black

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        if (sharedPreferences.getBoolean("isFirstTime", true)) {
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp)
            ) {
                Text(
                    "Skip >",
                    color = textColor,
                    fontSize = 20.sp,
                    modifier = Modifier.clickable { gotoHome() })
            }
        } else {
            Spacer(modifier = Modifier.height(25.dp))
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .then(
                if (sharedPreferences.getBoolean("isFirstTime", true)) {
                    Modifier.padding(top = 10.dp)
                } else {
                    Modifier.padding(top = 25.dp)
                }
            ), horizontalAlignment = Alignment.CenterHorizontally)
        {
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = null, modifier = Modifier.size(125.dp))
            Text(text = "Stylize", fontSize = 30.sp, color = textColor, fontWeight = FontWeight.Bold)
            Text(text = "Redefine Your Fashion Journey", fontSize = 13.sp, color = textColor)
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp))
        {
            Text(
                text = "Login To Your Account", color = textColor, fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                colors = CardDefaults.cardColors(Color(194, 223, 206, 255))
            ) {
                TextField(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Mail,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Enter your e-mail") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                    colors = TextFieldDefaults.colors(
                        unfocusedPlaceholderColor = Color.Gray,
                        focusedPlaceholderColor = Color.Gray,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedLeadingIconColor = Color.Black,
                        unfocusedLeadingIconColor = Color.Black,
                        focusedTrailingIconColor = Color.Black,
                        unfocusedTrailingIconColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = Color.LightGray,
                        selectionColors = TextSelectionColors(
                            handleColor = Color.Black,
                            backgroundColor = Color.DarkGray
                        )
                    ))
            }

            Spacer(modifier = Modifier.height(10.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                colors = CardDefaults.cardColors(Color(194, 223, 206, 255))
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Password") },
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        val image = if (passwordVisible) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = null)
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedPlaceholderColor = Color.Gray,
                        focusedPlaceholderColor = Color.Gray,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedLeadingIconColor = Color.Black,
                        unfocusedLeadingIconColor = Color.Black,
                        focusedTrailingIconColor = Color.Black,
                        unfocusedTrailingIconColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = Color.LightGray,
                        selectionColors = TextSelectionColors(
                            handleColor = Color.Black,
                            backgroundColor = Color.DarkGray
                        )
                    )
                )
            }
            Text(
                text = "Forget Password",
                modifier = Modifier.padding(top = 10.dp),
                color = textColor
            )
        }

        Button(onClick = {
                coroutineScope.launch(Dispatchers.IO) {
                    try {
                        val result = RetrofitInstance.api.loginUser(email, password)
                        if (result.isSuccessful && result.body() != null) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    result.body()!!.result.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                if (!result.body()!!.result.error && result.body()!!.user != null) {
                                    val profile = result.body()!!
                                    sharedPreferences.edit().putString("email", email).apply()
                                    profile.user!!.userId?.let { sharedPreferences.edit().putInt("userId",it).apply()
                                    }
                                    sharedPreferences.edit().putString("token", result.body()!!.user?.authToken).apply()
                                    sharedPreferences.edit().putBoolean("loggedIn", true).apply()
                                    sharedPreferences.edit().putBoolean("isFirstTime", false).apply()
                                    Toast.makeText(
                                        context,
                                        "${result.body()!!.user},${result.message()}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    gotoHome()
                                } else {
                                    android.util.Log.d("checkApi", result.body()!!.result.message)
                                }
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    "something went error" + result.message(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Error : " + e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(textColor),
            modifier = Modifier.padding(top = 30.dp), shape = RoundedCornerShape(corner = CornerSize(12.dp))) {
                Text(
                    text = "Log in",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 20.dp),
                    color = Color.White
                )
        }

        Text(text = "Don't have account? Create account", color = textColor,
            modifier = Modifier
                .padding(top = 10.dp)
                .clickable(onClick = { x() }))
    }
}


@Preview(showBackground = true)
@Composable
fun Log(){
    Login({},{})
}