package com.example.u_health.View.fragmentos

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.u_health.CantidadPastillaDec
import com.example.u_health.R
import com.example.u_health.View.MainActivity
import com.example.u_health.View.Menu

const val notificationID = 1
const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"
class Notification : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notificationIntent = Intent(context, Menu::class.java)
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)

        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        val notificacion = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_uhealth)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText(intent.getStringExtra(messageExtra))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID,notificacion)

    }
}