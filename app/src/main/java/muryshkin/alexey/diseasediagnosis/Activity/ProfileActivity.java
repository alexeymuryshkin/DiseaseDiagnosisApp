package muryshkin.alexey.diseasediagnosis.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import muryshkin.alexey.diseasediagnosis.Adapter.ProfileFragmentsAdapter;
import muryshkin.alexey.diseasediagnosis.Data.Authentication;
import muryshkin.alexey.diseasediagnosis.Fragment.SettingsFragment;
import muryshkin.alexey.diseasediagnosis.Fragment.ProfileFragment;
import muryshkin.alexey.diseasediagnosis.Fragment.SessionsFragment;
import muryshkin.alexey.diseasediagnosis.R;

public class ProfileActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageButton backImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        supportActionBar.setCustomView(R.layout.customized_profile_action_bar);

        backImageButton = (ImageButton) findViewById(R.id.backImageButton);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setFragments();

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager, true);
    }

    private void setFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add( ProfileFragment.newInstance() );
        fragments.add( SessionsFragment.newInstance() );
        fragments.add( SettingsFragment.newInstance() );
        ProfileFragmentsAdapter adapter = new ProfileFragmentsAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
    }
}
