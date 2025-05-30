package com.mattiasossai.binancepushtrader

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.content.Intent
import android.util.Log

class NotificationService : NotificationListenerService() {
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val pkg = sbn.packageName
        if (pkg != "com.binance.dev" && pkg != "com.binance.one") return  // je nach App-Paketname anpassen

        val extras = sbn.notification.extras
        val title = extras.getString("android.title") ?: return
        val text = extras.getCharSequence("android.text")?.toString() ?: return

        // Beispiel-Filter: nur Order-Filled-Nachrichten
        if (title.contains("Order filled", ignoreCase = true)
            || text.contains("Position closed", ignoreCase = true)) {

            // Signal aufbereiten
            val signal = SignalParser.parse(text)
            if (signal != null) {
                // an MainActivity senden
                val i = Intent("com.mattiasossai.NEW_SIGNAL")
                i.putExtra("signal_symbol", signal.symbol)
                i.putExtra("signal_side", signal.side)
                i.putExtra("signal_qty", signal.qty)
                sendBroadcast(i)
                Log.d("NotifService", "Signal broadcasted: $signal")
            }
        }
    }
}
