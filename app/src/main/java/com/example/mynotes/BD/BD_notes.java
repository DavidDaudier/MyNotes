package com.example.mynotes.BD;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mynotes.DAO.Notes_Dao;
import com.example.mynotes.Model.Notes;

@Database(entities = {Notes.class}, version = 1)
public abstract class BD_notes extends RoomDatabase {

    public abstract Notes_Dao notes_dao();

    public static BD_notes INSTANCE;

    public static BD_notes getDatabaseInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    BD_notes.class,
                    "BD_notes").allowMainThreadQueries().allowMainThreadQueries().build();
        }
        return INSTANCE;
    }


}

