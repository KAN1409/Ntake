package com.kareem.ntake.core
data class SearchDocument(val id:Long,val title:String,val body:String,val summary:String,val tags:String)
data class RankedResult(val id:Long,val score:Double,val reasons:List<String>)
object SearchEngine{
 fun rank(query:String,docs:List<SearchDocument>):List<RankedResult>{val q=tokens(query);if(q.isEmpty())return docs.map{RankedResult(it.id,0.0,listOf("Recent"))}
  return docs.map{d->val title=tokens(d.title);val body=tokens(d.body+" "+d.summary+" "+d.tags);var score=0.0;val why=mutableListOf<String>();q.forEach{token->if(title.contains(token)){score+=5;why+="title"};if(body.contains(token)){score+=2;why+="content"};if(title.any{editDistance(it,token)<=1}||body.any{editDistance(it,token)<=1}){score+=.8;why+="fuzzy"}};RankedResult(d.id,score,why.distinct())}.filter{it.score>0}.sortedByDescending{it.score}}
 private fun tokens(s:String)=s.lowercase().split(Regex("""[^\p{L}\p{N}]+""")).filter{it.length>1}.toSet()
 private fun editDistance(a:String,b:String):Int{if(kotlin.math.abs(a.length-b.length)>1)return 99;val dp=IntArray(b.length+1){it};for(i in 1..a.length){var prev=dp[0];dp[0]=i;for(j in 1..b.length){val old=dp[j];dp[j]=minOf(dp[j]+1,dp[j-1]+1,prev+if(a[i-1]==b[j-1])0 else 1);prev=old}};return dp[b.length]}
}