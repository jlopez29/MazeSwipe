package jlopez29.github.io.mazeswipe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GameScreen extends AppCompatActivity {

    public static TextView mazeView;
    SharedPreferences prefs;
    Integer mapSize = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_screen);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        mazeView = (TextView) findViewById(R.id.mazeView);

        Button generate = (Button) findViewById(R.id.generateMap);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String size = prefs.getString("mzSize","4");
                int mazeSize = Integer.valueOf(size);
                MazeGenerator mg = new MazeGenerator(mazeSize, mazeSize);
                mg.display();
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mazeSize:
                Intent i = new Intent(this,SettingsActivity.class);
                startActivity(i);
                return true;

        }
        return false;
    }
}
