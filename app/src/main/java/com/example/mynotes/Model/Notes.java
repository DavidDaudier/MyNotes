package com.example.mynotes.Model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "BD_notes")
public class Notes {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "titre_note")
    public String titreNote;

    @ColumnInfo(name = "date_note")
    public String dateNote;

    @ColumnInfo(name = "priorite_note")
    public String prioriteNote;

    @ColumnInfo(name = "notes_totale")
    public String notesTotale;

}
