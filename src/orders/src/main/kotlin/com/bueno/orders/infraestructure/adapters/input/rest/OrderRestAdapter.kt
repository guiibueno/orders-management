package com.bueno.orders.infraestructure.adapters.input.rest

import com.bueno.orders.application.dto.request.CreateOrderRequest
import com.bueno.orders.application.dto.response.*
import com.bueno.orders.application.port.input.CancelOrderPort
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
    val orderFinderPort: OrderFinderPort,
    val cancelOrderPort: CancelOrderPort
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
    fun findAll(@RequestParam status: OrderStatus?): ResponseEntity<List<OrderListItem>> {
        val result = orderFinderPort.findAll(status)
        if(result.isNotEmpty())
            return ResponseEntity(result, HttpStatus.OK)

        return ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/cancel")
    fun cancel(@PathVariable id: BigInteger): ResponseEntity<CancelOrderResponse> {
        val response = cancelOrderPort.invoke(id) ?: return ResponseEntity(null, HttpStatus.NOT_FOUND)

        val statusCode = when (response.status) {
            CancelOrderStatus.CANCELED -> HttpStatus.OK
            CancelOrderStatus.ERROR -> HttpStatus.BAD_REQUEST
        }

        return ResponseEntity(response, statusCode)
    }
}