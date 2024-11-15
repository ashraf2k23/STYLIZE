package com.progress.photos.delivery.screens

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.progress.photos.delivery.dataclass.Product
import com.progress.photos.delivery.R
import com.progress.photos.delivery.activities.LoginActivity
import com.progress.photos.delivery.mvvm.CartModelView
import java.lang.Float.sum

@Composable
fun CartScreen(navController: NavHostController) {

    val context = LocalContext.current

    val cartModelView : CartModelView = viewModel()
    val cart = cartModelView.productState.value


    val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    val userId = sharedPreferences.getInt("userId", 0)
    val isloggedIn = sharedPreferences.getBoolean("loggedIn", true)

    if(isloggedIn) {

        LaunchedEffect(Unit) {
            cartModelView.productFetcher(user = userId, context = context)
        }

        fun sellingPrice(price: Float, discount: Float): Float {
            return (price + 300) - ((price + 300) * (discount / 100.0f))
        }

        val cartList = cart.cart
        val total = cartList.sumOf {
            (it.quantity.toFloat() * sellingPrice(
                it.product[0].price.toFloat(),
                it.product[0].discount.toFloat()
            )).toDouble()
        }
        if (cartList.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "no item in carts",
                    fontSize = 15.sp,
                    color = Color.LightGray,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Explore items...",
                    fontSize = 15.sp,
                    color = Color.LightGray,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxSize()
                        .align(Alignment.TopCenter)
                ) {
                    items(cartList) { item ->
                        val price = item.product[0].price.toFloat() + 300
                        val discount =
                            price - (price * (item.product[0].discount.toFloat() / 100.0f))
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .background(Color(240, 241, 246, 255))
                                    .fillMaxSize()
                                    .padding(8.dp)
                                    .height(125.dp)
                                    .padding(4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .padding(5.dp)
                                        .aspectRatio(1f)
                                ) {
                                    AsyncImage(
                                        model = item.product[0].image,
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .weight(1f)
                                        .padding(5.dp),
                                    verticalArrangement = Arrangement.SpaceEvenly,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {

                                    Text(
                                        text = item.product[0].name,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 19.sp,
                                        fontFamily = FontFamily.Cursive
                                    )

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Box {
                                            Text(
                                                text = "Price : ₹ ${price.toInt()}",
                                                fontSize = 12.sp,
                                                modifier = Modifier.align(Alignment.Center)
                                            )
                                            Box(
                                                modifier = Modifier
                                                    .align(Alignment.CenterEnd)
                                                    .height(18.dp)
                                                    .width(2.dp)
                                                    .graphicsLayer(rotationZ = 45f)
                                                    .background(Color.Red)

                                            )
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            text = "₹ ${String.format("%.2f", discount)}",
                                            fontSize = 15.sp
                                        )
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        Text(text = "Quantity : ${item.quantity}", fontSize = 12.sp)
                                        Text(text = "Size : ${item.size}", fontSize = 12.sp)
                                    }
                                    val fprice = String.format("%.2f", discount * item.quantity)

                                    Text(
                                        text = "Total : ₹ $fprice",
                                        color = Color.Black,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                    item { Spacer(modifier = Modifier.height(100.dp)) }
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp, bottom = 50.dp)
                        .clip(RoundedCornerShape(topStart = 100.dp, topEnd = 100.dp))
                        .background(colorResource(id = R.color.green))
                        .height(60.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .align(Alignment.TopCenter),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(text = "Total : ₹ ${String.format("%.2f", total)}")
                        Text(
                            text = "Check Out",
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }

            }
        }
    } else {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.White), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "Log in to see Cart", color = Color.DarkGray, fontSize = 20.sp)
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


@Preview(showBackground = true)
@Composable
fun Show(){
    CartScreen(navController = rememberNavController())
}