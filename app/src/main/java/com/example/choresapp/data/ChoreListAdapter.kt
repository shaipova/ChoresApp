package com.example.choresapp.data

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.choresapp.R
import com.example.choresapp.activity.ChoreListActivity
import com.example.choresapp.model.Chore
import kotlinx.android.synthetic.main.popup.view.*

class ChoreListAdapter(private val list: ArrayList<Chore>,
                       private val context: Context) : RecyclerView.Adapter<ChoreListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val choreName = itemView.findViewById<TextView>(R.id.listChoreName)
        val assignedBy = itemView.findViewById<TextView>(R.id.listAssignedBy)
        val assignedTo = itemView.findViewById<TextView>(R.id.listAssignedTo)
        val deleteBtn = itemView.findViewById<Button>(R.id.listDeleteButton)
        val editBtn = itemView.findViewById<Button>(R.id.listEditButton)

        fun bindViews(chore: Chore) {
            choreName.text = chore.choreName
            assignedBy.text = chore.assignedBy
            assignedTo.text = chore.assignedTo

            deleteBtn.setOnClickListener(this)
            editBtn.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            var mPosition = adapterPosition
            var chore = list[mPosition]

            when(view!!.id) {
                deleteBtn.id -> {
                    deleteChore(chore.id!!.toInt())
                    list.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }
                editBtn.id -> {
                    editChore(chore)
                }
            }
        }

        fun deleteChore(id: Int) {
            val db = ChoresDatabaseHandler(context)
            db.deleteChore(id)
        }

        fun editChore(chore: Chore) {
            val dbHandler = ChoresDatabaseHandler(context)
            val viewPopup = LayoutInflater.from(context).inflate(R.layout.popup, null)

            val choreName = viewPopup.popEnterChore
            val assignedBy = viewPopup.popAssignedById
            val assignedTo = viewPopup.popAssignToId
            val saveButton = viewPopup.popSaveChore

            val dialogBuilder = AlertDialog.Builder(context).setView(viewPopup)
            val dialog = dialogBuilder.create()
            dialog.show()

            saveButton.setOnClickListener {
                if (!TextUtils.isEmpty(choreName.text.toString().trim())
                    && !TextUtils.isEmpty(assignedBy.text.toString().trim())
                    && !TextUtils.isEmpty(assignedTo.text.toString().trim())) {

                    chore.choreName = choreName.text.toString()
                    chore.assignedTo = assignedTo.text.toString()
                    chore.assignedBy = assignedBy.text.toString()

                    dbHandler.updateChore(chore)
                    notifyItemChanged(adapterPosition, chore) // это чтобы после апдейта задачи она тут же менялась
                    dialog.dismiss()

                } else {
                    Toast.makeText(context, "Please enter a chore", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}