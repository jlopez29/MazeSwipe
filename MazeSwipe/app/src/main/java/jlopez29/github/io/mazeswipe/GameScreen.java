package jlopez29.github.io.mazeswipe;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_screen);

        mazeView = (TextView)findViewById(R.id.mazeView);

        Button generate = (Button)findViewById(R.id.generateMap);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MazeGenerator mg = new MazeGenerator(4,4);
                mg.display();
            }
        });

    }
}
