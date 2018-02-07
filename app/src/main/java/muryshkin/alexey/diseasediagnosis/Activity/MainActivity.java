package muryshkin.alexey.diseasediagnosis.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import io.realm.Realm;
import muryshkin.alexey.diseasediagnosis.Data.Authentication;
import muryshkin.alexey.diseasediagnosis.R;

public class MainActivity extends AppCompatActivity {

    private ImageButton profileImageButton;
    private Button testButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.customized_main_action_bar);

        profileImageButton = (ImageButton) findViewById(R.id.profileImageButton);
        profileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileClick();
            }
        });

        testButton = (Button) findViewById(R.id.testButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTestClick();
            }
        });
    }

    private void onTestClick() {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    private void onProfileClick() {
        Intent intent = null;
        intent = new Intent(this, AuthenticationActivity.class);
        startActivity(intent);
    }
}
