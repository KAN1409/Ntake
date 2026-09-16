package com.kareem.ntake
import android.app.Application
class NtakeApp:Application(){lateinit var repo:NoteRepository;override fun onCreate(){super.onCreate();repo=NoteRepository(this)}}