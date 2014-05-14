package com.cep.mp3player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends Activity {
    private MediaPlayer player = new MediaPlayer();
    private MediaManager media_manager;

    void playSong(String song_path) {
        player.reset();
        try {
            player.setDataSource(song_path);
            player.prepare();
            player.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                .add(R.id.container, new PlaceholderFragment()).commit();
        }

        media_manager = new MediaManager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
        case R.id.action_settings:
            return true;
        case R.id.action_exit:
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onShowSongsClicked(View view) {
        Intent intent = new Intent(this, SongListActivity.class);
        intent.putExtra("com.cep.mp3player.available", media_manager.songTitles());
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED)
            return;

        super.onActivityResult(requestCode, resultCode, data);

        Bundle b = data.getExtras();
        int current_song = b.getInt("com.cep.mp3player.selected");
        playSong(media_manager.getPath(current_song));
        showSongTitle(current_song);
    }

    void showSongTitle(int index) {
        TextView song_title_wg = (TextView) findViewById(R.id.songTitle);
        song_title_wg.setText(media_manager.getTitle(index));
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}

class MediaManager {
    String music_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getPath() + '/';
    File[] song_files;

    public MediaManager() {
        File music_dir = new File(music_path);
        song_files = music_dir.listFiles();
    }

    public String[] songTitles() {
        List<String> titles = new ArrayList<String>();

        for (File f: song_files) {
            titles.add(f.getName());
        }
        return titles.toArray(new String[0]);
    }

    public String getPath(int index) {
        return song_files[index].getPath();
    }

    public String getTitle(int index) {
        return song_files[index].getName();
    }
}
