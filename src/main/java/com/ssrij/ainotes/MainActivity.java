package com.ssrij.ainotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardProvider;
import com.dexafree.materialList.card.OnActionClickListener;
import com.dexafree.materialList.card.action.TextViewAction;
import com.dexafree.materialList.listeners.RecyclerItemClickListener;
import com.dexafree.materialList.view.MaterialListView;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    Realm realm;
    MaterialListView mListView;
    String locs[] = { "Russia", "Finland", "Canada", "France",
            "Germany", "Ukraine", "India", "Japan", "China", "Vietnam", "Australia"};
    long times[] = {1476848460, 1476729300, 1474546800,
            1472911320, 1423041060, 1425513720, 1403490120,
            1423455720, 1442238840, 1422114240};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        realm = Realm.getDefaultInstance();
        mListView = (MaterialListView) findViewById(R.id.noteList);
        FloatingActionButton fabCreateNote = (FloatingActionButton)findViewById(R.id.fabAddNote);

        mListView.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Card card, int position) {
                Intent intent = new Intent(getBaseContext(), NoteActivity.class);
                RealmQuery<TextNoteItem> query = Realm.getDefaultInstance().where(TextNoteItem.class);
                query.equalTo("title", card.getProvider().getTitle());
                TextNoteItem item = query.findFirst();
                intent.putExtra("EXTRA_UNIQUE_NOTE_ID", item.getUniqueID());
                startActivity(intent);

            }

            @Override
            public void onItemLongClick(@NonNull Card card, int position) {

            }
        });

        fabCreateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NoteActivity.class));
            }
        });

        if (!realm.isEmpty()) {
            loadNotes();
        }

    }

    public void loadDummyNotesIntoDB() {
        realm.beginTransaction();

        TextNoteItem item1 = realm.createObject(TextNoteItem.class);
        item1.setUniqueID(UUID.randomUUID().toString());
        item1.setTitle("Earthquake in New Zealand");
        item1.setSubTitle("7.8 Magnitute earthquake in New Zealand");
        item1.setCreationLoc(locs[new Random().nextInt(9) + 1]);
        item1.setCreationTime(new Date(times[new Random().nextInt(9) + 1] * 1000));
        InputStream is = getResources().openRawResource(R.raw.note1);
        String noteText1 = "";
        try {
            noteText1 = IOUtils.toString(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        IOUtils.closeQuietly(is);
        item1.setNoteText(noteText1);

        TextNoteItem item2 = realm.createObject(TextNoteItem.class);
        item2.setUniqueID(UUID.randomUUID().toString());
        item2.setTitle("Nikola Tesla");
        item2.setSubTitle("Nikola Tesla - A Serbian-American inventor");
        item2.setCreationLoc(locs[new Random().nextInt(9) + 1]);
        item2.setCreationTime(new Date(times[new Random().nextInt(9) + 1] * 1000));
        InputStream is2 = getResources().openRawResource(R.raw.note2);
        String noteText2 = "";
        try {
            noteText2 = IOUtils.toString(is2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        IOUtils.closeQuietly(is2);
        item2.setNoteText(noteText2);

        TextNoteItem item3 = realm.createObject(TextNoteItem.class);
        item3.setUniqueID(UUID.randomUUID().toString());
        item3.setTitle("Vladimir Putin");
        item3.setSubTitle("President of the Russian Federation");
        item3.setCreationLoc(locs[new Random().nextInt(9) + 1]);
        item3.setCreationTime(new Date(times[new Random().nextInt(9) + 1] * 1000));
        InputStream is3 = getResources().openRawResource(R.raw.note3);
        String noteText3 = "";
        try {
            noteText3 = IOUtils.toString(is3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        IOUtils.closeQuietly(is3);
        item3.setNoteText(noteText3);

        TextNoteItem item4 = realm.createObject(TextNoteItem.class);
        item4.setUniqueID(UUID.randomUUID().toString());
        item4.setTitle("Oxford");
        item4.setSubTitle("Oxfordshire");
        item4.setCreationLoc(locs[new Random().nextInt(9) + 1]);
        item4.setCreationTime(new Date(times[new Random().nextInt(9) + 1] * 1000));
        InputStream is4 = getResources().openRawResource(R.raw.note4);
        String noteText4 = "";
        try {
            noteText4 = IOUtils.toString(is4);
        } catch (IOException e) {
            e.printStackTrace();
        }
        IOUtils.closeQuietly(is4);
        item4.setNoteText(noteText4);


        TextNoteItem item5 = realm.createObject(TextNoteItem.class);
        item5.setUniqueID(UUID.randomUUID().toString());
        item5.setTitle("Microsoft");
        item5.setSubTitle("Microsoft Corporation");
        item5.setCreationLoc(locs[new Random().nextInt(9) + 1]);
        item5.setCreationTime(new Date(times[new Random().nextInt(9) + 1] * 1000));
        InputStream is5 = getResources().openRawResource(R.raw.note5);
        String noteText5 = "";
        try {
            noteText5 = IOUtils.toString(is5);
        } catch (IOException e) {
            e.printStackTrace();
        }
        IOUtils.closeQuietly(is5);
        item5.setNoteText(noteText5);

        TextNoteItem item6 = realm.createObject(TextNoteItem.class);
        item6.setUniqueID(UUID.randomUUID().toString());
        item6.setTitle("Android");
        item6.setSubTitle("Android - World's most popular mobile OS");
        item6.setCreationLoc(locs[new Random().nextInt(9) + 1]);
        item6.setCreationTime(new Date(times[new Random().nextInt(9) + 1] * 1000));
        InputStream is6 = getResources().openRawResource(R.raw.note6);
        String noteText6 = "";
        try {
            noteText6 = IOUtils.toString(is6);
        } catch (IOException e) {
            e.printStackTrace();
        }
        IOUtils.closeQuietly(is6);
        item6.setNoteText(noteText6);

        TextNoteItem item7 = realm.createObject(TextNoteItem.class);
        item7.setUniqueID(UUID.randomUUID().toString());
        item7.setTitle("Lurkmore");
        item7.setSubTitle("Lurkmore, a Russian Wikipedia");
        item7.setCreationLoc(locs[new Random().nextInt(9) + 1]);
        item7.setCreationTime(new Date(times[new Random().nextInt(9) + 1] * 1000));
        InputStream is7 = getResources().openRawResource(R.raw.note7);
        String noteText7 = "";
        try {
            noteText7 = IOUtils.toString(is7);
        } catch (IOException e) {
            e.printStackTrace();
        }
        IOUtils.closeQuietly(is7);
        item7.setNoteText(noteText7);

        TextNoteItem item8 = realm.createObject(TextNoteItem.class);
        item8.setUniqueID(UUID.randomUUID().toString());
        item8.setTitle("Network address translation");
        item8.setSubTitle("What is NAT or Network address translation");
        item8.setCreationLoc(locs[new Random().nextInt(9) + 1]);
        item8.setCreationTime(new Date(times[new Random().nextInt(9) + 1] * 1000));
        InputStream is8 = getResources().openRawResource(R.raw.note8);
        String noteText8 = "";
        try {
            noteText8 = IOUtils.toString(is8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        IOUtils.closeQuietly(is8);
        item8.setNoteText(noteText8);

        TextNoteItem item9 = realm.createObject(TextNoteItem.class);
        item9.setUniqueID(UUID.randomUUID().toString());
        item9.setTitle("Emancipation Reform");
        item9.setSubTitle("Emancipation reform of 1861");
        item9.setCreationLoc(locs[new Random().nextInt(9) + 1]);
        item9.setCreationTime(new Date(times[new Random().nextInt(9) + 1] * 1000));
        InputStream is9 = getResources().openRawResource(R.raw.note9);
        String noteText9 = "";
        try {
            noteText9 = IOUtils.toString(is9);
        } catch (IOException e) {
            e.printStackTrace();
        }
        IOUtils.closeQuietly(is9);
        item9.setNoteText(noteText9);


        TextNoteItem item10 = realm.createObject(TextNoteItem.class);
        item10.setUniqueID(UUID.randomUUID().toString());
        item10.setTitle("Atheism");
        item10.setSubTitle("What is Atheism");
        item10.setCreationLoc(locs[new Random().nextInt(9) + 1]);
        item10.setCreationTime(new Date(times[new Random().nextInt(9) + 1] * 1000));
        InputStream is10 = getResources().openRawResource(R.raw.note10);
        String noteText10 = "";
        try {
            noteText10 = IOUtils.toString(is10);
        } catch (IOException e) {
            e.printStackTrace();
        }
        IOUtils.closeQuietly(is10);
        item10.setNoteText(noteText10);

        realm.commitTransaction();

        mListView.getAdapter().notifyDataSetChanged();
    }

    public void loadNotes() {
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy HH:mm");
        RealmQuery<TextNoteItem> query = realm.where(TextNoteItem.class);
        RealmResults<TextNoteItem> items = query.findAll();
        for (TextNoteItem item : items) {
            Card card = new Card.Builder(this)
                    .withProvider(new CardProvider())
                    .setLayout(R.layout.material_basic_buttons_card)
                    .setTitle(item.getTitle())
                    .setDescription(item.getNoteText().length() > 160 ? item.getNoteText().substring(0, 160) + "..." : item.getNoteText())
                    .addAction(R.id.left_text_button, new TextViewAction(this)
                            .setText(DATE_FORMAT.format(item.getCreationTime()))
                            .setTextResourceColor(R.color.black_button)
                            .setListener(new OnActionClickListener() {
                                @Override
                                public void onActionClicked(View view, Card card) {
                                    Toast.makeText(getApplicationContext(), "To do", Toast.LENGTH_SHORT).show();
                                }
                            }))
                    .addAction(R.id.right_text_button, new TextViewAction(this)
                            .setText(item.getCreationLoc())
                            .setTextResourceColor(R.color.accent_material_dark)
                            .setListener(new OnActionClickListener() {
                                @Override
                                public void onActionClicked(View view, Card card) {
                                    Toast.makeText(getApplicationContext(), "To do", Toast.LENGTH_SHORT).show();
                                }
                            }))
                    .endConfig()
                    .build();

            mListView.getAdapter().add(card);
        }
        mListView.smoothScrollToPosition(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_note:
                startActivity(new Intent(this, NoteActivity.class));
                return true;
            case R.id.search_notes:
                startActivity(new Intent(this, SearchActivity.class));
                return true;
            case R.id.load_dummy:
                loadDummyNotesIntoDB();
                return true;
            case R.id.reload_notes:
                mListView.getAdapter().clearAll();
                loadNotes();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
