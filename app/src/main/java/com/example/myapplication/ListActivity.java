package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ConatctModel[] myListData = new ConatctModel[] {
                new ConatctModel("Email", android.R.drawable.ic_dialog_email),
                new ConatctModel("Info", android.R.drawable.ic_dialog_info),
                new ConatctModel("Delete", android.R.drawable.ic_delete),
                new ConatctModel("Dialer", android.R.drawable.ic_dialog_dialer),
                new ConatctModel("Alert", android.R.drawable.ic_dialog_alert),
                new ConatctModel("Map", android.R.drawable.ic_dialog_map),
                new ConatctModel("Email", android.R.drawable.ic_dialog_email),
                new ConatctModel("Info", android.R.drawable.ic_dialog_info),
                new ConatctModel("Delete", android.R.drawable.ic_delete),
                new ConatctModel("Dialer", android.R.drawable.ic_dialog_dialer),
                new ConatctModel("Alert", android.R.drawable.ic_dialog_alert),
                new ConatctModel("Madp", android.R.drawable.ic_dialog_map),
                new ConatctModel("Emacil", android.R.drawable.ic_dialog_email),
                new ConatctModel("Infod", android.R.drawable.ic_dialog_info),
                new ConatctModel("Deletcde", android.R.drawable.ic_delete),
                new ConatctModel("Diacler", android.R.drawable.ic_dialog_dialer),
                new ConatctModel("Alecrt", android.R.drawable.ic_dialog_alert),
                new ConatctModel("Macsccp", android.R.drawable.ic_dialog_map),
                new ConatctModel("Emaicsl", android.R.drawable.ic_dialog_email),
                new ConatctModel("Infco", android.R.drawable.ic_dialog_info),
                new ConatctModel("Delscete", android.R.drawable.ic_delete),
                new ConatctModel("Dialcser", android.R.drawable.ic_dialog_dialer),
                new ConatctModel("Alercst", android.R.drawable.ic_dialog_alert),
                new ConatctModel("Mapdvc", android.R.drawable.ic_dialog_map),
        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        MyListAdapter adapter = new MyListAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    
}