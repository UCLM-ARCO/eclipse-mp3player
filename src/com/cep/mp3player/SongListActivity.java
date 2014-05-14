package com.cep.mp3player;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class SongListActivity extends ListActivity {
    private SongListAdapter adapter;
        
    @Override 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);
                
        String[] song_names = getIntent().getExtras().getStringArray("com.cep.mp3player.available");
        adapter = new SongListAdapter(this, buildSongList(song_names));
        setListAdapter(adapter);
    }
        
    @Override
    public void onListItemClick(ListView parent, View v, int position, long id) {
        adapter.toggleChecked(position);
    }
        
    public void onAddClicked(View v) {
        Intent resultIntent = new Intent();
                
        resultIntent.putIntegerArrayListExtra("com.cep.mp3player.selected", adapter.getSelectedSongs());
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    public void onCancelClicked(View v){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
        
    private ArrayList<SongRow> buildSongList(String[] song_names) {
        ArrayList<SongRow> songs = new ArrayList<SongRow>();

        for (String s: song_names)
            songs.add(new SongRow(s));
                
        return songs;
    }
}

class SongRow {
    private String title;
    private Boolean selected;

    SongRow(String title) {
        this.title = title;
        this.selected = false;
    }

    String title() {
        return title;
    }

    Boolean getSelected() {
        return selected;
    }

    public void toggle() {
        selected = ! selected;
    }
}

class SongListAdapter extends ArrayAdapter<SongRow> {

    private final Context context;
    private final ArrayList<SongRow> songRowList;

    SongListAdapter(Context context, ArrayList<SongRow> songRowList) {
        super(context, R.layout.row_list, songRowList);

        this.context = context;
        this.songRowList = songRowList;
    }

    @Override
        public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater)
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. get rowView from inflater
        View rowView = inflater.inflate(R.layout.row_list, parent, false);

        // 3. Get the different elements views from the rowView
        CheckedTextView songView = (CheckedTextView) rowView.findViewById(R.id.song);

        // 4. Set the contents for each view
        songView.setText(songRowList.get(position).title());
        if (songRowList.get(position).getSelected())
            songView.setChecked(true);

        return rowView;
    }
        
    void toggleChecked(int position) {
        songRowList.get(position).toggle();
        notifyDataSetChanged();
    }

    ArrayList<Integer> getSelectedSongs() {
        ArrayList<Integer> selected = new ArrayList<Integer>();

        for (int i = 0; i < songRowList.size(); i++)
            if (songRowList.get(i).getSelected())
                selected.add(i);
                
        return selected;
    }
}
