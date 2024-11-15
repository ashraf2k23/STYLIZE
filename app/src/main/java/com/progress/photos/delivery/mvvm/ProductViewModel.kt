package com.progress.photos.delivery.mvvm

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.progress.photos.delivery.apiPack.RetrofitInstance
import com.progress.photos.delivery.dataclass.Product
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    data class  ProductState(
        val loading : Boolean = true,
        val list :List<Product> = emptyList(),
        val error : String? = null
    )

    private val _productState = mutableStateOf(ProductState())

    val productState : State<ProductState> = _productState

    suspend fun productFetcher(title : String, context : Context) {
        viewModelScope.launch {
            try {
                val result = RetrofitInstance.api.getProduct(query = title)

                if (result.isSuccessful && result.body() != null) {

                    val responseBody = result.body()!!
                    if (responseBody.result.error) {
                        android.util.Log.d("checkApi", responseBody.result.message)
                    } else {
                        _productState.value = _productState.value.copy(
                            list = responseBody.data,
                            loading = false,
                            error = responseBody.result.message
                        )
                    }
                } else {
                    _productState.value = _productState.value.copy(
                        loading = false,
                        error = result.message()
                    )
                }
            } catch (e: Exception) {
                _productState.value = _productState.value.copy(
                    loading = false,
                    error = e.message
                )
            }

        }
    }
}