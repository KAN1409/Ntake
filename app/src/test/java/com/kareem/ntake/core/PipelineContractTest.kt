package com.kareem.ntake.core
import org.junit.Assert.*;import org.junit.Test
class PipelineContractTest{
 @Test fun understandingProducesSearchableClassification(){val u=Intelligence.understand("Negma marble quotation 2450 EGP tomorrow");val d=SearchDocument(7,"Negma marble",u.summary,u.summary,u.categories.joinToString(","));assertTrue("Pricing" in u.categories);assertTrue(u.followUp);assertEquals(7,SearchEngine.rank("marbel price",listOf(d)).first().id)}
 @Test fun relationshipUsesDerivedProject(){val a=Intelligence.understand("Negma quotation");val b=Intelligence.understand("Negma ceiling idea");val r=RelationshipEngine.suggest(Relatable(1,a.summary,a.categories,a.projects),listOf(Relatable(2,b.summary,b.categories,b.projects)));assertTrue(r.isNotEmpty())}
}