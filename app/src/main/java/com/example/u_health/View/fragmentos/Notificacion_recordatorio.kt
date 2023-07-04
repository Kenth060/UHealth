package com.example.u_health.View.fragmentos

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.u_health.CantidadPastillaDec
import com.example.u_health.R
import com.example.u_health.View.Login
import com.example.u_health.model.Medicamentos

const val notificationRecordatorioID = 2
const val channelRecordatorioID = "channel2"
const val titleExtraRecordatorio = "titleExtra2"
const val messageExtraRecordatorio = "messageExtra2"


class Notificacion_recordatorio : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationIntent = Intent(context, CantidadPastillaDec::class.java)

        var Id=intent.getStringExtra("Id")
        var Pastilla=intent.getStringExtra("Pastilla")
        var Dosis=intent.getStringExtra("Dosis")
        var Hora=intent.getStringExtra("Hora")
        var Cantidad=intent.getStringExtra("Cantidad")

        notificationIntent.putExtra("Id",Id)
        notificationIntent.putExtra("Pastilla",Pastilla)
        notificationIntent.putExtra("Dosis",Dosis)
        notificationIntent.putExtra("Hora",Hora)
        notificationIntent.putExtra("Cantidad",Cantidad)

        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        val notificacion = NotificationCompat.Builder(context, channelRecordatorioID)
            .setSmallIcon(R.drawable.ic_uhealth)
            .setContentTitle(intent.getStringExtra(titleExtraRecordatorio))
            .setContentText(intent.getStringExtra(messageExtraRecordatorio))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationRecordatorioID,notificacion)

    }
}