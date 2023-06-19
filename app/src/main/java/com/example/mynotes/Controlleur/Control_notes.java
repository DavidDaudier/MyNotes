package com.example.mynotes.Controlleur;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mynotes.BD.BD_notes;
import com.example.mynotes.DAO.Notes_Dao;
import com.example.mynotes.Model.Notes;

import java.util.List;

public class Control_notes {
    public Notes_Dao notes_dao;

    // Declaration des variables Filtrage des notes(Tous les notes, recente, priorite elevee, priorite basse
    public LiveData<List<Notes>> getAllNotes;
    public LiveData<List<Notes>> descendent;
    public LiveData<List<Notes>> elevee;
    public LiveData<List<Notes>> basse;


    public Control_notes(Application application){

        // Instantiation des variables
        BD_notes database  = BD_notes.getDatabaseInstance(application);
        notes_dao = database.notes_dao();

        getAllNotes = notes_dao.getAllNotes();
        descendent = notes_dao.descendent();
        elevee = notes_dao.elevee();
        basse = notes_dao.basse();
    }

    // Fonction permet d'ajouter une note
    public void ajouterNote(Notes notes){
        notes_dao.ajouterNote(notes);
    }

    // Fonction permet de supprimmer une note
    public void supprimmerNote(int id){
        notes_dao.supprimmerNote(id);
    }

    // Fonction permet de modifier une note
    public void modifierNote(Notes notes){
        notes_dao.modifierNote(notes);
    }
}
