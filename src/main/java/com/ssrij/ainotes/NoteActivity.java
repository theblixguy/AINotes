package com.ssrij.ainotes;

import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.OnReverseGeocodingListener;
import io.nlopez.smartlocation.SmartLocation;
import io.realm.Realm;
import io.realm.RealmQuery;

public class NoteActivity extends AppCompatActivity {

    boolean readMode = false;
    String uniqueID;

    Location loc = null;
    String locText = null;
    String locs[] = { "Russia", "Finland", "Canada", "France",
            "Germany", "Ukraine", "India", "Japan", "China", "Vietnam", "Australia"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (!SmartLocation.with(this).location().state().locationServicesEnabled()) {
            Log.i("AINotes", "Location services not available");
        }

        if (!SmartLocation.with(this).location().state().isAnyProviderAvailable()) {
            Log.i("AINotes", "No providers available");
        }

        if (SmartLocation.with(this).location().state().locationServicesEnabled() &&
                SmartLocation.with(this).location().state().isAnyProviderAvailable()) {

            SmartLocation.with(this).location()
                    .oneFix()
                    .start(new OnLocationUpdatedListener() {
                        @Override
                        public void onLocationUpdated(Location location) {
                            Log.i("AINotes", "Got location!");
                            Log.i("AINotes", Double.toString(location.getLatitude()) + "/" + Double.toString(location.getLongitude()));
                            SmartLocation.with(NoteActivity.this).geocoding()
                                    .reverse(location, new OnReverseGeocodingListener() {
                                        @Override
                                        public void onAddressResolved(Location original, List<Address> results) {
                                            Log.i("AINotes", "Got reversed location!");
                                            Log.i("AINotes", "Results size: " + Integer.toString(results.size()));
                                            locText = results.get(0).toString();
                                        }
                                    });
                        }
                    });
        }

        FloatingActionButton fabCreateNote = (FloatingActionButton)findViewById(R.id.fabSaveNote);
        final EditText title = (EditText)findViewById(R.id.noteTitleText);
        final EditText subTitle = (EditText)findViewById(R.id.noteSubtitleText);
        final EditText noteText = (EditText)findViewById(R.id.noteText);

        fabCreateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!readMode) {

                    final Realm realm = Realm.getDefaultInstance();
                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm bgRealm) {
                            TextNoteItem item = bgRealm.createObject(TextNoteItem.class, title.getText().toString());
                            item.setSubTitle(subTitle.getText().toString());
                            item.setUniqueID(UUID.randomUUID().toString());
                            item.setCreationTime(new Date());

                            //Commented out because Location services doesn't work in Emulator for some
                            //reason so loading random location data

                        /*if (loc != null) {
                            item.setCreationLocLat(loc.getLatitude());
                            item.setCreationLocLon(loc.getLongitude());
                        }*/
                        /*if (locText != null && !locText.isEmpty()) {
                            item.setCreationLoc(locText);
                        }*/

                            item.setCreationLoc(locs[new Random().nextInt(10) + 1]);
                            item.setNoteText(noteText.getText().toString());
                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            finish();
                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            Toast.makeText(NoteActivity.this, "Failed to save note", Toast.LENGTH_SHORT).show();
                            error.printStackTrace();
                        }
                    });
                } else {
                    final Realm realm = Realm.getDefaultInstance();
                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm bgRealm) {
                            TextNoteItem item = bgRealm.where(TextNoteItem.class).equalTo("uniqueID", uniqueID).findFirst();
                            item.setTitle(title.getText().toString());
                            item.setSubTitle(subTitle.getText().toString());
                            item.setCreationTime(new Date());
                            item.setNoteText(noteText.getText().toString());
                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            finish();
                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            Toast.makeText(NoteActivity.this, "Failed to save note", Toast.LENGTH_SHORT).show();
                            error.printStackTrace();
                        }
                    });
                }
            }
        });

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            uniqueID = extras.getString("EXTRA_UNIQUE_NOTE_ID");
            RealmQuery<TextNoteItem> query = Realm.getDefaultInstance().where(TextNoteItem.class);
            query.equalTo("uniqueID", uniqueID);
            TextNoteItem item = query.findFirst();
            title.setText(item.getTitle());
            subTitle.setText(item.getSubTitle());
            noteText.setText(item.getNoteText());
            readMode = true;
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
