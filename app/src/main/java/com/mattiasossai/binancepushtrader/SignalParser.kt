package com.mattiasossai.binancepushtrader

import kotlin.math.absoluteValue

data class Signal(
    val symbol: String,
    val side: String,  // "BUY" oder "SELL"
    val qty: Double
)

object SignalParser {
    /**
     * Erwartetes Format:
     *   "Order filled SYMBOL ±MENGE"
     * Beispiel:
     *   "Order filled BTCUSDT -0.05255"  → SELL 0.05255
     *   "Order filled ETHUSDT 0.10000"   → BUY 0.10000
     */
    fun parse(text: String): Signal? {
        // Nur Zeilen mit "Order filled" berücksichtigen
        if (!text.startsWith("Order filled", ignoreCase = true)) return null

        // Text in Teile splitten
        val parts = text.split("\\s+".toRegex())
        if (parts.size < 4) return null

        // SYMBOL ist das 3. Element
        val symbol = parts[2]

        // Menge mit Vorzeichen ist das 4. Element
        val amountStr = parts[3]
        val rawQty = amountStr.toDoubleOrNull() ?: return null

        // Side bestimmen und Menge positiv machen
        val side = if (rawQty < 0) "SELL" else "BUY"
        val qty = rawQty.absoluteValue

        return Signal(symbol = symbol, side = side, qty = qty)
    }
}
