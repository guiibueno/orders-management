package com.bueno.orders.domain.entity

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class AddressTest {

    // Teste de um endereço válido
    @Test
    fun `should create address with valid fields`() {
        val address = Address(
            street = "Rua Teste",
            number = "123",
            city = "Cidade Exemplo",
            state = "SP",
            country = "Brasil",
            zipCode = "12345-678"
        )

        // Verificando que o endereço foi criado sem exceções
        assertEquals("Rua Teste", address.street)
        assertEquals("123", address.number)
        assertEquals("Cidade Exemplo", address.city)
        assertEquals("SP", address.state)
        assertEquals("Brasil", address.country)
        assertEquals("12345-678", address.zipCode)
    }

    // Teste de exceção quando o campo street é maior que 50 caracteres
    @Test
    fun `should throw exception when street exceeds 50 characters`() {
        assertThrows<IllegalArgumentException> {
            Address(
                street = "Rua Teste com um nome de rua que é muito longo para o campo",
                number = "123",
                city = "Cidade Exemplo",
                state = "SP",
                country = "Brasil",
                zipCode = "12345-678"
            )
        }
    }

    // Teste de exceção quando o campo number é maior que 10 caracteres
    @Test
    fun `should throw exception when number exceeds 10 characters`() {
        assertThrows<IllegalArgumentException> {
            Address(
                street = "Rua Teste",
                number = "12345678901",  // Mais que 10 caracteres
                city = "Cidade Exemplo",
                state = "SP",
                country = "Brasil",
                zipCode = "12345-678"
            )
        }
    }

    // Teste de exceção quando o campo city é maior que 50 caracteres
    @Test
    fun `should throw exception when city exceeds 50 characters`() {
        assertThrows<IllegalArgumentException> {
            Address(
                street = "Rua Teste",
                number = "123",
                city = "Cidade Exemplo com um nome de cidade muito longo ok",
                state = "SP",
                country = "Brasil",
                zipCode = "12345-678"
            )
        }
    }

    // Teste de exceção quando o campo state não tem exatamente 2 caracteres
    @Test
    fun `should throw exception when state is not 2 characters`() {
        assertThrows<IllegalArgumentException> {
            Address(
                street = "Rua Teste",
                number = "123",
                city = "Cidade Exemplo",
                state = "SPBrasil",  // Estado maior que 2 caracteres
                country = "Brasil",
                zipCode = "12345-678"
            )
        }
    }

    // Teste de exceção quando o campo country não está no intervalo de 3 a 120 caracteres
    @Test
    fun `should throw exception when country length is out of range`() {
        assertThrows<IllegalArgumentException> {
            Address(
                street = "Rua Teste",
                number = "123",
                city = "Cidade Exemplo",
                state = "SP",
                country = "AB",  // Nome do país com menos de 3 caracteres
                zipCode = "12345-678"
            )
        }
    }

    // Teste de exceção quando o zipCode não segue o formato esperado
    @Test
    fun `should throw exception when zipCode is invalid`() {
        assertThrows<IllegalArgumentException> {
            Address(
                street = "Rua Teste",
                number = "123",
                city = "Cidade Exemplo",
                state = "SP",
                country = "Brasil",
                zipCode = "123456"  // Formato inválido de CEP
            )
        }
    }

    // Teste de exceção quando o zipCode tem formato errado
    @Test
    fun `should throw exception when zipCode format is incorrect`() {
        assertThrows<IllegalArgumentException> {
            Address(
                street = "Rua Teste",
                number = "123",
                city = "Cidade Exemplo",
                state = "SP",
                country = "Brasil",
                zipCode = "12.345-67X"  // Formato de CEP inválido
            )
        }
    }
}
