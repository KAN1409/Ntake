package com.kareem.ntake.core

data class SearchDocument(val id: Long, val title: String, val body: String, val summary: String, val tags: String)
data class RankedResult(val id: Long, val score: Double, val reasons: List<String>)

object SearchEngine {
 fun rank(query: String, docs: List<SearchDocument>): List<RankedResult> {
  val q=tokens(query); if(q.isEmpty()) return docs.map{RankedResult(it.id,0.0,listOf("Recent"))}
  return docs.map { d ->
   val title=tokens(d.title); val body=tokens(d.body+" "+d.summary+" "+d.tags); var score=0.0; val why=mutableListOf<String>()
   q.forEach { token ->
    if(token in title){score+=5.0;why+="title"}
    if(token in body){score+=2.0;why+="content"}
    if(title.any{fuzzyMatch(it,token)}||body.any{fuzzyMatch(it,token)}){score+=0.8;why+="fuzzy"}
   }
   RankedResult(d.id,score,why.distinct())
  }.filter{it.score>0.0}.sortedByDescending{it.score}
 }
 private fun tokens(s:String)=s.lowercase().split(Regex("""[^\p{L}\p{N}]+""")).filter{it.length>1}.toSet()
 private fun fuzzyMatch(a:String,b:String):Boolean {
  if(editDistance(a,b)<=1)return true
  if(a.length!=b.length||a.length<3)return false
  for(i in 0 until a.lastIndex){
   if(a[i]==b[i+1]&&a[i+1]==b[i]){
    val swapped=a.substring(0,i)+a[i+1]+a[i]+a.substring(i+2)
    if(swapped==b)return true
   }
  }
  return false
 }
 private fun editDistance(a:String,b:String):Int {
  if(kotlin.math.abs(a.length-b.length)>1)return 99
  val dp=IntArray(b.length+1){it}
  for(i in 1..a.length){var prev=dp[0];dp[0]=i;for(j in 1..b.length){val old=dp[j];dp[j]=minOf(dp[j]+1,dp[j-1]+1,prev+if(a[i-1]==b[j-1])0 else 1);prev=old}}
  return dp[b.length]
 }
}
