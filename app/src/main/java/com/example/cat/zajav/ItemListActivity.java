package com.example.cat.zajav;

import android.content.Context;
import android.content.Intent;
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

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    Cursor semCursor, semSpecCur;
    private SimpleCursorAdapter ZajavAdapter;
    private String zajavID;
    private Cursor zCursor;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        listView = (ListView) findViewById(R.id.zajav_list);

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.z_header, listView, false);
        listView.addHeaderView(header, null, true);


        // открываем подключение
        ZajavDBHelper.sqlHelper = new ZajavDBHelper(this);


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
        if (zCursor != null)
            zCursor.close();

        zCursor = ZajavDBHelper.fetchAllZajavs();

        // The desired columns to be bound
        String[] columns = new String[]{
                ZajavDBHelper.Z_ID,
                ZajavDBHelper.Z_STRCODE,
                ZajavDBHelper.Z_DATE,
                ZajavDBHelper.Z_NOTE
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.z_id,
                R.id.z_strcode,
                R.id.z_date,
                R.id.z_note
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        ZajavAdapter = new SimpleCursorAdapter(
                this, R.layout.item_list_content,
                zCursor,
                columns,
                to,
                0);

        // Assign adapter to ListView


        LayoutInflater inflater = getLayoutInflater();

        listView.setAdapter(ZajavAdapter);

        TextView tv = (TextView) findViewById(R.id.textView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
//                zCursor = (Cursor) listView.getItemAtPosition(position);
//                listView.setSelection(position);

                // Get the state's capital from this row in the database.
                zajavID =
                        zCursor.getString(zCursor.getColumnIndexOrThrow("_id"));
//                Toast.makeText(getApplicationContext(),
//                        zajavID, Toast.LENGTH_SHORT).show();

            }
        });

     /*   EditText myFilter = (EditText) findViewById(R.id.myFilter);
        myFilter.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                ZajavAdapter.getFilter().filter(s.toString());
            }
        });

       ZajavAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return sqlHelper.fetchCountriesByName(constraint.toString());
            }
        });
*/
    }

 /*   private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<DummyContent.DummyItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        ItemDetailFragment fragment = new ItemDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ItemDetailActivity.class);
                        intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public DummyContent.DummyItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }*/

    public void zajavAddClick(View view) {
//        Toast.makeText(getApplicationContext(),
//                zajavID+"  Add", Toast.LENGTH_SHORT).show();

        int id=ZajavDBHelper.addZajav();
        openDetail(view, Integer.toString(id),
                "",
                "",
                "");
    }

    public void zajavHeadClick(View view) {
//        Toast.makeText(getApplicationContext(),
//                zajavID+"  EditHead", Toast.LENGTH_SHORT).show();

        openDetail(view, zCursor.getString(zCursor.getColumnIndexOrThrow(ZajavDBHelper.Z_ID)),
                         zCursor.getString(zCursor.getColumnIndexOrThrow(ZajavDBHelper.Z_STRCODE)),
                         zCursor.getString(zCursor.getColumnIndexOrThrow(ZajavDBHelper.Z_DATE)),
                         zCursor.getString(zCursor.getColumnIndexOrThrow(ZajavDBHelper.Z_NOTE)));
    }

    public void openDetail(View view, String p_id, String p_strcode, String p_date, String p_note) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ID, p_id);
            arguments.putString(ItemDetailFragment.ARG_STRCODE, p_strcode);
            arguments.putString(ItemDetailFragment.ARG_DATE, p_date);
            arguments.putString(ItemDetailFragment.ARG_NOTE, p_note);
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        } else {
            Context context = view.getContext();
            Intent intent = new Intent(context, ItemDetailActivity.class);
            intent.putExtra(ItemDetailFragment.ARG_ID, p_id);
            intent.putExtra(ItemDetailFragment.ARG_STRCODE, p_strcode);
            intent.putExtra(ItemDetailFragment.ARG_DATE, p_date);
            intent.putExtra(ItemDetailFragment.ARG_NOTE, p_note);
            context.startActivity(intent);
        }
    }

    public void zajavSpecClick(View view) {
//        Toast.makeText(getApplicationContext(),
//                zajavID + "  Spec", Toast.LENGTH_SHORT).show();
        Context context = view.getContext();
        Intent intent = new Intent(context, SpecListActivity.class);
        intent.putExtra(ItemDetailFragment.ARG_ID, zajavID);
        context.startActivity(intent);
    }

    public void zajavDeleteClick(View view) {

//        Toast.makeText(getApplicationContext(),
//                zajavID + "  Delete", Toast.LENGTH_SHORT).show();
        ZajavDBHelper.deleteZajav(zajavID);
        displayListView();
    }
}
