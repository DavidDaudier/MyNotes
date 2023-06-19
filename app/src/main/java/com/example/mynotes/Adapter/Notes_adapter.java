package com.example.mynotes.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.Activity.UpdateNotesActivity;
import com.example.mynotes.MainActivity;
import com.example.mynotes.Model.Notes;
import com.example.mynotes.R;

import java.util.ArrayList;
import java.util.List;

public class Notes_adapter extends RecyclerView.Adapter<Notes_adapter.noteViewholder>{

    // Declaration des variables
    MainActivity mainActivity;
    List<Notes> notes;
    List<Notes> TousLesNotes;

    public Notes_adapter(MainActivity mainActivity, List<Notes> notes) {
        this.mainActivity = mainActivity;
        this.notes = notes;
        TousLesNotes = new ArrayList<>(notes);
    }

    // Fonction Recherche des notes
    public void rechercheNotes(List<Notes> nomFiltrer){
        this.notes = nomFiltrer;
        notifyDataSetChanged();
    }

    @Override
    public noteViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new noteViewholder(LayoutInflater.from(mainActivity).inflate(R.layout.item_notes, parent, false));
    }

    @Override
    public void onBindViewHolder(Notes_adapter.noteViewholder holder, int position) {
        Notes note = notes.get(position);
        holder.dateNotes.setText(note.dateNote);
        holder.titre.setText(note.titreNote);
        holder.notes.setText(note.notesTotale);
        switch (note.prioriteNote) {
            case "1":
                holder.notes_priorities.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                break;
            case "2":
                holder.notes_priorities.setBackgroundResource(R.drawable.ic_baseline_turned_in_24);
                break;
            case "3":
                holder.notes_priorities.setBackgroundResource(R.drawable.ic_baseline_mode_comment_24);
                break;
        }

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(mainActivity, UpdateNotesActivity.class);

            intent.putExtra("id", note.id);
            intent.putExtra("titre",note.titreNote);
            intent.putExtra("priorite", note.prioriteNote);
            intent.putExtra("note", note.notesTotale);

            mainActivity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class noteViewholder extends RecyclerView.ViewHolder{

        TextView dateNotes, titre, notes;
        View notes_priorities;

        public noteViewholder(View itemView) {
            super(itemView);

            dateNotes = itemView.findViewById(R.id.dates_notes);
            titre = itemView.findViewById(R.id.titres_notes);
            notes = itemView.findViewById(R.id.afficher_notes);
            notes_priorities = itemView.findViewById(R.id.notes_priorities);
        }
    }
}