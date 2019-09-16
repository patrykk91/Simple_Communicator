package pl.curlycode.simplecommunicator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ChildEventListener {
    private static final String TAG = LoginActivity.class.getSimpleName();


    AdapterCommunicator adapterCommunicator;
    EditText editText;
    EditText editTextMessage;
    TextView localUserTextView;
    FloatingActionButton buttonSend;
    RecyclerView recyclerView;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSend = findViewById(R.id.buttonSend);
        editText = findViewById(R.id.editTextSend);
        editTextMessage = findViewById(R.id.editTextMessageContent);
        localUserTextView = findViewById(R.id.localUser);
        recyclerView = findViewById(R.id.recyclerCommunicator);

        adapterCommunicator = new AdapterCommunicator();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterCommunicator);
        buttonSend.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startLoginActivity();
        } else {
            localUserTextView.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail().split("@")[0]);
            reference = FirebaseDatabase.getInstance().getReference("communications").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().split("@")[0]);
            reference.addChildEventListener(this);
        }
    }

    @Override
    protected void onPause() {
        if (reference != null) {
            reference.removeEventListener(this);
        }
        super.onPause();
    }

    private void startLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        MainActivity.this.startActivity(intent);
        MainActivity.this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startLoginActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: ");
        String receiver = editText.getText().toString().trim();
        String message = editTextMessage.getText().toString();

        if (receiver.length() > 0) {
            Intent serviceIntent = new Intent(MainActivity.this, CommunicationService.class);
            serviceIntent.putExtra("receiver", receiver);
            serviceIntent.putExtra("message", message);

            startService(serviceIntent);
        }
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        Log.d(TAG, "onChildAdded: ");
        if (adapterCommunicator != null) {
            Communicator communicator = dataSnapshot.getValue(Communicator.class);
            adapterCommunicator.communications.add(communicator);
            adapterCommunicator.notifyDataSetChanged();
        }
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        Log.d(TAG, "onChildChanged: ");
    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
        Log.d(TAG, "onChildRemoved: ");
    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        Log.d(TAG, "onChildMoved: ");
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
