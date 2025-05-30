package com.mattiasossai.binancepushtrader

import android.app.Notification
import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class NotificationService : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val pkg = sbn.packageName
        // Nur Binance-Apps zulassen
        if (!pkg.startsWith("com.binance")) return

        val extras = sbn.notification.extras
        // bigText bevorzugen, dann text
        val text = extras.getCharSequence(Notification.EXTRA_BIG_TEXT)
            ?.toString()
            ?: extras.getCharSequence(Notification.EXTRA_TEXT)?.toString()
            ?: return

        // Versuche, ein Signal zu parsen
        SignalParser.parse(text)?.let { signal ->
            // Neues Signal broadcasten
            Intent("com.mattiasossai.NEW_SIGNAL").also { i ->
                i.putExtra("signal_symbol", signal.symbol)
                i.putExtra("signal_side",   signal.side)
                i.putExtra("signal_qty",    signal.qty)
                sendBroadcast(i)
            }
            Log.d("NotifService", "Signal broadcasted: $signal")
        }
    }
}
