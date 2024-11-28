package com.bueno.orders.domain.entity

import java.util.regex.Pattern

data class Address (val street: String,
                    val number: String,
                    val city: String,
                    val state: String,
                    val country: String,
                    val zipCode: String) {
    init {
        validate()
    }

    private fun validate() {
        require(isValidStreet)
        require(isValidNumber)
        require(isValidCity)
        require(isValidState)
        require(isValidCountry)
        require(isValidZipCode())
    }

    private val isValidStreet: Boolean
        get() = street.length <= 50

    private val isValidNumber: Boolean
        get() = number.length <= 10

    private val isValidCity: Boolean
        get() = city.length <= 50

    private val isValidState: Boolean
        get() = state.length == 2

    private val isValidCountry: Boolean
        get() = country.length in 3..120

    private fun isValidZipCode(): Boolean {
        val pattern = Pattern.compile("^(([0-9]{2}\\.[0-9]{3}-[0-9]{3})|([0-9]{2}[0-9]{3}-[0-9]{3})|([0-9]{8}))$")
        val matcher = pattern.matcher(zipCode)
        return matcher.find()
    }
}