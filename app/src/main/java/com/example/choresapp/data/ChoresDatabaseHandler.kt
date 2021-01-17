package com.example.choresapp.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.choresapp.model.*
import java.text.DateFormat
import java.util.*

class ChoresDatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        // SQL - Structured Query Language
        val CREATE_CHORE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," +
                                        KEY_CHORE_NAME + " TEXT," +
                                        KEY_CHORE_ASSIGNED_BY + " TEXT," +
                                        KEY_CHORE_ASSIGNED_TO + " TEXT," + ")"
        db?.execSQL(CREATE_CHORE_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)

        // create table again
        onCreate(db)
    }

    // CRUD - Create, Read, Update, Delete

    fun createChore(chore: Chore) {
        val db: SQLiteDatabase = writableDatabase
        val values: ContentValues = ContentValues()
        values.put(KEY_CHORE_NAME, chore.choreName)
        values.put(KEY_CHORE_ASSIGNED_TO, chore.assignedTo)
        values.put(KEY_CHORE_ASSIGNED_BY, chore.assignedBy)

        db.insert(TABLE_NAME, null, values)

        Log.d("DATA INSERTED", "success")
        db.close()
    }

    //never used
    fun readAChore(id: Int): Chore {
        val db: SQLiteDatabase = writableDatabase
        val cursor: Cursor = db.query(TABLE_NAME, arrayOf(KEY_ID, KEY_CHORE_NAME, KEY_CHORE_ASSIGNED_BY,
                            KEY_CHORE_ASSIGNED_TO), KEY_ID + "=?", arrayOf(id.toString()),
                          null, null, null, null)

        cursor.moveToFirst()
            var chore = Chore()
            chore.choreName = cursor.getString(cursor.getColumnIndex(KEY_CHORE_NAME))
            chore.assignedBy = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_BY))
            chore.assignedTo = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TO))

        return chore;
    }

    fun readChores(): ArrayList<Chore> {
        val db: SQLiteDatabase = writableDatabase
        val list: ArrayList<Chore> = ArrayList()

        //select all chores from table
        val selectAll = "SELECT * FROM " + TABLE_NAME
        val cursor: Cursor = db.rawQuery(selectAll, null)

        //loop through our chores
        if(cursor.moveToFirst()) {
            do {
                var chore = Chore()
                chore.id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                chore.choreName = cursor.getString(cursor.getColumnIndex(KEY_CHORE_NAME))
                chore.assignedBy = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_BY))
                chore.assignedTo = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TO))

                list.add(chore)

            } while (cursor.moveToNext())
        }
        return list
    }

    fun updateChore(chore: Chore) : Int {
        val db: SQLiteDatabase = writableDatabase
        val values: ContentValues = ContentValues()
        values.put(KEY_CHORE_NAME, chore.choreName)
        values.put(KEY_CHORE_ASSIGNED_TO, chore.assignedTo)
        values.put(KEY_CHORE_ASSIGNED_BY, chore.assignedBy)

        //update a row
        return db.update(TABLE_NAME, values, KEY_ID + "=?", arrayOf(chore.id.toString()))
    }

    fun deleteChore(id: Int) {
        val db: SQLiteDatabase = writableDatabase
        db.delete(TABLE_NAME, KEY_ID + "=?", arrayOf(id.toString()))
        db.close()
    }

    fun getChoresCount(): Int {
        val db: SQLiteDatabase = writableDatabase
        val countQuery = "SELECT * FROM " + TABLE_NAME
        val cursor: Cursor = db.rawQuery(countQuery, null)

        return cursor.count
    }
}