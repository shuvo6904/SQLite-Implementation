package com.example.sqliteimplementation

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context : Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "EmployeeDatabase"
        private const val TABLE_CONTACTS = "EmployeeTable"
        private const val KEY_ID = "_id"
        private const val KEY_NAME = "name"
        private const val KEY_EMAIL = "email"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT" + ")")
        p0?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(p0)
    }

    fun addEmployee(emp : EmpModelClass) : Long{

        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, emp.name)
        contentValues.put(KEY_EMAIL, emp.email)

        val success = db.insert(TABLE_CONTACTS, null, contentValues)

        db.close()

        return success
    }

    fun viewEmployee(): ArrayList<EmpModelClass> {

        val empList = ArrayList<EmpModelClass>()

        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"

        val db = this.readableDatabase

        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var name: String
        var email: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                email = cursor.getString(cursor.getColumnIndex(KEY_EMAIL))

                val emp = EmpModelClass(id = id, name = name, email = email)
                empList.add(emp)

            } while (cursor.moveToNext())
        }
        return empList
    }

    fun updateEmployee(emp: EmpModelClass) : Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, emp.name)
        contentValues.put(KEY_EMAIL, emp.email)

        val success = db.update(TABLE_CONTACTS, contentValues, KEY_ID + "=" + emp.id, null)
        db.close()
        return success
    }

    fun deleteEmployee(emp : EmpModelClass) : Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.id)

        val success = db.delete(TABLE_CONTACTS, KEY_ID + "=" + emp.id, null)
        db.close()
        return success
    }

}