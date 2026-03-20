package com.example.amazon_sim.domain.model

data class Address(
    val id: String,
    val fullName: String,
    val phoneNumber: String,
    val country: String = "United States",
    val streetAddress: String,
    val aptSuite: String = "",
    val city: String,
    val state: String = "California",
    val zipCode: String,
    val isDefault: Boolean = false
)
