package com.mattiasossai.binancepushtrader

object SignalParser {
    fun parse(text: String): Signal? {
        // Beispiel: "Order filled: BTCUSDT BUY 0.1"
        val parts = text.split("\\s+".toRegex())
        return try {
            val symbol = parts[2]
            val side = parts[3]
            val qty = parts[4].toDouble()
            Signal(symbol, side, qty)
        } catch (e: Exception) {
            null
        }
    }
}
