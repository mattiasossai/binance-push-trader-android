package com.mattiasossai.binancepushtrader

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification

class NotificationService : NotificationListenerService() {
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        // TODO: Binance-Push filtern und weiterverarbeiten
    }
}