@file:Suppress("UNCHECKED_CAST")

package com.example.calculatorapp.fragments

import android.content.Context
import com.example.calculatorapp.TaskModels
import java.io.*

object FileUltis {

    private const val FILENAME = "listtodo.dat"
    fun writeData(context: Context, list: MutableList<TaskModels>) {
        val fileOutputStream: FileOutputStream =
            context.openFileOutput(FILENAME, Context.MODE_PRIVATE)
        val objectOutputStream = ObjectOutputStream(fileOutputStream)
        objectOutputStream.writeObject(list)
        objectOutputStream.close()
    }
    fun readData(context: Context): List<TaskModels> {
        var itemList: List<TaskModels>
        try {
            val fileInputStream: FileInputStream = context.openFileInput(FILENAME)
            val objectInputStream = ObjectInputStream(fileInputStream)
            itemList = objectInputStream.readObject() as List<TaskModels>
        } catch (e: FileNotFoundException) {
            itemList = emptyList()
        }
        return itemList
    }
}
