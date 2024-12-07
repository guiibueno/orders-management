package com.bueno.orders.infraestructure.adapters.output.persistence.repository

import com.bueno.orders.domain.entity.Order
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.math.BigInteger

@Repository
interface OrderRepository: MongoRepository<Order, BigInteger> {
    fun findByStatus(status: String): List<Order>
}