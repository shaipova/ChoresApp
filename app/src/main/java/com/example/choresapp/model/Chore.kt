package com.example.choresapp.model

import java.text.DateFormat
import java.util.*

class Chore() {
    var choreName: String? = null
    var assignedBy: String? = null
    var assignedTo: String? = null
    var id: Int? = null

    constructor(choreName: String,
                assignedBy: String,
                assignedTo: String,
                id: Int): this() {

        this.choreName = choreName
        this.assignedBy = assignedBy
        this.assignedTo = assignedTo
        this.id = id
    }
}