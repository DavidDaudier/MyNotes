package com.example.mynotes.Vue;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mynotes.Controlleur.Control_notes;
import com.example.mynotes.Model.Notes;

import java.util.List;

public class Notes_model_vue extends AndroidViewModel {

    // Declaration des variables
    public Control_notes controleur;
    public LiveData<List<Notes>> getAllNotes;
    public LiveData<List<Notes>> descendent;
    public LiveData<List<Notes>> elevee;
    public LiveData<List<Notes>> basse;


    public Notes_model_vue(Application application){
        super(application);

        // Instantiation des variables
        controleur = new Control_notes(application);
        getAllNotes = controleur.getAllNotes;
        descendent = controleur.descendent;
        elevee = controleur.elevee;
        basse = controleur.basse;
    }

    public void ajouterNote(Notes notes){
        controleur.ajouterNote(notes);
    }

    public void supprimmerNote(int id){
        controleur.supprimmerNote(id);
    }

    public void modifierNote(Notes notes) {
        controleur.modifierNote(notes);
    }
}
