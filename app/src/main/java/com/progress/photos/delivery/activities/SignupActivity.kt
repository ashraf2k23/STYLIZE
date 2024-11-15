package com.progress.photos.delivery.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.progress.photos.delivery.R
import com.progress.photos.delivery.apiPack.RetrofitInstance
import com.progress.photos.delivery.dataclass.User
import com.progress.photos.delivery.ui.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val intent = Intent(this, LoginActivity::class.java).apply{
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP}

        val x : () -> Unit = {
            startActivity(intent)
        }
        super.onCreate(savedInstanceState)
        setContent{
            AppTheme {
                SignUp{x()}
            }
        }
    }
}

@Composable
fun SignUp(x:()->Unit){

    val textColor = Color.Black

    var name by remember { mutableStateOf( "") }
    var phoneNo  by remember { mutableStateOf( "") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = null, modifier = Modifier.size(125.dp))
            Text(text = "Stylize", fontSize = 30.sp, color = textColor, fontWeight = FontWeight.Bold)
            Text(text = "Redefine Your Fashion Journey", fontSize = 13.sp, color = textColor)
        }

        Column(modifier = Modifier.padding(top = 30.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

            Text(text = "Create Your Account",color = textColor, fontSize = 20.sp, modifier = Modifier.padding(bottom = 10.dp))

            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp), colors = CardDefaults.cardColors(Color(194, 223, 206, 255))) {
                TextField(
                    leadingIcon = { Icon(imageVector = Icons.Filled.Person2, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text("Username") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
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

            Spacer(modifier = Modifier.height(5.dp))

            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp), colors = CardDefaults.cardColors(Color(194, 223, 206, 255))) {
                TextField(
                    leadingIcon = { Icon(imageVector = Icons.Filled.Mail, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("E-mail") },
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
                    )
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp), colors = CardDefaults.cardColors(Color(194, 223, 206, 255))) {
                TextField(
                    leadingIcon = { Icon(imageVector = Icons.Filled.Phone, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    value = phoneNo,
                    onValueChange = { phoneNo = it },
                    placeholder = { Text("Phone no.") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
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

            Spacer(modifier = Modifier.height(5.dp))

            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp), colors = CardDefaults.cardColors(Color(194, 223, 206, 255)))
            {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Password") },
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    leadingIcon = { Icon(imageVector = Icons.Filled.Lock, contentDescription = null) },
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
                    ))
            }

        }
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()

        Button(onClick = {
            coroutineScope.launch(Dispatchers.IO) {
                try {
                    val result = RetrofitInstance.api.registerUser(User(password=password, email=email, phone = phoneNo, name = name) )
                    if (result.isSuccessful && result.body() != null) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                result.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            if (!result.body()!!.error ) {
                                x()
                            } else {
                                android.util.Log.d("checkApi", result.body()!!.message)
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
            modifier = Modifier.padding(top= 25.dp),
            shape = RoundedCornerShape(corner = CornerSize(12.dp))) {
            Text(text = "Sign Up", fontSize = 20.sp, modifier= Modifier.padding(vertical=5.dp, horizontal = 16.dp), color = Color.White)
        }
        Spacer(modifier = Modifier.height(2.dp))

        Text(text = "Have account? Login to account", modifier=Modifier.clickable(onClick = {x()}), color=textColor)
    }
}

@Preview(showBackground = true)
@Composable
fun Show4(){
    SignUp{}
}