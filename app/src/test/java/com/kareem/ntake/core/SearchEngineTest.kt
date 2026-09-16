package com.kareem.ntake.core
import org.junit.Assert.*;import org.junit.Test
class SearchEngineTest{
 private val docs=listOf(SearchDocument(1,"Negma marble","Ahmed quotation 2450 EGP","new marble price","Pricing"),SearchDocument(2,"Lighting idea","warm indirect lighting","concept","Idea"))
 @Test fun exactRanksEvidence(){assertEquals(1,SearchEngine.rank("marble price",docs).first().id)}
 @Test fun typoStillFinds(){assertEquals(1,SearchEngine.rank("marbel",docs).first().id)}
}