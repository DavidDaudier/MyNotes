package com.example.mynotes.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mynotes.Model.Notes;

import java.util.List;

@androidx.room.Dao
public interface Notes_Dao {


    @Query("SELECT *FROM BD_notes")
    LiveData<List<Notes>> getAllNotes();

    @Query("SELECT *FROM BD_notes ORDER BY date_note DESC")
    LiveData<List<Notes>> descendent();

    @Query("SELECT *FROM BD_notes ORDER BY priorite_note ASC")
    LiveData<List<Notes>> elevee();

    @Query("SELECT *FROM BD_notes ORDER BY priorite_note DESC")
    LiveData<List<Notes>> basse();

    @Insert
    void ajouterNote(Notes... notes);

    @Query("DELETE FROM BD_notes WHERE id=:id")
    void supprimmerNote(int id);

    @Update
    void modifierNote(Notes notes);

}
