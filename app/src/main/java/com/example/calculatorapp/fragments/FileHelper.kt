package com.example.calculatorapp.fragments

import android.content.Context
import com.example.calculatorapp.ItemData
import com.example.calculatorapp.notificationID
import java.io.*

class FileHelper {
    val FILENAME = "listtodo.dat"
    fun writeData(list: MutableList<ItemData>, context: Context){
            val fos: FileOutputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE)
            val oas = ObjectOutputStream(fos)
            oas.writeObject(list)
            oas.close()
    }

    fun readData(context: Context): MutableList<ItemData> {
        var itemList: MutableList<ItemData>
        try{
            val fis: FileInputStream = context.openFileInput(FILENAME)
            val ois = ObjectInputStream(fis)
            itemList = ois.readObject() as MutableList<ItemData>
        }catch(e: FileNotFoundException){
            itemList = mutableListOf()
        }
        return itemList
    }

}
