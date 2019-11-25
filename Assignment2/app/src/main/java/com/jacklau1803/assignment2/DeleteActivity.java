package com.jacklau1803.assignment2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        final EditText id = findViewById(R.id.et_id);
        final EditText title = findViewById(R.id.et_title_del);
        Button del = findViewById(R.id.b_delete);
        final DBHandler db = new DBHandler(this);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id.getText().toString().trim().length() != 0){
                    toastMessage("Deleting where: ID = " + id.getText().toString() + ", from database");
                    boolean exist = db.checkData("ID", id.getText().toString());

                    if(!exist) {
                        toastMessage("Failed to delete, id doesn't exist");
                    }else {
                        db.deleteId(id.getText().toString());
                        id.getText().clear();
                        title.getText().clear();
                        toastMessage("Successfully Deleted");
                    }
                }else if(title.getText().toString().trim().length() != 0){
                    toastMessage("Deleting where: Title = " + title.getText().toString() + ", from database");
                    boolean exist = db.checkData("title", title.getText().toString());
                    if(!exist) {
                        toastMessage("Failed to delete, id doesn't exist");
                    }else {
                        db.deleteName(title.getText().toString());
                        title.getText().clear();
                        title.getText().clear();
                        toastMessage("Successfully Deleted");
                    }
                }else {
                    toastMessage("Please fill the text fields before deleting");
                }
                finish();
            }
        });
    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
