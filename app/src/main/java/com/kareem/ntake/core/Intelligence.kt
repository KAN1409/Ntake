package com.kareem.ntake.core
import java.net.URI
data class Understanding(val summary:String,val categories:Set<String>,val people:Set<String>,val projects:Set<String>,val urls:Set<String>,val followUp:Boolean,val reminder:Boolean,val confidence:Float)
object Intelligence {
 private val url=Regex("""https?://[^\s]+""",RegexOption.IGNORE_CASE)
 private val follow=Regex("""\b(wait|waiting|reply|respond|follow[ -]?up|send|call|check|tomorrow|due)\b|مستني|منتظر|رد|تابع|كلم|بكرة|ميعاد|ابعت""",setOf(RegexOption.IGNORE_CASE))
 private val reminder=Regex("""\b(remind|reminder|due|appointment|tomorrow)\b|فكرني|ميعاد|بكرة""",setOf(RegexOption.IGNORE_CASE))
 private val price=Regex("""\b(price|quote|quotation|cost|egp|usd)\b|سعر|عرض سعر|تكلفة""",setOf(RegexOption.IGNORE_CASE))
 private val idea=Regex("""\b(idea|concept|thought)\b|فكرة|خاطرة""",setOf(RegexOption.IGNORE_CASE))
 fun understand(raw:String):Understanding {
  val clean=raw.trim().replace(Regex("""\s+""")," "); val categories=linkedSetOf<String>()
  if(price.containsMatchIn(clean))categories+="Pricing";if(idea.containsMatchIn(clean))categories+="Idea";if(follow.containsMatchIn(clean))categories+="Follow-up";if(reminder.containsMatchIn(clean))categories+="Reminder";if(categories.isEmpty())categories+="Note"
  val urls=url.findAll(clean).map{it.value.trimEnd('.',',',')')}.toSet()
  val projects=Regex("""\b[A-Z][A-Za-z0-9_-]{2,}\b""").findAll(clean).map{it.value}.filterNot{it in setOf("The","This","Tomorrow","EGP","USD")}.take(8).toSet()
  val summary=if(clean.length<=220)clean else clean.take(217)+"…"
  return Understanding(summary,categories,emptySet(),projects,urls,follow.containsMatchIn(clean),reminder.containsMatchIn(clean),if(clean.isBlank())0f else .72f)
 }
 fun normalizeUrl(value:String)=runCatching{URI(value).normalize().toString()}.getOrDefault(value)
}