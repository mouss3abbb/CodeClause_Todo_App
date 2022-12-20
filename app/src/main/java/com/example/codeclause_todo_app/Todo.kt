package com.example.codeclause_todo_app

import android.icu.text.CaseMap.Title
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Todo", primaryKeys = ["title","body"])
data class Todo(
    val title: String,
    val body: String
){
}
