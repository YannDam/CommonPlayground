package com.wordpress.commonplayground.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wordpress.commonplayground.R;
import com.wordpress.commonplayground.model.Session;

import java.util.ArrayList;
import java.util.List;

public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.SessionViewHolder> {

    private List<?> activeSessions;

    // Pass in the sessions array into the constructor
    public SessionsAdapter(List<?> sessions) {
        this.activeSessions = sessions;
    }

    @Override
    @NonNull
    public SessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View sessionView = inflater.inflate(R.layout.item_session, parent, false);
        return new SessionViewHolder(context, sessionView);
    }


    @Override
    public void onBindViewHolder(@NonNull SessionViewHolder viewHolder, int position) {
        // Get the data model based on position
        Session session = (Session) activeSessions.get(position);

        // Set item views based on your views and data model
        TextView titleTextView = viewHolder.titleTextView;
        titleTextView.setText(session.getTitle());
        TextView gameTextView = viewHolder.gameTextView;
        gameTextView.setText(session.getGame());
        TextView placeTextView = viewHolder.placeTextView;
        if ("offline".equals(session.getType())){
            placeTextView.setText(session.getPlace());
        }
        TextView dateTextView = viewHolder.dateTextView;
        dateTextView.setText(session.getDate());

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return activeSessions.size();
    }

    public class SessionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView titleTextView, dateTextView, gameTextView, placeTextView;
        private Context context;

        public SessionViewHolder(Context context, View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.session_title);
            gameTextView = itemView.findViewById(R.id.session_game);
            placeTextView = itemView.findViewById(R.id.session_place);
            dateTextView = itemView.findViewById(R.id.session_date);
            this.context = context;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition(); // gets item position
            long currentSession = ((Session) activeSessions.get(position)).getId();
            Log.d("ClickTest", "Id:" + currentSession);
            //Here goes opening the SessionDetails Activity, just pass the ID
            Intent openSessionDetailActivity = new Intent(context, SessionDetailActivity.class);
            Bundle b = new Bundle();
            b.putParcelableArrayList("Sessions", (ArrayList<? extends Parcelable>) activeSessions);
            openSessionDetailActivity.putExtras(b);
            openSessionDetailActivity.putExtra("Index", position);
            context.startActivity(openSessionDetailActivity);
        }
    }
}

