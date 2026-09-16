package com.kareem.ntake.core
import org.junit.Assert.*;import org.junit.Test
class IntelligenceTest{
 @Test fun mixedLanguageFollowup(){val u=Intelligence.understand("Ahmed هيبعت quotation بتاع Negma بكرة");assertTrue(u.followUp);assertTrue("Pricing" in u.categories);assertTrue("Reminder" in u.categories)}
 @Test fun arabicPricing(){val u=Intelligence.understand("سعر Marble الجديد 2450 EGP/m²");assertTrue("Pricing" in u.categories)}
 @Test fun urlPreserved(){val u=Intelligence.understand("راجع https://example.com/a?id=4 بكرة");assertEquals(1,u.urls.size);assertTrue(u.reminder)}
}