package com.example.perpusapp;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import
        com.google.android.material.floatingactionbutton.FloatingActionButton;
public class MainActivity2 extends AppCompatActivity implements
        AdapterView.OnItemClickListener,
        DialogChoise.DialogChoiceListener {
    ListView Is;
    DBHelper dbHelper;
    Context context;
    int listData;
    SharedPreferences viewData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this,
                        AddActivity.class));
            }
        });
        dbHelper = new DBHelper(this);
        Is = (ListView) findViewById(R.id.list_peminjaman);
        Is.setOnItemClickListener(this);
        viewData = getSharedPreferences("currentListView", 0);
        listData = viewData.getInt("currentListView", 0);
        setupListView();
    }
    private void setupListView() {
        if (listData == 0) {
            allData();
        } else if (listData == 1) {
            dataDipinjam();
        } else if (listData == 2) {
            dataDikembalikan();
        }
    }
    public void allData() {
        Cursor cursor = dbHelper.allData();
        CustomCursorAdapter customCursorAdapter = new
                CustomCursorAdapter(this, cursor, 1);
        Is.setAdapter(customCursorAdapter);
    }
    public void dataDipinjam() {
        Cursor cursor = dbHelper.dataPinjam();
        CustomCursorAdapter customCursorAdapter = new
                CustomCursorAdapter(this, cursor, 1);
        Is.setAdapter(customCursorAdapter);
    }
    public void dataDikembalikan() {
        Cursor cursor = dbHelper.dataDikembalikan();
        CustomCursorAdapter customCursorAdapter = new
                CustomCursorAdapter(this, cursor, 1);
        Is.setAdapter(customCursorAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.sort) {
            DialogFragment dialogFragment = new DialogChoise();
            dialogFragment.setCancelable(false);
            dialogFragment.show(getSupportFragmentManager(), "Choice Dialog");
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long
            I) {
        TextView getID = (TextView) view.findViewById(R.id.listID);
        final long id = Long.parseLong(getID.getText().toString());
        Cursor cur = dbHelper.oneData(id);
        cur.moveToFirst();
        Intent idpinjam = new Intent(MainActivity2.this,
                AddActivity.class);
        idpinjam.putExtra(DBHelper.row_id, id);
        startActivity(idpinjam);
    }
    @Override
    protected void onResume() {
        super.onResume();
        setupListView();
    }
    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        SharedPreferences.Editor editor = viewData.edit();
        if (position == 0) {
            editor.putInt("currentListView", 0);
            editor.apply();
            allData();
        } else if (position == 1) {
            editor.putInt("currentListView", 1);
            editor.apply();
            dataDipinjam();
        } else if (position == 2) {
            editor.putInt("currentListView", 2);
            editor.apply();
            dataDikembalikan();
        }
    }
    @Override
    public void onNegativeButtonClicked() {
    }
    public void keluar(View view) {
        Intent intent = new Intent(MainActivity2.this,
                MainActivity3.class);
        startActivity(intent);
    }
    public void buku(View view) {
        Intent intent = new Intent(MainActivity2.this, entridata.class);
        startActivity(intent);
    }
}
