package com.ilyeong.movieverse.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionIdLocalDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val sessionIdKey = stringPreferencesKey("session_id")

    suspend fun getSessionId(): String =
        dataStore.data.map { preferences -> preferences[sessionIdKey] ?: "" }.first()

    suspend fun saveSessionId(sessionId: String) {
        dataStore.edit { preferences -> preferences[sessionIdKey] = sessionId }
    }
}