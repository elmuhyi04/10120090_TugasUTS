package com.example.a10120090_uts;
// NIM      : 10120090
// Nama     : Muhammad Rizky Muhyi
// Kelas    : IF-3


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.tugasuts_10120090.R;
import com.example.tugasuts_10120090.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import model.DatabaseHelper;
import model.NoteModel;
import presenter.InfoAplFragment;
import presenter.NoteFragment;
import presenter.ProfilFragment;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private EditText etTitle, etCategory, etContent;
    private ListView listViewNotes;
    private ArrayAdapter<NoteModel> adapter;
    private ArrayList<NoteModel> noteList;
    private ProfilFragment profilFragment;
    private NoteFragment noteFragment;
    private InfoAplFragment infoAplFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        @NonNull ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        databaseHelper = new DatabaseHelper(this);
        etTitle = findViewById(R.id.etTitle);
        etCategory = findViewById(R.id.etCategory);
        etContent = findViewById(R.id.etContent);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        Button btnDelete = findViewById(R.id.btnDelete);
        Button btnShowAll = findViewById(R.id.btnShowAll);
        listViewNotes = findViewById(R.id.listViewNotes);

        noteList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, noteList);
        listViewNotes.setAdapter(adapter);

        profilFragment = new ProfilFragment();
        noteFragment = new NoteFragment();
        infoAplFragment = new InfoAplFragment();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNote();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote();
            }
        });

        btnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAllNotes();
            }
        });

        listViewNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NoteModel selectedNote = noteList.get(position);
                etTitle.setText(selectedNote.getTitle());
                etCategory.setText(selectedNote.getCategory());
                etContent.setText(selectedNote.getContent());

                TextView tvDate = findViewById(R.id.tvDate);
                tvDate.setText(selectedNote.getDate());
            }
        });

        replaceFragment(profilFragment);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.profile:
                    replaceFragment(profilFragment);
                    break;
                case R.id.catatan:
                    replaceFragment(noteFragment);
                    break;
                case R.id.infoaplikasi:
                    replaceFragment(infoAplFragment);
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        @NonNull ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
    private void saveNote() {
        String title = etTitle.getText().toString().trim();
        String category = etCategory.getText().toString().trim();
        String content = etContent.getText().toString().trim();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        if (title.isEmpty() || category.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TITLE, title);
        values.put(DatabaseHelper.COLUMN_CATEGORY, category);
        values.put(DatabaseHelper.COLUMN_CONTENT, content);
        values.put(DatabaseHelper.COLUMN_DATE, date);
        long id = db.insert(DatabaseHelper.TABLE_NAME, null, values);

        if (id != -1) {
            Toast.makeText(this, "Note added with ID: " + id, Toast.LENGTH_SHORT).show();
            etTitle.setText("");
            etCategory.setText("");
            etContent.setText("");
            showAllNotes();
        } else {
            Toast.makeText(this, "Failed to add note", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateNote() {
        String title = etTitle.getText().toString().trim();
        String category = etCategory.getText().toString().trim();
        String content = etContent.getText().toString().trim();

        if (title.isEmpty() || category.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TITLE, title);
        values.put(DatabaseHelper.COLUMN_CATEGORY, category);
        values.put(DatabaseHelper.COLUMN_CONTENT, content);

        String selection = DatabaseHelper.COLUMN_TITLE + " LIKE ?";
        String[] selectionArgs = { etTitle.getText().toString().trim() };

        int rowsAffected = db.update(DatabaseHelper.TABLE_NAME, values, selection, selectionArgs);

        if (rowsAffected > 0) {
            Toast.makeText(this, "Note updated successfully", Toast.LENGTH_SHORT).show();
            etTitle.setText("");
            etCategory.setText("");
            etContent.setText("");
            showAllNotes();
        } else {
            Toast.makeText(this, "Failed to update note", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteNote() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String selection = DatabaseHelper.COLUMN_TITLE + " LIKE ?";
        String[] selectionArgs = { etTitle.getText().toString().trim() };
        int rowsAffected = db.delete(DatabaseHelper.TABLE_NAME, selection, selectionArgs);

        if (rowsAffected > 0) {
            Toast.makeText(this, "Note deleted successfully", Toast.LENGTH_SHORT).show();
            etTitle.setText("");
            etCategory.setText("");
            etContent.setText("");
            showAllNotes();
        } else {
            Toast.makeText(this, "Failed to delete note", Toast.LENGTH_SHORT).show();
        }
    }


    private void showAllNotes() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projection = {
                DatabaseHelper.COLUMN_ID,
                DatabaseHelper.COLUMN_TITLE,
                DatabaseHelper.COLUMN_CATEGORY,
                DatabaseHelper.COLUMN_CONTENT,
                DatabaseHelper.COLUMN_DATE
        };
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        noteList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE));
            String category = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CATEGORY));
            String content = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CONTENT));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE));
            NoteModel note = new NoteModel(id, title, category, content, date);
            noteList.add(note);
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }
}