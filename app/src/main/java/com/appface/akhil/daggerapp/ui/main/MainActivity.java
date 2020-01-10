package com.appface.akhil.daggerapp.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.appface.akhil.daggerapp.BaseActivity;
import com.appface.akhil.daggerapp.R;
import com.appface.akhil.daggerapp.ui.main.posts.PostsFragments;
import com.appface.akhil.daggerapp.ui.main.profile.ProfileFragment;

import androidx.annotation.Nullable;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "MainActivity", Toast.LENGTH_SHORT).show();
        testFragment();
    }

    private void testFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, new PostsFragments())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout: {
                sessionManager.logOut();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
