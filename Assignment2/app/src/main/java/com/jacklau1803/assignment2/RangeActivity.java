package com.jacklau1803.assignment2;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RangeActivity extends AppCompatActivity {
    ListView listView;
    Button display;
    EditText from;
    EditText to;
    DBHandler db;
    ArrayList<String> ids = new ArrayList<>();
    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range);

        display = findViewById(R.id.b_display);
        from = findViewById(R.id.et_from);
        to = findViewById(R.id.et_to);
        listView = findViewById(R.id.rangelist);
        db = new DBHandler(this);
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (from.getText().toString().trim().length() == 0) {
                    toastMessage("Enter from index");
                } else if (to.getText().toString().trim().length() == 0) {
                    toastMessage("Enter to index");
                } else if (!db.checkData("ID", from.getText().toString())) {
                    toastMessage("From index out of range");
                } else if (!db.checkData("ID", to.getText().toString())) {
                    toastMessage("To index out of range");
                } else {
                    ids = new ArrayList<>();
                    titles = new ArrayList<>();
                    images = new ArrayList<>();
                    Cursor cursor = db.getData();

                    if (cursor.getCount() == 0) {
                        toastMessage("Database Empty");
                    } else {
                        while (cursor.moveToNext()) {
                            if (Integer.parseInt(cursor.getString(0)) >= Integer.parseInt(from.getText().toString()) &&
                                    Integer.parseInt(cursor.getString(0)) <= Integer.parseInt(to.getText().toString())) {
                                titles.add(cursor.getString(1));
                                ids.add(cursor.getString(0));
                                images.add(cursor.getString(2));
                            }
                        }
                    }
                    ListAdaptor adaptor = new ListAdaptor();
                    listView.setAdapter(adaptor);
                }
            }
        });

    }

    class ListAdaptor extends BaseAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.list, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            TextView title = (TextView) view.findViewById(R.id.tv_title);
            TextView id = (TextView) view.findViewById(R.id.tv_id);
            title.setText("TITLE: " + titles.get(position));
            title.setText("TITLE: " + titles.get(position));
            title.setText("TITLE: " + titles.get(position));
            id.setText("ID: " + ids.get(position));

            imageView.setImageURI(Uri.parse(images.get(position)));
            return view;
        }
    }
    private void toastMessage(String message){
            Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
