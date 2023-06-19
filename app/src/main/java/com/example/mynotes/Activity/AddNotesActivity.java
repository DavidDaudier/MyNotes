package com.example.mynotes.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mynotes.Model.Notes;
import com.example.mynotes.R;
import com.example.mynotes.Vue.Notes_model_vue;
import com.example.mynotes.databinding.ActivityAddNotesBinding;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class AddNotesActivity extends AppCompatActivity {

    // Declaration des variables
    ActivityAddNotesBinding binding;
    Notes_model_vue notesModelVue;
    String titre, note, priority_checked = "1";

    // Fonction principale de la Creation des notes
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notesModelVue = ViewModelProviders.of(this).get(Notes_model_vue.class);

        //Selection d'un priorite, deselection des autres
        binding.favoryPriority.setOnClickListener(view -> {
            priority_checked ="1";
            binding.favoryPriority.setBackgroundResource(R.drawable.bg_priority_cheked);
            binding.turnedPriority.setBackgroundResource(0);
            binding.commentPriority.setBackgroundResource(0);
        });

        binding.turnedPriority.setOnClickListener(view -> {
            priority_checked ="2";
            binding.turnedPriority.setBackgroundResource(R.drawable.bg_priority_cheked);
            binding.favoryPriority.setBackgroundResource(0);
            binding.commentPriority.setBackgroundResource(0);
        });

        binding.commentPriority.setOnClickListener(view -> {
            priority_checked ="3";
            binding.commentPriority.setBackgroundResource(R.drawable.bg_priority_cheked);
            binding.favoryPriority.setBackgroundResource(0);
            binding.turnedPriority.setBackgroundResource(0);
        });

        binding.btnSaveNote.setOnClickListener(view -> {
            titre = binding.titreNote.getText().toString();
            note = binding.noteTotale.getText().toString();

            CreateNotes(titre, note);
        });

    }

    // Fonction Ajouter notes (CreateNotes)
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void CreateNotes(String titre, String note) {

        Calendar calendar;
        SimpleDateFormat simpleDateFormat;

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yy hh:mm:ss aa");

        Notes notes1 = new Notes();
        notes1.titreNote = titre;
        notes1.notesTotale = note;
        notes1.dateNote = simpleDateFormat.format(calendar.getTime());
        notes1.prioriteNote = priority_checked;
        if (notes1.notesTotale.isEmpty()){
            Toast.makeText(this, "Note non enregistrer", Toast.LENGTH_SHORT).show();
        }
        else{
            notesModelVue.ajouterNote(notes1);
            Toast.makeText(this, "Note ajouter avec succès", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


}