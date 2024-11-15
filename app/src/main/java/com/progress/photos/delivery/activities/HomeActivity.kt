package com.progress.photos.delivery.activities


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person2
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.progress.photos.delivery.R
import com.progress.photos.delivery.mvvm.SharedProductModel
import com.progress.photos.delivery.screens.CartScreen
import com.progress.photos.delivery.screens.HomeScreen
import com.progress.photos.delivery.screens.NotificationScreen
import com.progress.photos.delivery.screens.ProductListScreen
import com.progress.photos.delivery.screens.ProductScreen
import com.progress.photos.delivery.screens.ProfileScreen
import com.progress.photos.delivery.screens.SearchScreen
import com.progress.photos.delivery.ui.theme.AppTheme

class HomeActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            AppTheme {
                Home()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home() {

    val navController = rememberNavController()
    val bgColor = colorResource(id = R.color.green)

    val sharedProductModel : SharedProductModel = viewModel()

    var screenHeader by remember {
        mutableStateOf("Home")
    }

    var actionIcon : ImageVector? by remember {
        mutableStateOf(Icons.Default.Search)
    }

    fun iconAction(){
        when(actionIcon){
            Icons.Default.Search -> navController.navigate("search"){
                launchSingleTop = true
                popUpTo("search") { inclusive = false }
            }
            Icons.Default.SearchOff -> navController.navigate("home"){
                launchSingleTop = true
                popUpTo("home") { inclusive = false }
            }
        }
    }

    Scaffold(
        topBar = { if(screenHeader!= "Product Details"){
            TopAppBar(modifier = Modifier
                .background(colorResource(id = R.color.green))
                .clip(RoundedCornerShape(bottomEnd = 15.dp, bottomStart = 15.dp)),
                    title = {
                        Text(
                            screenHeader,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp,
                            color = Color(65, 95, 145, 255)
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(bgColor),
                    actions = {
                        if(actionIcon!=null)
                            Icon(imageVector = actionIcon!!,
                                contentDescription = "Custom Icon",
                                tint = Color(65, 95, 145, 255),
                                modifier = Modifier
                                    .padding(end = 15.dp)
                                    .size(35.dp)
                                    .clip(CircleShape)
                                    .clickable { iconAction() }
                            )
                    }
                )
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            NavHost(navController = navController, startDestination = "home") {
                composable(
                    route ="home",
                    enterTransition ={ slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) },
                    exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }) })
                { HomeScreen(navController = navController, sharedProductModel); screenHeader="Home"; actionIcon=Icons.Default.Search}
                composable(
                    route="notification",
                    enterTransition ={ slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) },
                    exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }) })
                { NotificationScreen(navController = navController); screenHeader="Notifications"; actionIcon=Icons.Default.Notifications}
                composable(
                    route="cart",
                    enterTransition ={ slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) },
                    exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }) })
                { CartScreen(navController = navController); screenHeader="Cart"; actionIcon=Icons.Default.Edit }
                composable(
                    route="profile",
                    enterTransition ={ slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) },
                    exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }) })
                { ProfileScreen(navController = navController); screenHeader="Profile"; actionIcon=null }
                composable(
                    route ="search",
                    enterTransition ={ slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) },
                    exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }) })
                { SearchScreen(navController = navController, sharedProductModel); screenHeader="Search"; actionIcon=Icons.Default.SearchOff }
                composable(
                    route ="product",
                    enterTransition ={ slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) },
                    exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }) })
                { ProductScreen(navController = navController, sharedProductModel); screenHeader="Product Details"; actionIcon=Icons.Default.Search }
                composable(
                    route ="productList",
                    enterTransition ={ slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) },
                    exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }) })
                { ProductListScreen(navController = navController,sharedProductModel); screenHeader="Product Details"; actionIcon=null }
            }
            if(screenHeader!= "Product Details"){ CustomBottomAppBar(navController,  screenHeader, Modifier.align(Alignment.BottomCenter))}
        }
    }
}


@Composable
fun CustomBottomAppBar(navController : NavController,  screenHeader : String, modifier: Modifier){

    val containerColor = colorResource(id = R.color.green)

    BottomAppBar(containerColor =containerColor,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, bottom = 20.dp)
            .clip(RoundedCornerShape(100.dp))
            .height(50.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(100.dp)),
            colors = CardDefaults.cardColors(colorResource(id = R.color.green))
        ) {
            Row(modifier = Modifier.fillMaxSize().padding(start = 15.dp, end = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Icon(
                    imageVector = if (screenHeader == "Home") Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = null,
                    tint = Color(65, 95, 145, 255),
                    modifier = Modifier
                        .size(50.dp)
                        .padding(vertical = 10.dp)
                        .clip(CircleShape)
                        .then(
                            if (screenHeader != "Home") {
                                Modifier.clickable {
                                    navController.navigate("home") {
                                        launchSingleTop = true
                                        popUpTo("home") { inclusive = false }
                                    }
                                }
                            } else Modifier.background(Color(154, 178, 219, 255))
                        )
                )

                Icon(
                    imageVector = if (screenHeader == "Notifications") Icons.Filled.Notifications else Icons.Outlined.Notifications,
                    contentDescription = null,
                    tint = Color(65, 95, 145, 255),
                    modifier = Modifier
                        .size(50.dp)
                        .padding(vertical = 10.dp)
                        .clip(CircleShape)
                        .then(
                            if (screenHeader != "Notifications") {
                                Modifier.clickable {
                                    navController.navigate("notification") {
                                        launchSingleTop = true
                                        popUpTo("notification") { inclusive = false }
                                    }
                                }
                            } else Modifier.background(Color(154, 178, 219, 255))
                        )
                )

                Icon(
                    imageVector = if (screenHeader == "Cart") Icons.Filled.ShoppingCart else Icons.Outlined.ShoppingCart,
                    contentDescription = null,
                    tint = Color(65, 95, 145, 255),
                    modifier = Modifier
                        .size(50.dp)
                        .padding(vertical =10.dp)
                        .clip(CircleShape)
                        .then(
                            if (screenHeader != "Cart") {
                                Modifier.clickable {
                                    navController.navigate("cart") {
                                        launchSingleTop = true
                                        popUpTo("cart") { inclusive = false }
                                    }
                                }
                            } else Modifier.background(Color(154, 178, 219, 255))
                        )
                )

                Icon(
                    imageVector = if (screenHeader == "Profile") Icons.Filled.Person2 else Icons.Outlined.Person2,
                    contentDescription = null,
                    tint = Color(65, 95, 145, 255),
                    modifier = Modifier
                        .size(50.dp)
                        .padding(vertical =10.dp)
                        .clip(CircleShape)
                        .then(
                            if (screenHeader != "Profile") {
                                Modifier.clickable {
                                    navController.navigate("profile") {
                                        launchSingleTop = true
                                        popUpTo("profile") { inclusive = false }
                                    }
                                }
                            } else Modifier.background(Color(154, 178, 219, 255))
                        )
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Show5(){
    Home()
}
