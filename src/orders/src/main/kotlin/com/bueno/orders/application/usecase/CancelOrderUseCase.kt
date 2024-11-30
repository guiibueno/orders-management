package com.bueno.orders.application.usecase

import com.bueno.orders.application.dto.response.CancelOrderResponse
import com.bueno.orders.application.dto.response.CancelOrderStatus
import com.bueno.orders.application.port.input.CancelOrderPort
import com.bueno.orders.application.port.output.OrderOutputPort
import com.bueno.orders.domain.valueobject.OrderStatus
import org.springframework.stereotype.Service
import java.math.BigInteger

@Service
class CancelOrderUseCase(
    val orderOutputPort: OrderOutputPort
): CancelOrderPort {
    override fun invoke(id: BigInteger): CancelOrderResponse? {
        val order = orderOutputPort.getById(id) ?: return null

        val updateResult = order.updateStatus(OrderStatus.CANCELED)

        if(updateResult.first){
            orderOutputPort.save(order)
            return CancelOrderResponse(id, CancelOrderStatus.CANCELED, updateResult.second)
        }

        return CancelOrderResponse(id, CancelOrderStatus.ERROR, updateResult.second)
    }
}