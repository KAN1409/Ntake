package com.kareem.ntake.core
data class Relatable(val id:Long,val body:String,val tags:Set<String>,val projects:Set<String>)
object RelationshipEngine{
 fun suggest(current:Relatable,others:List<Relatable>):List<Relation>{return others.asSequence().filter{it.id!=current.id}.mapNotNull{other->
  val commonProjects=current.projects.intersect(other.projects);val commonTags=current.tags.intersect(other.tags).filterNot{it=="Note"}.toSet()
  when{commonProjects.isNotEmpty()->Relation(current.id,other.id,RelationType.SAME_PROJECT,"Shared project: "+commonProjects.joinToString(),.9f)
   commonTags.isNotEmpty()->Relation(current.id,other.id,RelationType.SAME_TOPIC,"Shared context: "+commonTags.joinToString(),.7f)
   else->null}}.sortedByDescending{it.confidence}.take(8).toList()}
}