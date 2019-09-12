package pl.curlycode.simplecommunicator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.core.Tag;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = LoginActivity.class.getSimpleName();


    AdapterCommunicator adapterCommunicator;
    EditText editText;
    FloatingActionButton buttonSend;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSend = findViewById(R.id.buttonSend);
        editText = findViewById(R.id.editTextMessage);
        recyclerView = findViewById(R.id.recyclerCommunicator);

        adapterCommunicator = new AdapterCommunicator();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterCommunicator);
        buttonSend.setOnClickListener(this);

        adapterCommunicator.communications.add(new Communicator("testy"));
        adapterCommunicator.communications.add(new Communicator("testy"));
        adapterCommunicator.communications.add(new Communicator("testy"));
        adapterCommunicator.communications.add(new Communicator("testy"));
        adapterCommunicator.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startLoginActivity();
        }
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
    }
}
