package com.progress.photos.delivery.mvvm

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.progress.photos.delivery.apiPack.RetrofitInstance
import com.progress.photos.delivery.dataclass.CartItem
import kotlinx.coroutines.launch

class CartModelView : ViewModel() {

    data class  CartState(
        val loading : Boolean = true,
        val cart :List<CartItem> = emptyList(),
        val error : String? = null
    )

    private val _productState = mutableStateOf(CartState())

    val productState : State<CartState> = _productState

    suspend fun productFetcher(user : Int, context : Context) {
        viewModelScope.launch {
            try {
                val result = RetrofitInstance.api.fetchCartItems(user = user)

                if (result.isSuccessful && result.body() != null) {

                    Toast.makeText(context, result.body()!!.result.message, Toast.LENGTH_SHORT).show()

                    val responseBody = result.body()!!

                    if (responseBody.result.error) {
                        android.util.Log.d("checkApi", responseBody.result.message)
                    } else {
                        _productState.value = _productState.value.copy(
                            cart = responseBody.cart_items,
                            loading = false,
                            error = responseBody.result.message
                        )
                    }
                } else {
                    _productState.value = _productState.value.copy(
                        loading = false,
                        error = result.message()
                    )
                    Toast.makeText(context, "Error fetching item: ${result.message()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {_productState.value = _productState.value.copy(
                loading = false,
                error = e.message
            )
                Toast.makeText(context, "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }

        }
    }
}