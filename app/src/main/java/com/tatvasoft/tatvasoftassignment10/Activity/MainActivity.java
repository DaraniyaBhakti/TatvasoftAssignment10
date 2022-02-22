package com.tatvasoft.tatvasoftassignment10.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tatvasoft.tatvasoftassignment10.Fragment.UserListFragment;
import com.tatvasoft.tatvasoftassignment10.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainContainer,new UserListFragment())
                .commit();
    }
}