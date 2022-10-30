package com.SecretDelta.delta.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.SecretDelta.delta.Activities.EditEvent;
import com.SecretDelta.delta.Models.CalEventModel;
import com.SecretDelta.delta.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventRecyclerViewHolder> {

    private ArrayList<CalEventModel> eventList;
    private Context context;

    public EventAdapter(Context context, ArrayList<CalEventModel> eventList) {
        this.context = context;
        this.eventList = eventList;
    }


    @NonNull
    @Override
    public EventRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_event, parent, false);
        return new EventRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventRecyclerViewHolder holder, int position) {
        final CalEventModel eveModel = eventList.get(position);
        final String event = eveModel.getEventName();
        final String startTime = eveModel.getHourOfDayFrom() + ":" + eveModel.getMinuteFrom();
        final String endTime = eveModel.getHourOfDayTo() + ":" + eveModel.getMinuteTo();

        holder.eventName.setText(event);
        holder.startTime.setText(startTime);
        holder.endTime.setText(endTime);

//        holder.eventRecycler.setHasFixedSize(true);
//        holder.eventRecycler.setLayoutManager(new LinearLayoutManager(context,
//                LinearLayoutManager.VERTICAL, false));
//
//        holder.eventRecycler.setNestedScrollingEnabled(false);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eveId = eveModel.getId();
                String eveName = eveModel.getEventName();
                String eveDes = eveModel.getDescription();
                int eveYear = eveModel.getYear();
                int eveMonth = eveModel.getMonth();
                int eveDay = eveModel.getDay();
                int eveHourOfDayFrom = eveModel.getHourOfDayFrom();
                int eveMinuteFrom = eveModel.getMinuteFrom();
                int eveHourOfDayTo = eveModel.getHourOfDayTo();
                int eveMinuteTo = eveModel.getMinuteTo();

                Context context = view.getContext();
                Intent intent = new Intent(context, EditEvent.class);
                intent.putExtra("eveId", eveId);
                intent.putExtra("eveName", eveName);
                intent.putExtra("eveDes", eveDes);
                intent.putExtra("eveYear", eveYear);
                intent.putExtra("eveMonth", eveMonth);
                intent.putExtra("eveDay", eveDay);
                intent.putExtra("eveHourOfDayFrom", eveHourOfDayFrom);
                intent.putExtra("eveMinuteFrom", eveMinuteFrom);
                intent.putExtra("eveHourOfDayTo", eveHourOfDayTo);
                intent.putExtra("eveMinuteTo", eveMinuteTo);

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }


    @SuppressLint("NonConstantResourceId")
    static class EventRecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.eventName)
        TextView eventName;

        @BindView(R.id.startTime)
        TextView startTime;

        @BindView(R.id.endTime)
        TextView endTime;

        public EventRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
