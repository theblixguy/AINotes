package com.ssrij.ainotes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.widget.Toast;

import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardProvider;
import com.dexafree.materialList.card.OnActionClickListener;
import com.dexafree.materialList.card.action.TextViewAction;
import com.dexafree.materialList.view.MaterialListView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.mauker.materialsearchview.MaterialSearchView;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity{

    Realm realm;
    MaterialSearchView searchView;
    MaterialListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        realm = Realm.getDefaultInstance();

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        mListView = (MaterialListView) findViewById(R.id.searchList);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Gson gson = new GsonBuilder()
                        .create();
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .baseUrl(LUISInterface.ENDPOINT)
                        .build();
                LUISInterface luisAPI = retrofit.create(LUISInterface.class);

                Call<LUISResponse> searchResult = luisAPI.getSearchResult("856e06f1-7628-4c18-9dd1-57472ff8faf3", "34fac67b40634d83b7ddaf423bcb1198", query);

                searchResult.enqueue(new Callback<LUISResponse>() {
                    @Override
                    public void onResponse(Call<LUISResponse> call, Response<LUISResponse> response) {

                        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy HH:mm");
                        String location = "", time = "", topic = "";

                        if (response.isSuccessful()) {
                            if (response.body().getEntities().size() > 0) {
                                for (Entity e : response.body().getEntities()) {

                                    Log.i("AINotes", "-------------------------------");
                                    Log.i("AINotes", "Entity => " + e.getEntity());
                                    Log.i("AINotes", "Type => " + e.getType());
                                    Log.i("AINotes", "Start Index => " + e.getStartIndex());
                                    Log.i("AINotes", "End Index => " + e.getEndIndex());
                                    Log.i("AINotes", "Score => " + e.getScore());
                                    Log.i("AINotes", "-------------------------------");

                                    if (e.getType().equals("Location")) {
                                        location = e.getEntity();
                                    } else if (e.getType().equals("Topic")) {
                                        topic = e.getEntity();
                                    } else if (e.getType().equals("Time")) {
                                        time = e.getEntity();
                                    }

                                }

                                RealmQuery<TextNoteItem> query = realm.where(TextNoteItem.class);
                                if (!topic.equals("")) {
                                    query.contains("title", topic, Case.INSENSITIVE);
                                }
                                if (!location.equals("")) {
                                    query.contains("creationLoc", location, Case.INSENSITIVE);
                                }
                                if (!time.equals("")) {
                                    Log.i("AINotes", new Date().toString());
                                    query.between("creationTime", parseDateToken(time), new Date());
                                }
                                RealmResults<TextNoteItem> items = query.findAll();

                                if (items.size() > 0) {
                                    for (TextNoteItem item : items) {
                                        Log.i("AINotes", "Item created at " + item.getCreationTime().toString());
                                        Card card = new Card.Builder(SearchActivity.this)
                                                .withProvider(new CardProvider())
                                                .setLayout(R.layout.material_basic_buttons_card)
                                                .setTitle(item.getTitle())
                                                .setDescription(item.getNoteText().length() > 160 ? item.getNoteText().substring(0, 160) + "..." : item.getNoteText())
                                                .addAction(R.id.left_text_button, new TextViewAction(SearchActivity.this)
                                                        .setText(DATE_FORMAT.format(item.getCreationTime()))
                                                        .setTextResourceColor(R.color.black_button)
                                                        .setListener(new OnActionClickListener() {
                                                            @Override
                                                            public void onActionClicked(View view, Card card) {
                                                                Toast.makeText(getApplicationContext(), "To do", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }))
                                                .addAction(R.id.right_text_button, new TextViewAction(SearchActivity.this)
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
                                }

                            } else {
                                Log.i("AINotes", "There were no entities");
                            }
                        } else {
                            Log.i("AINotes", "Response was not successful");
                        }
                    }

                    @Override
                    public void onFailure(Call<LUISResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.post(new Runnable(){

            @Override
            public void run()
            {
                searchView.openSearch();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                    BaseInputConnection mInputConnection = new BaseInputConnection(searchView, true);
                    mInputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                    mInputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER));
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static Date parseDateToken(String token) {
        Date date = null;
        if (token.equalsIgnoreCase("yesterday") || token.equalsIgnoreCase("last day")) {
            date = new DateTime().minusDays(1).toDate();
        }
        else if (token.equalsIgnoreCase("one week ago") || token.equalsIgnoreCase("last week") || token.equalsIgnoreCase("a week ago")) {
            date = new DateTime().minusWeeks(1).toDate();
        }
        else if (token.equalsIgnoreCase("two weeks ago") || token.equalsIgnoreCase("two weeks")) {
            date = new DateTime().minusWeeks(2).toDate();
        }
        else if (token.equalsIgnoreCase("three weeks ago") || token.equalsIgnoreCase("three weeks")) {
            date = new DateTime().minusWeeks(3).toDate();
        }
        else if (token.equalsIgnoreCase("one month ago") || token.equalsIgnoreCase("last month")) {
            date = new DateTime().minusMonths(1).toDate();
        }
        else if (token.equalsIgnoreCase("two months ago") || token.equalsIgnoreCase("two months")) {
            date = new DateTime().minusMonths(2).toDate();
        }
        else if (token.equalsIgnoreCase("two years ago") || token.equalsIgnoreCase("two years")) {
            date = new DateTime().minusYears(2).toDate();
        }
        else if (token.equalsIgnoreCase("year ago") || token.equalsIgnoreCase("a year ago") || token.equalsIgnoreCase("one year ago")) {
            date = new DateTime().minusYears(1).toDate();
        }
        Log.i("AINotes", date.toString());
        return date;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
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
