package com.example.perpusapp;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class entridata extends AppCompatActivity {
    EditText name, contact, dob;
    Button insert, update, delete, view;
    DBHelper2 DB2;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entridata);
        name = findViewById(R.id.name);
        contact = findViewById(R.id.contact);
        update = findViewById(R.id.btnUpdate);
        dob = findViewById(R.id.dob);
        insert = findViewById(R.id.btnInsert);
        delete = findViewById(R.id.btnDelete);
        view = findViewById(R.id.btnView);
        DB2 = new DBHelper2(this);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                String contactTXT = contact.getText().toString();
                String dobTXT = dob.getText().toString();
                Boolean checkinsertdata = DB2.insertuserdata(nameTXT,
                        contactTXT, dobTXT);
                if (checkinsertdata == true)
                    Toast.makeText(entridata.this, "New Entry Inserted",
                                    Toast.LENGTH_SHORT)
                            .show();
                else
                    Toast.makeText(entridata.this, "New Entry Not Inserted", Toast.LENGTH_SHORT)
                                    .show();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                String contactTXT = contact.getText().toString();
                String dobTXT = dob.getText().toString();
                Boolean checkupdatedata = DB2.updateuserdata(nameTXT,
                        contactTXT, dobTXT);
                if (checkupdatedata == true)
                    Toast.makeText(entridata.this, "Entry Updated",
                            Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(entridata.this, "New Entry Not Updated",
                                    Toast.LENGTH_SHORT)
                            .show();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                Boolean checkudeletedata = DB2.deletedata(nameTXT);
                if (checkudeletedata == true)
                    Toast.makeText(entridata.this, "Entry Deleted",
                            Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(entridata.this, "Entry Not Deleted",
                                    Toast.LENGTH_SHORT)
                            .show();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB2.getdata();
                if (res.getCount() == 0) {
                    Toast.makeText(entridata.this, "No Entry Exists",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Nama buku :" + res.getString(0) + "\n");
                    buffer.append("pengarang :" + res.getString(1) + "\n");
                    buffer.append("harga :" + res.getString(2) + "\n\n");
                }
                AlertDialog.Builder builder = new
                        AlertDialog.Builder(entridata.this);
                builder.setCancelable(true);
                builder.setTitle("koleksi buku saya");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
    }
    public void kembali2(View view) {
        Intent intent = new Intent(entridata.this, MainActivity2.class);
        startActivity(intent);
    }
}
