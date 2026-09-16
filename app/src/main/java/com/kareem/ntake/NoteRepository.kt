package com.kareem.ntake
import android.content.*
import android.database.sqlite.*
import com.kareem.ntake.core.*
import kotlinx.coroutines.flow.*

data class Note(val id:Long,val title:String,val body:String,val source:String,val createdAt:Long,val status:String="READY",val tags:String="",val summary:String="",val followUp:Boolean=false,val dueAt:Long?=null)

class NoteDb(c:Context):SQLiteOpenHelper(c,"ntake.db",null,2){
 override fun onCreate(db:SQLiteDatabase){
  db.execSQL("CREATE TABLE notes(id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT NOT NULL,body TEXT NOT NULL,source TEXT NOT NULL,createdAt INTEGER NOT NULL,status TEXT NOT NULL,tags TEXT NOT NULL,summary TEXT NOT NULL,followUp INTEGER NOT NULL DEFAULT 0,dueAt INTEGER)")
  db.execSQL("CREATE INDEX idx_notes_created ON notes(createdAt DESC)")
 }
 override fun onUpgrade(db:SQLiteDatabase,o:Int,n:Int){}
}

class NoteRepository(c:Context){
 private val db=NoteDb(c)
 private val _notes=MutableStateFlow<List<Note>>(emptyList());val notes=_notes.asStateFlow()
 init{refresh()}
 @Synchronized fun capture(title:String,body:String,source:String,dueAt:Long?=null):Long{
  val u=Intelligence.understand(body)
  val v=ContentValues().apply{put("title",title.ifBlank{titleFromBody(body)});put("body",body);put("source",source);put("createdAt",System.currentTimeMillis());put("status","READY");put("tags",u.categories.joinToString(","));put("summary",u.summary);put("followUp",if(u.followUp)1 else 0);if(dueAt!=null)put("dueAt",dueAt)}
  val id=db.writableDatabase.insertOrThrow("notes",null,v);refresh();return id
 }
 @Deprecated("Use capture so every note goes through understanding")
 @Synchronized fun add(title:String,body:String,source:String,tags:String="",summary:String="",followUp:Boolean=false,dueAt:Long?=null)=capture(title,body,source,dueAt)
 @Synchronized fun setResolved(id:Long){db.writableDatabase.execSQL("UPDATE notes SET status='RESOLVED' WHERE id=?",arrayOf(id));refresh()}
 @Synchronized fun search(q:String):List<Note>{
  val all=_notes.value
  if(q.isBlank())return all
  val docs=all.map{SearchDocument(it.id,it.title,it.body,it.summary,it.tags)}
  val order=SearchEngine.rank(q,docs).mapIndexed{index,r->r.id to index}.toMap()
  return all.filter{it.id in order}.sortedBy{order[it.id]}
 }
 @Synchronized fun connections(id:Long):List<Relation>{
  val all=_notes.value
  val current=all.firstOrNull{it.id==id}?:return emptyList()
  fun rel(n:Note):Relatable{val u=Intelligence.understand(n.body);return Relatable(n.id,n.body,n.tags.split(",").filter{it.isNotBlank()}.toSet(),u.projects)}
  return RelationshipEngine.suggest(rel(current),all.map(::rel))
 }
 @Synchronized fun refresh(){_notes.value=query("SELECT * FROM notes ORDER BY createdAt DESC",emptyArray())}
 private fun query(sql:String,args:Array<String>):List<Note>{val out=mutableListOf<Note>();db.readableDatabase.rawQuery(sql,args).use{c->while(c.moveToNext())out+=Note(c.getLong(0),c.getString(1),c.getString(2),c.getString(3),c.getLong(4),c.getString(5),c.getString(6),c.getString(7),c.getInt(8)==1,if(c.isNull(9))null else c.getLong(9))};return out}
 private fun titleFromBody(s:String)=s.trim().lineSequence().firstOrNull()?.take(52)?.ifBlank{"New note"}?:"New note"
}
