package com.kareem.ntake.core
import android.app.*;import android.content.Context;import android.content.Intent;import android.os.Build;import com.kareem.ntake.ReminderReceiver
class ReminderScheduler(private val context:Context){
 fun schedule(noteId:Long,title:String,at:Long):Boolean{if(at<=System.currentTimeMillis())return false;val am=context.getSystemService(AlarmManager::class.java);val pi=pending(noteId,title);return runCatching{if(Build.VERSION.SDK_INT < Build.VERSION_CODES.S || am.canScheduleExactAlarms()) am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,at,pi) else am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,at,pi);true}.getOrDefault(false)}
 fun cancel(noteId:Long){context.getSystemService(AlarmManager::class.java).cancel(pending(noteId,""))}
 private fun pending(id:Long,title:String)=PendingIntent.getBroadcast(context,(id xor(id ushr 32)).toInt(),Intent(context,ReminderReceiver::class.java).putExtra("noteId",id).putExtra("title",title),PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
}