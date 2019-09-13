package pl.curlycode.simplecommunicator;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CommunicationService extends Service {
    private static final String TAG = CommunicationService.class.getSimpleName();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!intent.hasExtra("receiver")) {
            return super.onStartCommand(intent, flags, startId);
        }
        final String receiver = intent.getStringExtra("receiver");
        FirebaseDatabase.getInstance().getReference("users").child(receiver).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: ");
                if (dataSnapshot.exists()) {
                    FirebaseDatabase.getInstance().getReference("communications").child(receiver).push().setValue(new Communicator(receiver));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: ");
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
