package ba.dami.littlehallo;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView icMic;
    TextView tvMalo;
    RelativeLayout rcBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        icMic = (ImageView) findViewById(R.id.icMic);
        tvMalo = (TextView) findViewById(R.id.tvMalo);
        rcBackground = (RelativeLayout) findViewById(R.id.rcBackground);
        icMic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == icMic){
            rcBackground.setBackgroundColor(getResources().getColor(android.R.color.white));
            tvMalo.setVisibility(View.GONE);

            try {
                new Thread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "sr-Latn");
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Magic word");
            try {
                startActivityForResult(intent, 0);
            } catch (ActivityNotFoundException e) {
                Log.e("speak_error", e.toString());
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String search = result.get(0).toString().replace("[", "").replace("]", "");

            Log.d(MainActivity.class.getSimpleName(), search + " ");
            if (search.contains("little") || search.contains("hello") || search.contains("malo") || search.contains("hallo") || search.contains("8") || search.contains("osam")|| search.contains("eight")) {
                tvMalo.setVisibility(View.VISIBLE);
                rcBackground.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                tvMalo.setTextColor(getResources().getColor(android.R.color.white));
            }

            super.onActivityResult(requestCode, resultCode, data);

        }
    }
}
