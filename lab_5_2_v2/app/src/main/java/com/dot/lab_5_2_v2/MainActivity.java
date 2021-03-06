package com.dot.lab_5_2_v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu,v,menuInfo);
        MenuInflater inflater = getMenuInflater();
        //menu.setHeaderTitle("Context Menu");
        inflater.inflate(R.menu.contextualmenu,menu);
    }
    

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        super.onContextItemSelected(item);
        Log.d(TAG, "onContextItemSelected: "+ item.getTitle().toString());

        return true;
    }
}