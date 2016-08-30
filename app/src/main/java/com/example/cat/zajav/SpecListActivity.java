package com.example.cat.zajav;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class SpecListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    Cursor semCursor, semSpecCur;
    private SimpleCursorAdapter SpecAdapter;
    private String zajavID;
    private String specID;
    private Cursor sCursor;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spec_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        zajavID=getIntent().getStringExtra(ItemDetailFragment.ARG_ID);

        listView = (ListView) findViewById(R.id.spec_list);

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.s_header, listView, false);
        listView.addHeaderView(header, null, true);


        displayListView();



/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/

//        View recyclerView = findViewById(R.id.item_list);
//        assert recyclerView != null;

//        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        ZajavAdapter.notifyDataSetChanged();
//        listView.refreshDrawableState();
        displayListView();
    }

    private void displayListView() {


//        Cursor cursor = ZajavDBHelper.fetchAllZajavs();
        if (sCursor != null)
            sCursor.close();

        sCursor = ZajavDBHelper.fetchAllSpec(zajavID);

        // The desired columns to be bound
        String[] columns = new String[]{
                ZajavDBHelper.S_ID,
                ZajavDBHelper.S_RES,
                ZajavDBHelper.S_RESNAME,
                ZajavDBHelper.S_QUANTITY,
                ZajavDBHelper.S_COST
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.s_id,
                R.id.s_res,
                R.id.s_resname,
                R.id.s_quantity,
                R.id.s_cost
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        SpecAdapter = new SimpleCursorAdapter(
                this, R.layout.spec_list_content,
                sCursor,
                columns,
                to,
                0);

        // Assign adapter to ListView


        LayoutInflater inflater = getLayoutInflater();

        listView.setAdapter(SpecAdapter);

        TextView tv = (TextView) findViewById(R.id.textView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
//                zCursor = (Cursor) listView.getItemAtPosition(position);
//                listView.setSelection(position);

                // Get the state's capital from this row in the database.
                specID =
                        sCursor.getString(sCursor.getColumnIndexOrThrow("_id"));
//                Toast.makeText(getApplicationContext(),
//                        zajavID, Toast.LENGTH_SHORT).show();

            }
        });

    }


    public void specAddClick(View view) {

        Intent resIntent = new Intent(Intent.ACTION_VIEW);
        resIntent.addCategory("SPR_RESOURCES");
 //       resIntent.setType("*/*");

        PackageManager packageManager = getPackageManager();
        List activities = packageManager.queryIntentActivities(resIntent,
                PackageManager.MATCH_ALL);
        if (activities.size() == 0)
        Toast.makeText(getApplicationContext(),"Нет поставшика контента для 'SPR_RESOURCES'.", Toast.LENGTH_SHORT).show();
        else{
            startActivityForResult(resIntent, 110);
 //           startActivity (resIntent);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
 //       if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode > 0) {
                int id=ZajavDBHelper.addSpec(zajavID, resultCode);
            }
   //     }
    }



    public void specDeleteClick(View view) {

//        Toast.makeText(getApplicationContext(),
//                zajavID + "  Delete", Toast.LENGTH_SHORT).show();
        ZajavDBHelper.deleteSpec(specID);
        displayListView();
    }
}
