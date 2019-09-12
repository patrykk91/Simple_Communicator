package pl.curlycode.simplecommunicator;

import com.google.firebase.auth.FirebaseAuth;

public class Communicator {
    public String senderName;
    public String receiverName;
    public long timestamp;

    public Communicator() {
    }

    public Communicator(String receiverName) {
        this.receiverName = receiverName;
        senderName = FirebaseAuth.getInstance().getCurrentUser().getEmail().split("@")[0];
        timestamp = System.currentTimeMillis();
    }
}
