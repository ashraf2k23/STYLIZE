package com.progress.photos.delivery.screens


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.progress.photos.delivery.R
import com.progress.photos.delivery.activities.LoginActivity
import com.progress.photos.delivery.apiPack.RetrofitInstance
import com.progress.photos.delivery.mvvm.SharedProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(navController: NavController, sharedProductModel: SharedProductModel) {

    val coroutineScope = rememberCoroutineScope()

    val product = sharedProductModel.product

    var showCartOption by remember{ mutableStateOf(false) }
    var itemCount by remember{ mutableIntStateOf(1) }

    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {

            Header(navController, "Details")

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(colorResource(id = R.color.green))) {
                AsyncImage(model = product.image, contentDescription = null, modifier = Modifier.align(Alignment.Center))
            }

            Text(
                text = product.name,
                fontSize = 25.sp,
                color = Color(65, 95, 145, 255),
                fontFamily = FontFamily.Cursive,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = product.description,
                fontSize = 20.sp,
                fontFamily = FontFamily.Cursive,
                color = Color(65, 95, 145, 255),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
//
//            Row {
//                Text(
//                    text = "Sizes :",
//                    fontSize = 20.sp,
//                    fontFamily = FontFamily.Cursive,
//                    color = Color(65, 95, 145, 255),
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .fillMaxWidth()
//                )
//                val strList = product.size.split(",")
//                var index by remember { mutableIntStateOf(0) }
//                strList.forEach { item ->
//                    Box(
//                        modifier = Modifier
//                            .size(35.dp)
//                            .padding(4.dp)
//                            .clickable {
//                                index = strList.indexOf(item)
//                            }
//                            .border(
//                                width = 1.dp,
//                                color = if (item == strList[index]) Color(0xFF415F91) else Color.Transparent
//                            )
//                    ) {
//                        Text(text = item, color= Color(65, 95, 145, 255), modifier=Modifier.align(Alignment.Center).padding(2.dp))
//                    }
//                }
//            }

            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Price :  ",
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Cursive,
                    color = Color(65, 95, 145, 255),
                    modifier = Modifier
                        .padding(8.dp))
                val price = product.price.toFloat()+300

                Box(modifier = Modifier.padding(start =4.dp)) {

                    Text(text = "₹ ${price.toInt()}", fontSize = 12.sp, modifier = Modifier.align(Alignment.Center))
                    Box(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .align(Alignment.Center)
                            .height(22.dp)
                            .width(2.dp)
                            .graphicsLayer(rotationZ = 45f)
                            .background(Color.Red)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                val discount = price - (price * (product.discount.toFloat() / 100.0f))
                Text(text = "₹ ${String.format("%.2f", discount)}", fontSize = 18.sp )
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Select Quantity : ",
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Cursive,
                    color = Color(65, 95, 145, 255),
                    modifier = Modifier.padding(2.dp)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Icon(imageVector = Icons.Default.Remove, contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color(154, 178, 219, 255))
                            .size(20.dp)
                            .clickable { if (itemCount > 1) itemCount -= 1 })
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "$itemCount", fontSize = 25.sp, color = Color.Black)
                Spacer(modifier = Modifier.width(5.dp))
                Icon(imageVector = Icons.Default.Add, contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(154, 178, 219, 255))
                        .size(20.dp)
                        .clickable { itemCount += 1 })
            }
        }

        val sharedPreferences: SharedPreferences =  context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isloggedIn = sharedPreferences.getBoolean("loggedIn", true)

        Card(modifier = Modifier
                .width(220.dp)
                .height(70.dp)
                .padding(15.dp)
                .clip(RoundedCornerShape(250.dp))
                .align(Alignment.BottomCenter)
                .clickable {
                    if(isloggedIn) {
                        val userId = sharedPreferences.getInt("userId", 0)
                        coroutineScope.launch(Dispatchers.IO) {
                            try {
                                val result = RetrofitInstance.api.addToCart(
                                    productId = product.product_id,
                                    userId = userId,
                                    quantity = itemCount,
                                    size = "L"
                                )
                                if (result.isSuccessful && result.body() != null) {
                                    withContext(Dispatchers.Main) {
                                        Toast
                                            .makeText(
                                                context,
                                                result.body()!!.message,
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                        if (!result.body()!!.error) {
                                            showCartOption = true
                                        } else {
                                            Log.d("checkApi", result.body()!!.message)
                                        }
                                    }
                                } else {
                                    withContext(Dispatchers.Main) {
                                        Toast
                                            .makeText(context, result.message(), Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                            } catch (e: Exception) {
                                withContext(Dispatchers.Main) {
                                    Toast
                                        .makeText(
                                            context,
                                            "Error : " + e.message,
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
                            }
                        }
                    }
                    else{
                        val intent = Intent(context, LoginActivity::class.java)
                        context.startActivity(intent)
                    }
                })
            {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = if(isloggedIn) "Add to cart" else "Log in to add to cart",
                        fontSize = 20.sp, modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                            .background(Color(154, 178, 219, 255))
                            .padding(10.dp), color = Color(14, 48, 102, 255)
                    )
                }
            }

        if(showCartOption){
            AlertDialog(onDismissRequest = {showCartOption=false},modifier = Modifier
                .clip(RoundedCornerShape(10.dp))){
                    var targetRotation by remember { mutableFloatStateOf(0f) }

                    val rotationAngle by animateFloatAsState(
                        targetValue = targetRotation,
                        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
                        label="")

                    val scale by animateFloatAsState(
                        targetValue = if (targetRotation == 360f) 1f else 0.1f, // Start small and grow
                        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
                        label = ""
                    )

                    LaunchedEffect(Unit) {
                        targetRotation = 360f
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .background(Color.White)
                            .padding(20.dp))
                    {
                        Image(imageVector = Icons.Default.Done, contentDescription = null,
                            modifier = Modifier
                                .size(35.dp)
                                .clip(CircleShape)
                                .background(Color(154, 178, 219, 255))
                                .padding(4.dp)
                                .graphicsLayer(
                                    rotationZ = rotationAngle,
                                    scaleX = scale,
                                    scaleY = scale
                                ))
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Item Added",fontSize = 15.sp, color = Color.Black)
                    }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowS(){
    ProductScreen(navController = rememberNavController(), sharedProductModel = SharedProductModel())
}