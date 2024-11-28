package com.bueno.orders.infraestructure.adapters.input.rest

import com.bueno.orders.application.dto.request.CreateOrderRequest
import com.bueno.orders.application.dto.response.CreateOrderResponse
import com.bueno.orders.application.dto.response.OrderDto
import com.bueno.orders.application.dto.response.OrderListItem
import com.bueno.orders.application.port.input.CreateOrderPort
import com.bueno.orders.application.port.input.OrderFinderPort
import com.bueno.orders.domain.valueobject.OrderStatus
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigInteger

@RestController
@RequestMapping("/orders")
class OrderRestAdapter(
    val createOrderPort: CreateOrderPort,
    val orderFinderPort: OrderFinderPort
) {
    @PostMapping("")
    fun create(@RequestBody request: CreateOrderRequest): ResponseEntity<CreateOrderResponse?> {
        val response = createOrderPort.invoke(request)

        return ResponseEntity(response, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: BigInteger): ResponseEntity<OrderDto?> {
        val result = orderFinderPort.findById(id)
        if(result != null)
            return ResponseEntity(result, HttpStatus.OK)

        return ResponseEntity(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("")
    fun findAll(@RequestParam status: OrderStatus): ResponseEntity<List<OrderListItem>> {
        val result = orderFinderPort.findAll(status)
        if(result.isNotEmpty())
            return ResponseEntity(result, HttpStatus.OK)

        return ResponseEntity(null, HttpStatus.NO_CONTENT);
    }
}