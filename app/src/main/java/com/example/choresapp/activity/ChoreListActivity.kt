package com.example.choresapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.choresapp.R
import com.example.choresapp.data.ChoreListAdapter
import com.example.choresapp.data.ChoresDatabaseHandler
import com.example.choresapp.model.Chore
import kotlinx.android.synthetic.main.activity_chore_list.*
import kotlinx.android.synthetic.main.popup.view.*

class ChoreListActivity : AppCompatActivity() {
    private var dbHandler = ChoresDatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chore_list)

        val choreListItems = ArrayList<Chore>()
        val adapter = ChoreListAdapter(choreListItems, this)
        val layoutManager = LinearLayoutManager(this)

        recyclerViewId.layoutManager = layoutManager
        recyclerViewId.adapter = adapter

        var choreList: ArrayList<Chore> = dbHandler.readChores()
        choreList.reverse()

        for (c in choreList.iterator()) {
            val chore = Chore()
            chore.choreName = "Chore: ${c.choreName}"
            chore.assignedBy = "From: ${c.assignedBy}"
            chore.assignedTo = "To: ${c.assignedTo}"
            chore.id = c.id

            choreListItems.add(chore)
        }

        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_menu_button) {
            Log.d("Item clicked", "Item clicked")
            createPopupDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createPopupDialog() {
        val viewPopup = layoutInflater.inflate(R.layout.popup, null)
        val choreName = viewPopup.popEnterChore
        val assignedBy = viewPopup.popAssignedById
        val assignedTo = viewPopup.popAssignToId
        val saveButton = viewPopup.popSaveChore

        val dialogBuilder = AlertDialog.Builder(this).setView(viewPopup)
        val dialog = dialogBuilder.create()
        dialog.show()

        saveButton.setOnClickListener {
            if (!TextUtils.isEmpty(choreName.text.toString().trim())
                && !TextUtils.isEmpty(assignedBy.text.toString().trim())
                && !TextUtils.isEmpty(assignedTo.text.toString().trim())) {

                var chore = Chore()
                chore.choreName = choreName.text.toString()
                chore.assignedTo = assignedTo.text.toString()
                chore.assignedBy = assignedBy.text.toString()

                dbHandler.createChore(chore)
                dialog.dismiss()

                startActivity(Intent(this, ChoreListActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Please enter a chore", Toast.LENGTH_LONG).show()
            }
        }
    }
}