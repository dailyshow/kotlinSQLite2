package com.cis.kotlinsqlite2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context: Context): SQLiteOpenHelper(context, "Test2.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("msg", "onCreate")

        var sql = "create table TestTable (" +
                "idx integer primary key autoincrement," +
                "textData text not null," +
                "intData integer not null," +
                "floatData real not null," +
                "dateData date not null" +
                ")"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d("msg", "oldVersion : ${oldVersion}, newVersion : ${newVersion}")
    }
}