package ch.zli.pg.app.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.zli.pg.app.data.Contact;
import ch.zli.pg.contactscan.R;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private final List<Contact> contacts;
    private final Context context;

    public ContactAdapter(List<Contact> contacts, Context context) {
        this.contacts = contacts;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.labelName.setText(contact.getName());
        holder.labelNumber.setText(contact.getNumber());
        holder.buttonSelect.setOnClickListener(v -> {
            Intent intent = new Intent(context, ShareView.class);
            intent.putExtra("contact_id", contact.id);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    protected static class ContactViewHolder extends RecyclerView.ViewHolder {
        private final TextView labelName;
        private final TextView labelNumber;
        private final Button buttonSelect;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            labelName = itemView.findViewById(R.id.labelName);
            labelNumber = itemView.findViewById(R.id.labelNumber);
            buttonSelect = itemView.findViewById(R.id.buttonSelect);
        }
    }


}