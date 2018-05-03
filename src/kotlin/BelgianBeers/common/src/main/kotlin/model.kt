package org.jetbrains.kotlincsharpdemo

data class Brewery(val Name: String)

data class Beer(val Name: String,
                val Brewery: Brewery?,
                val Rating: Double,
                val Votes: Double)

data class BeerItem(
        val name: String?,
        val brewery: String?,
        val rating: Double?,
        val votes: Double?
)
