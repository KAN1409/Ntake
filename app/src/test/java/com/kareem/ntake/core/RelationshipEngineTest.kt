package com.kareem.ntake.core
import org.junit.Assert.*;import org.junit.Test
class RelationshipEngineTest{@Test fun sameProjectConnects(){val a=Relatable(1,"quote",setOf("Pricing"),setOf("Negma"));val b=Relatable(2,"sent",setOf("Pricing"),setOf("Negma"));val r=RelationshipEngine.suggest(a,listOf(b));assertEquals(RelationType.SAME_PROJECT,r.first().type)}}
