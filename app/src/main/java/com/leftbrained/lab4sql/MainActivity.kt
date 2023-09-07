package com.leftbrained.lab4sql

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    final val TAG = "TEST_LOG"

    val db = DbHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view: View) {
        val cv = ContentValues()
        val textName = findViewById<TextInputEditText>(R.id.textName).text.toString()
        val textEmail = findViewById<TextInputEditText>(R.id.textEmail).text.toString()
        when (view.id) {
            R.id.btnAdd -> {
                cv.put("name", textName)
                cv.put("email", textEmail)
                val row = db.writableDatabase.insert("users", null, cv)
                Log.d(TAG, "Added row: $row")
            }
            R.id.btnRead -> {
                val cursor = db.readableDatabase.query("users", null, null, null, null, null, null)
                if (cursor.moveToFirst()) {
                    do {
                        val idIndex = cursor.getColumnIndex("id")
                        val nameIndex = cursor.getColumnIndex("name")
                        val emailIndex = cursor.getColumnIndex("email")
                        val viewName = findViewById<TextView>(R.id.viewName)

                        if (idIndex != -1 && nameIndex != -1 && emailIndex != -1) {
                            Log.d(TAG, "ID: ${cursor.getInt(idIndex)}")
                            Log.d(TAG, "Name: ${cursor.getString(nameIndex)}")
                            Log.d(TAG, "Email: ${cursor.getString(emailIndex)}")
                            viewName.text = cursor.getString(nameIndex)
                        } else {
                            Log.e(TAG, "Columns not found in the cursor!")
                        }
                    } while (cursor.moveToNext())
                }
                cursor.close()
            }
            R.id.btnClear -> {
                val row = db.writableDatabase.delete("users", null, null)
                Log.d(TAG, "Deleted rows: $row")
            }
        }
        db.close()
    }
}