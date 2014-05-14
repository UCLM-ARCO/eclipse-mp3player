package com.cep.mp3player;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class SongListActivity extends ListActivity {
        
    @Override 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                
        String[] song_names = getIntent().getExtras().getStringArray("com.cep.mp3player.available");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                                                                android.R.layout.simple_list_item_1, song_names);
        setListAdapter(adapter);
    }
        
    @Override
    public void onListItemClick(ListView parent, View v, int position, long id) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("com.cep.mp3player.selected", position);
        
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
