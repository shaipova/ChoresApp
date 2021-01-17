package com.example.choresapp.activity

import android.app.ProgressDialog
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.choresapp.R
import com.example.choresapp.data.ChoreListAdapter
import com.example.choresapp.data.ChoresDatabaseHandler
import com.example.choresapp.model.Chore
import kotlinx.android.synthetic.main.activity_chore_list.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var dbHandler = ChoresDatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkDB()

        saveChore.setOnClickListener {
            if (!TextUtils.isEmpty(enterChore.text.toString()) && !TextUtils.isEmpty(assignedById.text.toString()) && !TextUtils.isEmpty(assignToId.text.toString())) {

                var chore = Chore()
                chore.choreName = enterChore.text.toString()
                chore.assignedTo = assignToId.text.toString()
                chore.assignedBy = assignedById.text.toString()

                //save to database
                dbHandler.createChore(chore)

                startActivity(Intent(this, ChoreListActivity::class.java))
            } else {
                Toast.makeText(this, "Please enter a chore", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkDB(){
        if (dbHandler.getChoresCount() > 0) {
            startActivity(Intent(this, ChoreListActivity::class.java))
        }
    }
}