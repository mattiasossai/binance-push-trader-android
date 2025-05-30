package com.mattiasossai.binancepushtrader

import kotlin.math.absoluteValue

data class Signal(
    val symbol: String,
    val side: String,  // "BUY" oder "SELL"
    val qty: Double
)

object SignalParser {
    /**
     * Erwartetes Notification-Text-Format:
     *   "Your <SYMBOL> order in Futures Copy Trading has been filled. Entry Price <PRICE>, Quantity: <QTY>"
     * Beispiele:
     *   "Your ENAUSDT order in Futures Copy Trading has been filled. Entry Price 0.3549, Quantity: 166"
     *   "Your 1000PEPEUSDT order in Futures Copy Trading has been filled. Entry Price 0.0129155, Quantity: -4581"
     */
    fun parse(text: String): Signal? {
        // Regex für Symbol & Qty
        val regex = """Your\s+([A-Z0-9]+)\s+order.*Quantity:\s*(-?\d+(\.\d+)?)""".toRegex()
        val match = regex.find(text) ?: return null

        val symbol = match.groupValues[1]
        val rawQty = match.groupValues[2].toDoubleOrNull() ?: return null
        val side = if (rawQty < 0) "SELL" else "BUY"
        val qty = rawQty.absoluteValue

        return Signal(symbol = symbol, side = side, qty = qty)
    }
}
