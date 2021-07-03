package softing.ubah4ukdev.moviesinfosearcher.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import softing.ubah4ukdev.moviesinfosearcher.R

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.fcm

Created by Ivan Sheynmaer

2021.07.03
v1.0
 */
class FcmService : FirebaseMessagingService() {

    companion object {
        private const val CHANNEL_ID = "CHANNEL_ID"
        private const val CHANNEL_NAME = "Channel Name"
        private const val CHANNEL_DISCRIPTION = "Channel Description"
    }

    private val notificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = CHANNEL_DISCRIPTION
            }.also {
                notificationManager.createNotificationChannel(it)
            }
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        message.notification?.apply {
            showNotification(this)
        }
    }

    private fun showNotification(notification: RemoteMessage.Notification) {
        NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setContentTitle(notification.title)
            setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(notification.body)
            )
            //setContentText(notification.body)
            setSmallIcon(R.drawable.ic_mail)
            setAutoCancel(true)
        }.also {
            notificationManager.notify(1, it.build())
        }
    }
}