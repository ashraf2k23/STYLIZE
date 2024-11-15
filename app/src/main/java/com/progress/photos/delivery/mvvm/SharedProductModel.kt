package com.progress.photos.delivery.mvvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.progress.photos.delivery.dataclass.Product

class SharedProductModel : ViewModel() {
    private var _product by mutableStateOf(
        Product(
            description = "",
            discount = "",
            gender = "",
            name = "",
            price = "",
            product_id = 0,
            size = "",
            tag = "",
            type = "",
            image = ""
        )
    )

    fun setProduct(newProduct: Product) {
        _product = newProduct
    }

    val product: Product get() = _product

    /*
        so in this code "val product: Product get() = _product"
        we are storing this getter() function,
            fun getProduct(): Product {
                return _product
            }
        in product var
     */



    private var _search by mutableStateOf("")

    val title get() = _search

    fun setSearch(title : String) {
        _search = title
    }

    private var _productResult by mutableStateOf(ProductViewModel.ProductState())

    val productResult: ProductViewModel.ProductState get() = _productResult

    fun setProductResult(newProduct: ProductViewModel.ProductState) {
        _productResult = newProduct
    }


}