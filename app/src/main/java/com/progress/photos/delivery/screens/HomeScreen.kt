package com.progress.photos.delivery.screens

import android.graphics.fonts.FontStyle
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.progress.photos.delivery.mvvm.ProductViewModel
import com.progress.photos.delivery.R
import com.progress.photos.delivery.dataclass.Product
import com.progress.photos.delivery.mvvm.SharedProductModel
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(navController: NavHostController, sharedProductModel: SharedProductModel) {

    val suggestionViewModel : ProductViewModel = viewModel(key = "1")
    val suggestion by suggestionViewModel.productState

    val arrivalViewModel : ProductViewModel = viewModel(key = "2")
    val arrivals by arrivalViewModel.productState

    val clothingViewModel : ProductViewModel = viewModel(key = "3")
    val clothing by clothingViewModel.productState

    val accessoriesViewModel : ProductViewModel = viewModel(key = "4")
    val accessories by accessoriesViewModel.productState

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        suggestionViewModel.productFetcher("Suggested", context)
        arrivalViewModel.productFetcher("New Arrival", context)
        clothingViewModel.productFetcher("Clothing", context)
        accessoriesViewModel.productFetcher("Accessories", context)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.green)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Filter(navController, sharedProductModel) }
        //item { Banners() }
        item { ItemListSection("Suggested", navController, suggestion, sharedProductModel) }
        item { ItemListSection("New Arrival", navController, arrivals, sharedProductModel) }
        item { ItemListSection("Clothing", navController, clothing, sharedProductModel) }
        item { ItemListSection("Accessories", navController, accessories, sharedProductModel) }
        item { Spacer(modifier = Modifier.height(60.dp)) }
    }
}

@Composable
fun Filter(navController: NavController, sharedProductModel : SharedProductModel)
{
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(150.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Category : ",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(5.dp),
            color = Color(65, 95, 145, 255),
            fontFamily = FontFamily.Cursive
        )

        val categories = listOf("Men", "Women", "Unisex")
        LazyRow(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            items(categories) { category ->
                CategoryCard(category) {
                        sharedProductModel.setSearch(category)
                        navController.navigate("productList") {
                            launchSingleTop = true
                            popUpTo("productList") { inclusive = false }
                        }
                    }
                }
            }
        }
}

@Composable
fun CategoryCard(text: String, onClick:(() -> Unit)) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .height(100.dp)
            .width(80.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .size(150.dp)
                .weight(1f)
                .background(Color(240, 241, 246, 255))
                .border(width = 2.dp, color = Color(65, 95, 145, 255), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            val icon = when(text) {
                "Men" -> painterResource(id = R.drawable.men)
                "Women" -> painterResource(R.drawable.women)
                "Unisex" -> painterResource(R.drawable.unisex)
                else -> null
            }
            if (icon != null) {
                Image(painter = icon,
                    contentDescription ="",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop)
            }
        }
        Text(text = text, color = Color.Black, fontSize = 15.sp, fontFamily = FontFamily.SansSerif)
    }
}

@Composable
fun Banners() {
    val images = listOf(R.drawable.banner1, R.drawable.banner2, R.drawable.banner3)
    val pagerState = rememberPagerState(initialPage = 0) { images.size }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            key = { images[it] },
            modifier = Modifier.fillMaxSize()
        ) { index ->
            Image(
                painter = painterResource(id = images[index]),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(20.dp))
            )
        }

        listOf(Icons.Outlined.ChevronLeft to Alignment.CenterStart, Icons.Outlined.ChevronRight to Alignment.CenterEnd).forEach { (icon, alignment) ->
            Image(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .align(alignment)
                    .size(40.dp)
                    .padding(5.dp)
                    .clickable {
                        coroutineScope.launch {
                            val newPage =
                                (pagerState.currentPage + if (icon == Icons.Outlined.ChevronLeft) -1 else 1).coerceIn(
                                    0,
                                    images.size - 1
                                )
                            pagerState.animateScrollToPage(newPage, animationSpec = tween(100))
                        }
                    }
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(images.size) { pageIndex ->
                val color = if (pagerState.currentPage == pageIndex) Color(184, 193, 238, 255) else Color(134, 134, 134, 144)
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                )
            }
        }
    }
}

@Composable
fun ItemListSection(
    title: String,
    navController: NavController,
    suggestion: ProductViewModel.ProductState,
    sharedProductModel: SharedProductModel
) {
    val configuration = LocalConfiguration.current
    val halfScreenWidth = (configuration.screenWidthDp * 0.47).dp
    val cardHeight = (configuration.screenHeightDp * 0.35).dp

    val productList = suggestion.list


    Column(modifier = Modifier.fillMaxWidth()) {
        if(suggestion.list.isNotEmpty()) {
            Text(
                text = "$title : ",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(5.dp),
                color = Color(65, 95, 145, 255),
                fontFamily = FontFamily.Cursive
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                items(productList.size) { item ->
                    ProductCard(productList[item], halfScreenWidth, cardHeight, sharedProductModel) {
                        navController.navigate("product") {
                            launchSingleTop = true
                            popUpTo("product") { inclusive = false }
                        }
                    }
                }
            }
        } else{
            suggestion.error?.let { Text(text = it, color=Color.Black) }
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    width: Dp,
    height: Dp,
    sharedProductModel: SharedProductModel,
    navigate: () -> Unit
) {

    Card(
        modifier = Modifier
            .width(width)
            .height(height)
            .padding(2.dp)
            .clickable(
                onClick = {
                    sharedProductModel.setProduct(product)
                    navigate()
                }
            ),
        elevation = CardDefaults.cardElevation(3.dp, pressedElevation = 20.dp, hoveredElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .background(Color(240, 241, 246, 255)),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = product.image, contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        Column(modifier = Modifier
            .background(Color(0x9FCFDDFF))
            .graphicsLayer(alpha = 0.8f)
            .align(Alignment.BottomStart)
            .fillMaxWidth()
            .height(65.dp)) {
            Text(
                text = product.name,
                modifier = Modifier.padding(6.dp),
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.sp,
                color = Color(13, 36, 75, 243)
            )
            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {

                val price = product.price.toFloat()+300

                Box(modifier = Modifier.padding(start =4.dp)) {

                    Text(text = "₹ ${price.toInt()}", fontSize = 12.sp, modifier = Modifier.align(Alignment.Center))
                    Box(
                        modifier = Modifier.padding(start=10.dp).align(Alignment.Center).height(22.dp).width(2.dp).graphicsLayer(rotationZ = 45f)
                            .background(Color.Red)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                val discount = price - (price * (product.discount.toFloat() / 100.0f))
                Text(text = "₹ ${String.format("%.2f", discount)}", fontSize = 18.sp )
            }
        }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowHome() {
    Column {

    }
}


@Composable
fun OutlinedText(
    text: String,
    fontSize: TextUnit = 24.sp,
    textColor: Color = Color.Blue,
    outlineColor: Color = Color.White,
    outlineThickness: Float = 4f,
    fontFamily: FontFamily = FontFamily.Default,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val typeface = when (fontFamily) {
            FontFamily.Serif -> android.graphics.Typeface.SERIF
            FontFamily.SansSerif -> android.graphics.Typeface.SANS_SERIF
            FontFamily.Monospace -> android.graphics.Typeface.MONOSPACE
            FontFamily.Cursive -> android.graphics.Typeface.create("cursive", android.graphics.Typeface.NORMAL)
            else -> android.graphics.Typeface.DEFAULT
        }

        val textPaintOutline = android.graphics.Paint().apply {
            color = outlineColor.toArgb() // Outline color
            textSize = fontSize.toPx()
            style = android.graphics.Paint.Style.STROKE // Outline style
            strokeWidth = outlineThickness
            isAntiAlias = true // Smoother edges
            this.typeface = typeface // Set font family
        }

        val textPaintFill = android.graphics.Paint().apply {
            color = textColor.toArgb() // Fill color
            textSize = fontSize.toPx()
            style = android.graphics.Paint.Style.FILL // Fill style
            isAntiAlias = true // Smoother edges
            this.typeface = typeface // Set font family
        }

        // Center the text within the canvas
        val x = (size.width - textPaintOutline.measureText(text)) / 2
        val y = (size.height / 2) - ((textPaintOutline.descent() + textPaintOutline.ascent()) / 2)

        // Draw the outline
        drawIntoCanvas { canvas ->
            canvas.nativeCanvas.drawText(text, x, y, textPaintOutline)
        }

        // Draw the fill color
        drawIntoCanvas { canvas ->
            canvas.nativeCanvas.drawText(text, x, y, textPaintFill)
        }
    }
}
