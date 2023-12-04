package com.example.recyclerviewretrofitfrancisco.data.sources.remote

import com.example.recyclerviewretrofitfrancisco.data.model.toCustomer
import com.example.recyclerviewretrofitfrancisco.data.model.toOrder
import com.example.recyclerviewretrofitfrancisco.data.sources.remote.ConstantesSources.error
import com.example.recyclerviewretrofitfrancisco.domain.model.Customer
import com.example.recyclerviewretrofitfrancisco.domain.model.Order
import com.example.recyclerviewretrofitfrancisco.utils.NetworkResultt
import java.lang.Exception
import javax.inject.Inject


class RemoteDataSource @Inject constructor(
    private val customerService: CustomerService,
    private val orderService: OrderService
) {
    suspend fun getCustomers(): NetworkResultt<List<Customer>> {
        try {
            val response = customerService.getCustomers()

            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    val customers = it.map { customerResponse ->
                        customerResponse.toCustomer()
                    }
                    return NetworkResultt.Success(customers)
                }
                error(ConstantesSources.noData)
            } else {
                val errorMessage = response.errorBody()?.string() ?: ConstantesSources.unknownError
                error("$error ${response.code()} : $errorMessage")
            }
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    suspend fun getCustomer(id: Int): NetworkResultt<Customer> {
        try {
            val response = customerService.getCustomer(id)

            return if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    val customer = it.toCustomer()
                    return NetworkResultt.Success(customer)
                }

                error(ConstantesSources.noData)
            } else {
                val errorMessage = response.errorBody()?.string() ?: ConstantesSources.unknownError
                error("$error ${response.code()} : $errorMessage")
            }
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    suspend fun deleteCustomer(id: Int): NetworkResultt<Unit> {
        try {
            val response = customerService.deleteCustomer(id)

            return if (response.isSuccessful) {
                NetworkResultt.Success(Unit)
            } else {
                val errorMessage = response.errorBody()?.string() ?: ConstantesSources.unknownError
                error("$error ${response.code()} : $errorMessage")
            }
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    suspend fun getCustomerOrders(id: Int): NetworkResultt<List<Order>> {
        try {
            val response = orderService.getCustomerOrders(id)

            if (!response.isSuccessful) {
                val errorMessage = response.errorBody()?.string() ?: ConstantesSources.unknownError
                error("$error ${response.code()} : $errorMessage")
            } else {
                val body = response.body()
                body?.let {
                    val orders = it.map { orderResponse ->
                        orderResponse.toOrder()
                    }
                    return NetworkResultt.Success(orders)
                }
                error(ConstantesSources.noData)
            }
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    suspend fun deleteOrder(id: Int): NetworkResultt<Unit> {
        try {
            val response = orderService.deleteOrder(id)

            return if (response.isSuccessful) {
                NetworkResultt.Success(Unit)
            } else {
                val errorMessage = response.errorBody()?.string() ?: ConstantesSources.unknownError
                error("$error ${response.code()} : $errorMessage")
            }
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    suspend fun addOrder(order: Order): NetworkResultt<Order> {
        try {
            val response = orderService.addOrder(order)

            return if (!response.isSuccessful) {
                val errorMessage = response.errorBody()?.string() ?: ConstantesSources.unknownError
                error("$error ${response.code()} : $errorMessage")
            } else {
                val body = response.body()
                body?.let {
                    val order = it.toOrder()
                    return NetworkResultt.Success(order)
                }
                error(ConstantesSources.noData)
            }
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }
}