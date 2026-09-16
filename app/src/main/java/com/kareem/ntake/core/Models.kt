package com.kareem.ntake.core
enum class SourceType{TEXT,VOICE,IMAGE,URL,SHARE}
enum class ProcessingStage{CAPTURED,EXTRACTING,UNDERSTANDING,INDEXING,READY,FAILED}
enum class FollowUpStatus{OPEN,WAITING,DUE,OVERDUE,RESOLVED}
enum class RelationType{SAME_PERSON,SAME_PROJECT,SAME_TOPIC,FOLLOWS_UP,REFERENCES}
data class SourceArtifact(val uri:String?,val mime:String?,val originalText:String?,val checksum:String?)
data class Relation(val fromId:Long,val toId:Long,val type:RelationType,val reason:String,val confidence:Float)
