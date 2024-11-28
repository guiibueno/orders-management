package com.bueno.orders.infraestructure.adapters.output

import com.bueno.orders.domain.entity.Order
import com.bueno.orders.infraestructure.adapters.output.repository.OrderRepository
import org.springframework.data.repository.findByIdOrNull
import java.math.BigInteger
import com.bueno.orders.application.port.output.OrderOutputPort
import org.springframework.stereotype.Service

@Service
class OrderPersistenceAdapter (
    val repository: OrderRepository
) : OrderOutputPort {
    override fun save(order: Order): Order {
        return repository.save(order)
    }

    override fun getById(id: BigInteger): Order? {
        return repository.findByIdOrNull(id)
    }

}