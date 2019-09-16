package pl.curlycode.simplecommunicator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterCommunicator extends RecyclerView.Adapter<AdapterCommunicator.CommunicationHolder> {
    public ArrayList<Communicator> communications = new ArrayList<>();

    @NonNull
    @Override
    public CommunicationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_communication,parent,false);
        return new CommunicationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunicationHolder holder, int position) {
        Communicator communicator = communications.get(position);
        holder.textUser.setText(communicator.senderName);
        holder.textMessage.setText(communicator.message);
    }

    @Override
    public int getItemCount() {
        return communications.size();
    }

    public class CommunicationHolder extends RecyclerView.ViewHolder {
        public TextView textUser;
        public TextView textMessage;

        public CommunicationHolder(@NonNull View itemView) {
            super(itemView);
            textUser = itemView.findViewById(R.id.textUserCommunication);
            textMessage = itemView.findViewById(R.id.textMessageCommunication);

        }
    }
}
