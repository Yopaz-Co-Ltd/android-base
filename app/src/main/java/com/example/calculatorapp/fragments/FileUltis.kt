package com.example.calculatorapp.fragments

import android.content.Context
import com.example.calculatorapp.TaskModel
import java.io.*

object FileUltis {

    private const val FILENAME = "listtodo.dat"

    fun writeData(context: Context, list: MutableList<TaskModel>) {

        val fileOutputStream: FileOutputStream =
            context.openFileOutput(FILENAME, Context.MODE_PRIVATE)
        val objectOutputStream = ObjectOutputStream(fileOutputStream)
        objectOutputStream.writeObject(list)
        objectOutputStream.close()
    }

    fun readData(context: Context): MutableList<TaskModel> {

        var itemList: MutableList<TaskModel>
        try {
            val fileInputStream: FileInputStream = context.openFileInput(FILENAME)
            val objectInputStream = ObjectInputStream(fileInputStream)
            itemList = objectInputStream.readObject() as MutableList<TaskModel>
        } catch (e: FileNotFoundException) {
            itemList = mutableListOf()
        }
        return itemList
    }
}
