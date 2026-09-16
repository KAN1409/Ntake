package com.kareem.ntake
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
class NtakeWidget : AppWidgetProvider() {
 override fun onUpdate(context:Context, manager:AppWidgetManager, ids:IntArray) {
  ids.forEach { id ->
   val views=RemoteViews(context.packageName,R.layout.widget_ntake)
   views.setOnClickPendingIntent(R.id.w_note,captureIntent(context,"text",1))
   views.setOnClickPendingIntent(R.id.w_voice,captureIntent(context,"voice",2))
   views.setOnClickPendingIntent(R.id.w_scan,captureIntent(context,"image",3))
   manager.updateAppWidget(id,views)
  }
 }
 private fun captureIntent(context:Context,mode:String,code:Int):PendingIntent {
  val intent=Intent(context,MainActivity::class.java).putExtra("capture",mode).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
  return PendingIntent.getActivity(context,code,intent,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
 }
}