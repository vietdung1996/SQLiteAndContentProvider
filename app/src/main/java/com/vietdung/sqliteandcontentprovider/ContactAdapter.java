package com.vietdung.sqliteandcontentprovider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private List<Contact> mContacts;
    private Context mContext;
    private LayoutInflater mInflater;

    public ContactAdapter(Context context, List<Contact> contacts) {
        mContext = context;
        mContacts = contacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setTextName(mContacts.get(position));
        holder.setTextPhone(mContacts.get(position));
    }

    @Override
    public int getItemCount() {
        return mContacts == null ? 0 : mContacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextName;
        private TextView mTextPhone;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextName = itemView.findViewById(R.id.text_name);
            mTextPhone = itemView.findViewById(R.id.text_phone);
        }

        public void setTextName(Contact contact){
            mTextName.setText(contact.getName());
        }

        public void setTextPhone(Contact contact){
            mTextPhone.setText(contact.getPhone());
        }
    }
}
