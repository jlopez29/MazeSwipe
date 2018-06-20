package jlopez29.github.io.mazeswipe;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyBroadcastReceiver extends BroadcastReceiver {

    String buttonPress = "jlopez29.github.io.mazeswipe.buttonpress";

    public void onReceive(Context context, final Intent intent) {
        String action = intent.getAction();
        String btext = intent.getStringExtra("Btext");

        Log.e("On", "Receive..");

        if(action.equals(buttonPress))
        {
            Log.e("Button","is: " + btext);
            String dir = String.valueOf(btext.charAt(0));
            Intent i = new Intent(context,GameScreen.class);
            i.putExtra("Btext",dir);
            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(i);
        }
    }
}
