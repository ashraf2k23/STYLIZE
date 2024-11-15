package com.progress.photos.delivery.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.progress.photos.delivery.R
import com.progress.photos.delivery.mvvm.ProductViewModel
import com.progress.photos.delivery.mvvm.SharedProductModel

@Composable
fun ProductListScreen(navController: NavController, sharedProductModel: SharedProductModel) {

    val configuration = LocalConfiguration.current
    val halfScreenWidth = (configuration.screenWidthDp * 0.47).dp
    val cardHeight = (configuration.screenHeightDp * 0.35).dp

    val productTitle = sharedProductModel.title

    val searchModel : ProductViewModel = viewModel()
    val searchState by searchModel.productState

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        searchModel.productFetcher(productTitle, context)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Header(navController, sharedProductModel.title)
        ProductGrid(halfScreenWidth, cardHeight, navController, sharedProductModel, searchState)
    }
}

@Composable
fun Header(navController: NavController, list: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp))
            .background(colorResource(id = R.color.green)),
        contentAlignment = Alignment.CenterStart
    ) {
        BackButton(navController, Modifier.align(Alignment.CenterStart))
        Title(Modifier.align(Alignment.Center), list)
    }
}

@Composable
fun BackButton(navController: NavController, modifier: Modifier) {
    Icon(
        imageVector = Icons.Default.ArrowBackIosNew,
        contentDescription = null,
        modifier = modifier
            .padding(16.dp)
            .size(20.dp)
            .clickable { navController.popBackStack() },
        tint = Color(65, 95, 145, 255)
    )
}

@Composable
fun Title(modifier: Modifier, list: String) {
    Text(
        text = if(list=="Details"){"Details"}else{"Product For $list"},
        color = Color(65, 95, 145, 255),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier.padding(10.dp)
    )
}

//@Composable
//fun FilterButton(modifier: Modifier) {
//    Icon(
//        imageVector = Icons.Default.FilterAlt,
//        contentDescription = null,
//        modifier = modifier
//            .padding(16.dp)
//            .size(20.dp),
//        tint = Color.Black
//    )
//}

@Composable
fun ProductGrid(
    halfScreenWidth: Dp,
    cardHeight: Dp,
    navController: NavController,
    sharedProductModel: SharedProductModel,
    productState: ProductViewModel.ProductState
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(halfScreenWidth),
        contentPadding = PaddingValues(2.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(productState.list){
                product->
            ProductCard(
                product,
                halfScreenWidth,
                cardHeight,
                sharedProductModel
            ) {
                navController.navigate("product") {
                    launchSingleTop = true
                    popUpTo("product") { inclusive = false }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProductListScreen() {
    ProductListScreen(
        navController = rememberNavController(),
        sharedProductModel = SharedProductModel()
    )
}