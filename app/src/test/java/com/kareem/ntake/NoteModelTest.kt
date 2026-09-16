package com.kareem.ntake
import org.junit.Assert.*;import org.junit.Test
class NoteModelTest{@Test fun sourceTruthIsKept(){val n=Note(1,"t","original","VOICE",1);assertEquals("original",n.body);assertEquals("VOICE",n.source)}@Test fun followUpStateIsExplicit(){assertTrue(Note(1,"q","waiting","TEXT",1,followUp=true).followUp)}}