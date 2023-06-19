package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.mynotes.Activity.AddNotesActivity;
import com.example.mynotes.Adapter.Notes_adapter;
import com.example.mynotes.Model.Notes;
import com.example.mynotes.Vue.Notes_model_vue;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Declaration des variables
    FloatingActionButton btnAddNote;
    Notes_model_vue notesModelVue;
    RecyclerView notesRecycler;
    Notes_adapter notes_adapter;
    TextView noFilter, descendent, elevee, basse;
    List<Notes> filtrerListMots;
    static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ***** Recuperation des ID *******
        btnAddNote = findViewById(R.id.btnAddNote);
        notesRecycler = findViewById(R.id.notesRecycler);

            //Fitrage par date
        noFilter = findViewById(R.id.no_filter);
        descendent = findViewById(R.id.descendent);

            //Filtrage par priorite des notes
        elevee = findViewById(R.id.elevee);
        basse = findViewById(R.id.basse);


        //Fitrage par date et priorite des notes
        noFilter.setBackgroundResource(R.drawable.strok_filter_selected);

        noFilter.setOnClickListener(view -> {
            loadData(0);
            descendent.setBackgroundResource(R.drawable.bg_filter);
            elevee.setBackgroundResource(R.drawable.bg_filter);
            basse.setBackgroundResource(R.drawable.bg_filter);
            noFilter.setBackgroundResource(R.drawable.strok_filter_selected);
        });
        descendent.setOnClickListener(view -> {
            loadData(1);
            noFilter.setBackgroundResource(R.drawable.bg_filter);
            elevee.setBackgroundResource(R.drawable.bg_filter);
            basse.setBackgroundResource(R.drawable.bg_filter);
            descendent.setBackgroundResource(R.drawable.strok_filter_selected);
        });
        elevee.setOnClickListener(view -> {
            loadData(2);
            noFilter.setBackgroundResource(R.drawable.bg_filter);
            descendent.setBackgroundResource(R.drawable.bg_filter);
            basse.setBackgroundResource(R.drawable.bg_filter);
            elevee.setBackgroundResource(R.drawable.strok_filter_selected);
        });
        basse.setOnClickListener(view -> {
            loadData(3);
            noFilter.setBackgroundResource(R.drawable.bg_filter);
            descendent.setBackgroundResource(R.drawable.bg_filter);
            elevee.setBackgroundResource(R.drawable.bg_filter);
            basse.setBackgroundResource(R.drawable.strok_filter_selected);
        });

        notesModelVue = ViewModelProviders.of(this).get(Notes_model_vue.class);

        btnAddNote.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AddNotesActivity.class));
        });

        notesModelVue.getAllNotes.observe(this, new Observer<List<Notes>>(){
            @Override
            public void onChanged(List<Notes> notes) {
                setNotes_adapter(notes);
                filtrerListMots = notes;
            }
        });
    }

    // Action sur les boutons top
    private void loadData(int i){
        //Filtrage Par defaut(Date de modification)
        if (i==0){
            notesModelVue.getAllNotes.observe(this, new Observer<List<Notes>>(){
                @Override
                public void onChanged(List<Notes> notes) {
                    setNotes_adapter(notes);
                    filtrerListMots = notes;
                }
            });
        }
        //Filtrage Recente(Date de modification)
        else if (i==1){
            notesModelVue.descendent.observe(this, new Observer<List<Notes>>(){
                @Override
                public void onChanged(List<Notes> notes) {
                    setNotes_adapter(notes);
                    filtrerListMots = notes;
                }
            });
        }
        //Filtrage elevee(Par Priorite des notes(favoris=1, important=2 et commentaire=3))
        else if (i==2){
            notesModelVue.elevee.observe(this, new Observer<List<Notes>>(){
                @Override
                public void onChanged(List<Notes> notes) {
                    setNotes_adapter(notes);
                    filtrerListMots = notes;
                }
            });
        }
        //Filtrage basse(Par Priorite des notes(favoris=1, important=2 et commentaire=3))
        else if (i==3){
            notesModelVue.basse.observe(this, new Observer<List<Notes>>(){
                @Override
                public void onChanged(List<Notes> notes) {
                    setNotes_adapter(notes);
                    filtrerListMots = notes;
                }
            });
        }
    }

    //Affichage des notes sous forme de grille
    public void setNotes_adapter(List<Notes> notes){
        notesRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        notes_adapter = new Notes_adapter(MainActivity.this, notes);
        notesRecycler.setAdapter(notes_adapter);
    }

    // Creation bouton 'Rechercher notes'
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView)menuItem.getActionView();
        searchView.setQueryHint("Rechercher votre note...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String textRechercher) {
                filtrerNotes(textRechercher);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    // Filtrages des notes recherchees
    private void filtrerNotes(String textRechercher){
        ArrayList<Notes> filtrerMots = new ArrayList<>();
        for (Notes notes:this.filtrerListMots){
            if (    notes.titreNote.contains((textRechercher.toLowerCase(Locale.ROOT)))||
                    notes.notesTotale.contains(textRechercher.toLowerCase(Locale.ROOT))||
                    notes.titreNote.contains((textRechercher.toUpperCase(Locale.ROOT)))||
                    notes.notesTotale.contains(textRechercher.toUpperCase(Locale.ROOT))
            ){
                filtrerMots.add(notes);
            }
        }
        this.notes_adapter.rechercheNotes(filtrerMots);
    }

}