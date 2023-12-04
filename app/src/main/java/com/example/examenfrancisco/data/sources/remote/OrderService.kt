package com.example.recyclerviewretrofitfrancisco.data.sources.remote

import com.example.examenfrancisco.data.sources.remote.ConstantesSources
import com.example.recyclerviewretrofitfrancisco.data.model.OrderResponse
import com.example.recyclerviewretrofitfrancisco.domain.model.Order
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface OrderService {

    @GET(ConstantesSources.ordersCustomers)
    suspend fun getCustomerOrders(@Query(ConstantesSources.id) id : Int) : Response<List<OrderResponse>>

    @POST(ConstantesSources.orders)
    suspend fun addOrder(@Body order: Order) : Response<OrderResponse>

    @DELETE(ConstantesSources.deleteOrder)
    suspend fun deleteOrder(@Path(ConstantesSources.id) id : Int) : Response <Unit>

}