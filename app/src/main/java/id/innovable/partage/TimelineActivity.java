package id.innovable.partage;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.innovable.partage.Helper.googleAuth;

public class TimelineActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Intent login;
    private googleAuth ceksesi;
    FloatingActionButton tambah;
    Button submitisi;
    SwipeRefreshLayout refresh;



    private static final String TAG = TimelineActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerViewAdapter recyclerViewAdapter;
    private EditText addTaskBox;
    private DatabaseReference databaseReference;
    private List<Content> allContent;
    private List<Content> cacheContent;
    private boolean firstState = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        ceksesi = new googleAuth();
        login = new Intent(TimelineActivity.this,LoginActivity.class);
        refresh = (SwipeRefreshLayout) findViewById(R.id.timeline_refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (cacheContent.isEmpty()){
                    refresh.setRefreshing(false);
                }else{
                    updateAllTask();
                }
            }
        });


        if (mAuth.getInstance().getCurrentUser() != null){
            Toast.makeText(TimelineActivity.this,"Session success",Toast.LENGTH_SHORT);
            Log.d("Session","Success");
        }else {
            Toast.makeText(TimelineActivity.this,"Session failed",Toast.LENGTH_SHORT);
            Log.d("Session","Failed");
        }

        allContent = new ArrayList<Content>();
        cacheContent = new ArrayList<Content>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Timeline");
        recyclerView = (RecyclerView) this.findViewById(R.id.task_list);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        tambah = (FloatingActionButton)findViewById(R.id.btn_tambah);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                databaseReference.setValue("Di cobian kang "+Math.random());
                final Dialog dialog = new Dialog(TimelineActivity.this);
                dialog.setContentView(R.layout.formisian);
                submitisi = (Button)dialog.findViewById(R.id.btn_formisi);
                submitisi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        long unixTime = System.currentTimeMillis() / 1000L;

                        EditText content = (EditText) dialog.findViewById(R.id.edit_formisi);
                        Map<String, Content> timeline = new HashMap<String, Content>();
                        Content singleContent = new Content(String.valueOf(content.getText()),String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid()),String.valueOf(unixTime));
//                        String key = databaseReference.push().getKey();
                        timeline.put("Content", singleContent);

                        databaseReference.push().setValue(timeline);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });



        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                saveCache(dataSnapshot);
                if (firstState){
                    updateAllTask();
                    firstState = false;
                }
                Toast toast = new Toast(TimelineActivity.this);
                toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
                toast.makeText(TimelineActivity.this,"New Posts",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void logout() {
        try {
            Log.d("sesi",String.valueOf(mAuth.getInstance().getCurrentUser().getEmail()));
            mAuth.getInstance().signOut();
            startActivity(login);
        }catch (Exception e){
            Log.d("sesi",e.getMessage());
        }
    }

    private void updateAllTask(){

        allContent.addAll(cacheContent);
        cacheContent.clear();

        Collections.sort(allContent, new Comparator<Content>(){
            public int compare(Content obj1, Content obj2) {
                return obj2.getTimestamp().compareTo(obj1.getTimestamp());
            }
        });

        recyclerViewAdapter = new RecyclerViewAdapter(TimelineActivity.this, allContent);
        recyclerView.setAdapter(recyclerViewAdapter);
        refresh.setRefreshing(false);
    }

    private void saveCache(DataSnapshot dataSnapshot){
        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
            Content newContent = singleSnapshot.getValue(Content.class);
            cacheContent.add(newContent);

        }
    }
}
