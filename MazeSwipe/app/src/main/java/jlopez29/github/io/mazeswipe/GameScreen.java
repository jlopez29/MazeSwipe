package jlopez29.github.io.mazeswipe;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GameScreen extends AppCompatActivity {

    public static TextView mazeView;
    SharedPreferences prefs;
    public static Integer mazeSize = 4;

    ArrayList<Integer> imageList;
    Integer[][] drawables;

    GridView gv;

    int[][] maze;
    boolean finished = false;
    public static Integer[] visitedDrawables;
    public static int x = 0;
    public static int y = 0;

    int tx = 0;
    int ty = 0;

    private float downX, downY, upX, upY;
    static final int MIN_DISTANCE = 150;

    RelativeLayout N;
    RelativeLayout S;
    RelativeLayout E;
    RelativeLayout W;
    RelativeLayout NS;
    RelativeLayout NE;
    RelativeLayout NW;
    RelativeLayout SE;
    RelativeLayout SW;
    RelativeLayout WE;
    RelativeLayout NSE;
    RelativeLayout NSW;
    RelativeLayout NWE;
    RelativeLayout SWE;
    RelativeLayout NSEW;

    MazeGenerator mg;

    String endPoint = "";

    Button generate;
    FloatingActionButton fab;
    Context mCtx;

    ImageAdapter ia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_screen);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mCtx = this;
        mazeView = findViewById(R.id.mazeView);
        String size = prefs.getString("mzSize", "4");
        mazeSize = Integer.valueOf(size);

        initLayouts();

        generate = findViewById(R.id.generateMap);

        gv = findViewById(R.id.gridView);

        fab = findViewById(R.id.help);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gv.getVisibility() == View.VISIBLE) {
                    gv.setVisibility(View.GONE);
                    fab.show();
                    fab.setClickable(true);
                }
                else
                {
                    gv.setVisibility(View.VISIBLE);
                    fab.hide();
                    fab.setClickable(false);
                }
            }
        });

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(gv.getVisibility() == View.VISIBLE) {
                    gv.setVisibility(View.GONE);
                    fab.show();
                    fab.setClickable(true);
                }
                else
                {
                    gv.setVisibility(View.VISIBLE);
                    fab.hide();
                    fab.setClickable(false);
                }
            }
        });

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String size = prefs.getString("mzSize", "4");
                mazeSize = Integer.valueOf(size);

                initVisited();

                initGrid();

                visitedDrawables[0] = R.drawable.circle_curr;

                ia = new ImageAdapter(mCtx, visitedDrawables);
                gv.setNumColumns(mazeSize);
                gv.setAdapter(ia);

                mg = new MazeGenerator(mazeSize, mazeSize);
                maze = mg.getMaze();

                drawables[0][0] = R.drawable.circle_curr;

                endPoint = mg.getEnd();
                finished = false;
                generate.setVisibility(View.GONE);
                generate.setClickable(false);
                mazeView.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);

                mg.display();
                gameLoop();

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void initGrid() {
        Log.i("Init", "Grid");
        drawables = new Integer[mazeSize][mazeSize];
        for (int i = 0; i < mazeSize; i++) {
            for (int j = 0; j < mazeSize; j++) {
                drawables[i][j] = R.drawable.circle;
            }
        }
    }

    public void initVisited() {
        visitedDrawables = new Integer[mazeSize*mazeSize];
        for (int i = 0; i < mazeSize * mazeSize; i++) {
            visitedDrawables[i] = R.drawable.circle;
        }
    }

    public int getGridPos(int mx,int my)
    {
        int spot = 0;

        spot = my*mazeSize + mx;

        return spot;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        String dir = "";

        if (extras != null)
        {
            if (extras.containsKey("Btext"))
                dir = extras.getString("Btext");

            if (dir != null)
            {

                if (dir.equals("E")) {

                    Log.e("Left", "to Right");
                    //left to right
                    tx = x + 1;
                    ty = y;
                    if (validMove(1)) {
                        Log.e("Valid", "Move");
                        int pos = getGridPos(x,y);
                        Log.e("Pos", "is " + pos);
                        visitedDrawables[pos] = R.drawable.circle_dark;
                        x = tx;
                        y = ty;
                        pos = getGridPos(x,y);
                        visitedDrawables[pos] = R.drawable.circle_curr;

                        ia.notifyDataSetChanged();
                        gameLoop();
                    }
                } else if (dir.equals("D")) {
                    // left
                    Log.e("Right", "to Left");
                    tx = x - 1;
                    ty = y;
                    if (validMove(2)) {
                        Log.e("Valid", "Move");
                        int pos = getGridPos(x,y);
                        Log.e("Pos", "is " + pos);
                        visitedDrawables[pos] = R.drawable.circle_dark;
                        x = tx;
                        y = ty;
                        pos = getGridPos(x,y);
                        visitedDrawables[pos] = R.drawable.circle_curr;

                        ia.notifyDataSetChanged();
                        gameLoop();
                    }
                } else if (dir.equals("B")) {
                    // top to bottom
                    Log.e("Top", "to Bottom");
                    tx = x;
                    ty = y + 1;
                    if (validMove(3)) {
                        Log.e("Valid", "Move");
                        int pos = getGridPos(x,y);
                        Log.e("Pos", "is " + pos);
                        visitedDrawables[pos] = R.drawable.circle_dark;
                        x = tx;
                        y = ty;
                        pos = getGridPos(x,y);
                        visitedDrawables[pos] = R.drawable.circle_curr;

                        ia.notifyDataSetChanged();
                        gameLoop();
                    }
                } else if (dir.equals("A")) {
                    // top
                    Log.e("Bottom", "to Top");
                    tx = x;
                    ty = y - 1;
                    if (validMove(4)) {
                        Log.e("Valid", "Move");
                        int pos = getGridPos(x,y);
                        Log.e("Pos", "is " + pos);
                        visitedDrawables[pos] = R.drawable.circle_dark;
                        x = tx;
                        y = ty;
                        pos = getGridPos(x,y);
                        visitedDrawables[pos] = R.drawable.circle_curr;

                        ia.notifyDataSetChanged();
                        gameLoop();
                    }
                }
            }
        }
    }


    public void gameLoop()
    {
        String endGame = "";

        endGame += x + "" + y;

        if(endGame.equals(endPoint))
        {
            finished = true;
            Toast.makeText(this,"You reached the end!!!",Toast.LENGTH_LONG).show();
            generate.setVisibility(View.VISIBLE);
            generate.setClickable(true);
            mazeView.setVisibility(View.VISIBLE);
        }

        if(!finished)
            updateDisplay();
        else
        {
            x = 0;
            y = 0;
            finished = false;
            gv.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
            hideAll();
        }
    }

    public boolean validMove(int dir) {
        if (tx >= 0 && ty >= 0 && tx < mazeSize && ty < mazeSize) {
            switch (dir) {
                case 1:
                    //right
                    Log.e("Check","Right");
                    if(maze[x][y] == 4 || maze[x][y] == 5 || maze[x][y] == 6 || maze[x][y] == 12 || maze[x][y] == 4 || maze[x][y] == 7 || maze[x][y] == 13 || maze[x][y] == 14 || maze[x][y] == 15)
                        return true;
                    else
                        return false;
                case 2:
                    //left
                    Log.e("Check","Left");
                    if(maze[x][y] == 8 || maze[x][y] == 9 || maze[x][y] == 10 || maze[x][y] == 11 || maze[x][y] == 12 || maze[x][y] == 13 || maze[x][y] == 14 || maze[x][y] == 15)
                        return true;
                    else
                        return false;
                case 3:
                    //down
                    Log.e("Check","Down");
                    if(maze[x][y] == 2 || maze[x][y] == 3 || maze[x][y] == 6 || maze[x][y] == 10 || maze[x][y] == 7 || maze[x][y] == 11 || maze[x][y] == 14 || maze[x][y] == 15)
                        return true;
                    else
                        return false;
                case 4:
                    //up
                    Log.e("Check","Up");
                    if(maze[x][y] == 1 || maze[x][y] == 3 || maze[x][y] == 5 || maze[x][y] == 9 || maze[x][y] == 7 || maze[x][y] == 11 || maze[x][y] == 13 || maze[x][y] == 15)
                        return true;
                    else
                        return false;
                default:
                    return false;
            }
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        switch(event.getAction()) { // Check vertical and horizontal touches
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                downY = event.getY();
                return true;
            }
            case MotionEvent.ACTION_UP: {
                upX = event.getX();
                upY = event.getY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                //HORIZONTAL SCROLL
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    if (Math.abs(deltaX) >MIN_DISTANCE) {
                        // left or right
                        if (deltaX < 0) {

                            Log.e("Left","to Right");
                            //left to right
                            tx = x+1;
                            ty = y;
                            if(validMove(1))
                            {
                                Log.e("Valid", "Move");
                                int pos = getGridPos(x,y);
                                Log.e("Pos", "is " + pos);
                                visitedDrawables[pos] = R.drawable.circle_dark;
                                x = tx;
                                y = ty;
                                pos = getGridPos(x,y);
                                visitedDrawables[pos] = R.drawable.circle_curr;

                                ia.notifyDataSetChanged();
                                gameLoop();
                                return true;
                            }
                            else
                                return false;
                        }
                        if (deltaX > 0) {
                            // right to left
                            Log.e("Right","to Left");
                            tx = x-1;
                            ty = y;
                            if(validMove(2))
                            {
                                Log.e("Valid", "Move");
                                int pos = getGridPos(x,y);
                                Log.e("Pos", "is " + pos);
                                visitedDrawables[pos] = R.drawable.circle_dark;
                                x = tx;
                                y = ty;
                                pos = getGridPos(x,y);
                                visitedDrawables[pos] = R.drawable.circle_curr;

                                ia.notifyDataSetChanged();
                                gameLoop();
                                return true;
                            }
                            else
                                return false;
                        }
                    } else {
                        //not long enough swipe...
                        Log.e("Swipe","Too short");
                        return false;
                    }
                }
                //VERTICAL SCROLL
                else {
                    if (Math.abs(deltaY) > MIN_DISTANCE) {
                        // top or down
                        if (deltaY < 0) {
                            // top to bottom
                            Log.e("Top","to Bottom");
                            tx = x;
                            ty = y+1;
                            if(validMove(3))
                            {
                                Log.e("Valid", "Move");
                                int pos = getGridPos(x,y);
                                Log.e("Pos", "is " + pos);
                                visitedDrawables[pos] = R.drawable.circle_dark;
                                x = tx;
                                y = ty;
                                pos = getGridPos(x,y);
                                visitedDrawables[pos] = R.drawable.circle_curr;

                                ia.notifyDataSetChanged();
                                gameLoop();
                                return true;
                            }
                            else
                                return false;
                        }
                        if (deltaY > 0) {
                            // bottom to top
                            Log.e("Bottom","to Top");
                            tx = x;
                            ty = y-1;
                            if(validMove(4))
                            {
                                Log.e("Valid", "Move");
                                int pos = getGridPos(x,y);
                                Log.e("Pos", "is " + pos);
                                visitedDrawables[pos] = R.drawable.circle_dark;
                                x = tx;
                                y = ty;
                                pos = getGridPos(x,y);
                                visitedDrawables[pos] = R.drawable.circle_curr;

                                ia.notifyDataSetChanged();
                                gameLoop();
                                return true;
                            }
                            else
                                return false;
                        }
                    } else {
                        //not long enough swipe...
                        Log.e("Swipe","Too short");
                        return false;
                    }
                }
                return false;
            }
        }
        return false;
    }

    public void updateDisplay()
    {
        hideAll();
        switch(maze[x][y])
        {
            case 1:
                showN();
                break;
            case 2:
                showS();
                break;
            case 3:
                showNS();
                break;
            case 4:
                showE();
                break;
            case 5:
                showNE();
                break;
            case 6:
                showSE();
                break;
            case 7:
                showNSE();
                break;
            case 8:
                showW();
                break;
            case 9:
                showNW();
                break;
            case 10:
                showSW();
                break;
            case 11:
                showNSW();
                break;
            case 12:
                showWE();
                break;
            case 13:
                showNWE();
                break;
            case 14:
                showSWE();
                break;
            case 15:
                showNSEW();
                break;
            default:
                hideAll();
                break;

        }
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


    public void initLayouts()
    {
        N = findViewById(R.id.N);
        S = findViewById(R.id.S);
        E = findViewById(R.id.E);
        W = findViewById(R.id.W);
        NS = findViewById(R.id.NS);
        NE = findViewById(R.id.NE);
        NW = findViewById(R.id.NW);
        SE = findViewById(R.id.SE);
        SW = findViewById(R.id.SW);
        WE = findViewById(R.id.WE);
        NSE = findViewById(R.id.NSE);
        NSW = findViewById(R.id.NSW);
        NWE = findViewById(R.id.NWE);
        SWE = findViewById(R.id.SWE);
        NSEW = findViewById(R.id.NSEW);
    }

    public void hideAll()
    {
        N.setVisibility(View.GONE);
        S.setVisibility(View.GONE);
        E.setVisibility(View.GONE);
        W.setVisibility(View.GONE);
        NS.setVisibility(View.GONE);
        NE.setVisibility(View.GONE);
        NW.setVisibility(View.GONE);
        SE.setVisibility(View.GONE);
        SW.setVisibility(View.GONE);
        WE.setVisibility(View.GONE);
        NSE.setVisibility(View.GONE);
        NSW.setVisibility(View.GONE);
        NWE.setVisibility(View.GONE);
        SWE.setVisibility(View.GONE);
        NSEW.setVisibility(View.GONE);
    }

    public void showN()
    {
        N.setVisibility(View.VISIBLE);
    }

    public void showS()
    {
        S.setVisibility(View.VISIBLE);
    }

    public void showE()
    {
        E.setVisibility(View.VISIBLE);
    }

    public void showW()
    {
        W.setVisibility(View.VISIBLE);
    }

    public void showNS()
    {
        NS.setVisibility(View.VISIBLE);
    }

    public void showNE()
    {
        NE.setVisibility(View.VISIBLE);
    }

    public void showNW()
    {
        NW.setVisibility(View.VISIBLE);
    }

    public void showSE()
    {
        SE.setVisibility(View.VISIBLE);
    }

    public void showSW()
    {
        SW.setVisibility(View.VISIBLE);
    }

    public void showWE()
    {
        WE.setVisibility(View.VISIBLE);
    }

    public void showNSE()
    {
        NSE.setVisibility(View.VISIBLE);
    }

    public void showNSW()
    {
        NSW.setVisibility(View.VISIBLE);
    }

    public void showNWE()
    {
        NWE.setVisibility(View.VISIBLE);
    }

    public void showSWE()
    {
        SWE.setVisibility(View.VISIBLE);
    }

    public void showNSEW()
    {
        NSEW.setVisibility(View.VISIBLE);
    }

}
