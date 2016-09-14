package com.example.cat.sqliteportactivity;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.cat.sqliteport.R;

public class TblDataActivity extends AppCompatActivity {
    private SimpleCursorAdapter TblDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_data);



        String s_uri = (String) getIntent().getExtras().get("URI");
        String s_id = (String) getIntent().getExtras().get("id");

        Uri uri = Uri.parse(s_uri);
        if ((s_id == null) || (s_id.length() == 0)) {
        } else {
            uri= ContentUris.withAppendedId(uri, Long.parseLong(s_id));
        }
        Cursor c = getContentResolver().query(uri, null, null, null, null);

        ListView listView = (ListView) findViewById(R.id.data_list_view);

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.z_header, listView, false);



        String[] columns = new String[c.getColumnCount()];
        int[] all = new int[]{
                R.id.d_1, R.id.d_2, R.id.d_3, R.id.d_4, R.id.d_5, R.id.d_6, R.id.d_7, R.id.d_8
        };
        int[] to = new int[c.getColumnCount()];

        for (int i=0; i<c.getColumnCount(); i++){
           columns[i]=c.getColumnName(i);
           to[i]=all[i];
           TextView tv= new TextView(header.getContext());
           tv.setText("   "+c.getColumnName(i)+"    ");
           tv.setGravity(Gravity.LEFT);
            //noinspection ResourceType
         //   tv.setTextColor(getResources().getColor(R.drawable.item_text_selector));
           header.addView(tv);
        }

        // the XML defined views which the data will be bound to

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        TblDataAdapter = new SimpleCursorAdapter(
                this, R.layout.tbl_data_content,
                c,
                columns,
                to,
                0);

        // Assign adapter to ListView


//        LayoutInflater inflater = getLayoutInflater();


        listView.addHeaderView(header, null, true);

        listView.setAdapter(TblDataAdapter);

    }
}
