package com.progress.photos.delivery.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.progress.photos.delivery.R
import com.progress.photos.delivery.mvvm.ProductViewModel
import com.progress.photos.delivery.mvvm.SharedProductModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(navController : NavController, sharedProductModel: SharedProductModel){

    val productViewModel : ProductViewModel = viewModel ()
    val productState by productViewModel.productState

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val containerColor = colorResource(id = R.color.green)

    var search by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 4.dp)
    ) {
        OutlinedTextField(
            value = search,
            onValueChange = { search = it },
            textStyle= TextStyle(fontSize = 18.sp),
            singleLine = true,
            shape = RoundedCornerShape(100.dp),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            placeholder = @Composable { Text(text = "Search items...") },
            trailingIcon = {
                if (search.isNotEmpty()) {
                    Image(imageVector = Icons.Filled.Clear, contentDescription = null,
                        modifier = Modifier.clickable {
                            if (search.isNotEmpty()) search=""
                        }
                    )
                }
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    if(search.isNotEmpty()) {
                        coroutineScope.launch {
                            productViewModel.productFetcher(search, context)
                            delay(2000)
                        }
                        if(productState.list.isNotEmpty()) {
                            sharedProductModel.setSearch(search)
                            navController.navigate("productList") {
                                launchSingleTop = true
                                popUpTo("productList") { inclusive = false }
                            }
                        }
                    }
                }),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                selectionColors = TextSelectionColors(backgroundColor =Color(170,236,157,255), handleColor = containerColor),
                focusedBorderColor = containerColor,
                unfocusedBorderColor = Color(178, 231, 172, 255)
            )
        )

    }
}

@Preview(showBackground = true)
@Composable
fun ShowSearch(){
    SearchScreen(navController = rememberNavController(), sharedProductModel = SharedProductModel())
}