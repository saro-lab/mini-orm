package me.saro.miniorm

import java.sql.Connection
import java.sql.DriverManager

class OrmDriverManager(
    private val url: String
) {
    fun getConnection(): Connection = DriverManager.getConnection(url)
}
