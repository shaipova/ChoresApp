package com.example.choresapp.model

val DATABASE_VERSION: Int = 1
val DATABASE_NAME: String = "chore.db" // db - database
val TABLE_NAME: String = "chores"

// Chores table columns names

val KEY_ID: String = "id"
val KEY_CHORE_NAME: String = "chore_name"
val KEY_CHORE_ASSIGNED_BY: String = "chore_assigned_by"
val KEY_CHORE_ASSIGNED_TO: String = "chore_assigned_to"