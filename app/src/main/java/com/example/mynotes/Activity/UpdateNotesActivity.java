package com.example.mynotes.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mynotes.Model.Notes;
import com.example.mynotes.R;
import com.example.mynotes.Vue.Notes_model_vue;
import com.example.mynotes.databinding.ActivityAddNotesBinding;
import com.example.mynotes.databinding.ActivityUpdateNotesBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Calendar;

public class UpdateNotesActivity extends AppCompatActivity {

    // Declaration des variables
    ActivityUpdateNotesBinding binding;
    Notes_model_vue notesModelVue;
    int UpdateId;
    String  UpdateTitre, UpdatePriority_checked, UpdateNote, priority_checked = "1";

    // Fonction principale
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUpdateNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Instantiation des variables
        UpdateId = getIntent().getIntExtra("id", 0);
        UpdateTitre = getIntent().getStringExtra("titre");
        UpdatePriority_checked = getIntent().getStringExtra("priorite");
        UpdateNote = getIntent().getStringExtra("note");

        binding.titreNoteUpdate.setText(UpdateTitre);
        binding.noteTotaleUpdate.setText(UpdateNote);

        // Condition de selection d'un priorite pour que le background change
        if(UpdatePriority_checked.equals("1")){
            binding.favoryPriority.setBackgroundResource(R.drawable.bg_priority_cheked);
        }else if (UpdatePriority_checked.equals("2")){
            binding.turnedPriority.setBackgroundResource(R.drawable.bg_priority_cheked);
        }else if (UpdatePriority_checked.equals("3")){
            binding.commentPriority.setBackgroundResource(R.drawable.bg_priority_cheked);
        }

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

        binding.btnUpdateNote.setOnClickListener(view -> {
            String titre = binding.titreNoteUpdate.getText().toString();
            String note = binding.noteTotaleUpdate.getText().toString();

            UpdateNotes(titre, note);
        });
    }

    // Fonction pour modifier un note
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void UpdateNotes(String titre, String note) {

        // Declaration des variables locales
        Calendar calendar;
        SimpleDateFormat simpleDateFormat;

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yy hh:mm:ss");

        Notes updateNotes = new Notes();
        updateNotes.id = UpdateId;
        updateNotes.titreNote = titre;
        updateNotes.notesTotale = note;
        updateNotes.dateNote = simpleDateFormat.format(calendar.getTime());
        updateNotes.prioriteNote = priority_checked;

        if (updateNotes.notesTotale.isEmpty()){
            Toast.makeText(this, "Note non enregistrer", Toast.LENGTH_SHORT).show();
        }
        else{
            notesModelVue.modifierNote(updateNotes);
            Toast.makeText(this, "Note modifier avec succès", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    // Icone de suppression
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_icone_supprimer, menu);
        return true;
    }

    // Fonction choix option menu supprimer
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // Declaration des variables de suppression
        TextView yes, no;

        // Condition pour supprimer un note
        if (item.getItemId()== R.id.icone_delete){
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(UpdateNotesActivity.this);
            View view = LayoutInflater.from(UpdateNotesActivity.this).
                        inflate(R.layout.delete_note, (LinearLayout)findViewById(R.id.deleteNote));

            bottomSheetDialog.setContentView(view);
             yes = view.findViewById(R.id.delete_yes);
             no = view.findViewById(R.id.delte_no);

             yes.setOnClickListener(view1 -> {
                notesModelVue.supprimmerNote(UpdateId);
                 Toast.makeText(this, "Note supprimer avec succès", Toast.LENGTH_SHORT).show();
                finish();
             });

             no.setOnClickListener(view1 -> {
                 bottomSheetDialog.dismiss();
             });

             bottomSheetDialog.show();
        }
        return true;
    }
}