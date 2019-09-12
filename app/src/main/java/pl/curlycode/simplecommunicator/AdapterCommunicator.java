package pl.curlycode.simplecommunicator;

import android.service.autofill.TextValueSanitizer;
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
        holder.textTitle.setText(communicator.senderName);
    }

    @Override
    public int getItemCount() {
        return communications.size();
    }

    public class CommunicationHolder extends RecyclerView.ViewHolder {
        public TextView textTitle;


        public CommunicationHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitleCommunication);
        }
    }
}
