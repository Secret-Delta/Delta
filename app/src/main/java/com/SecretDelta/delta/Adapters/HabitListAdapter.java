package com.SecretDelta.delta.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.SecretDelta.delta.Interfaces.StartActivityInterface;
import com.SecretDelta.delta.Models.Habit;
import com.SecretDelta.delta.R;
import com.SecretDelta.delta.ViewHolders.HabitListViewHolder;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HabitListAdapter extends RecyclerView.Adapter<HabitListViewHolder> {

    private ArrayList<Habit> habitsList;
    private String frequencyType;
    private Context context;
    private StartActivityInterface startActivityInterface;

    public HabitListAdapter(ArrayList<Habit> habitsList, Context context, StartActivityInterface startActivityInterface) {
        this.frequencyType=frequencyType;
        this.context=context;
        this.habitsList=habitsList;
        this.startActivityInterface=startActivityInterface;
    }

    @NonNull
    @Override
    public HabitListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.habit_list_item_cell,parent,false);

        HabitListViewHolder habitListViewHolder=new HabitListViewHolder(view, context,startActivityInterface);
//
//        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference=firebaseDatabase.getReference().child("Habits");
//        databaseReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                Habit habit;
//                habit=snapshot.getValue(Habit.class);
//                System.err.println(snapshot.getValue().toString());
//                if(snapshot.getChildren()!=null)
//                    Toast.makeText(context,"DataExists",Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(context,"No data",Toast.LENGTH_SHORT).show();
//
//                addHabitToSpecificList(habit);
//                notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//                notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                notifyDataSetChanged();
//
//            }
//        });

//        loadDataFromFirebase();

        return habitListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HabitListViewHolder holder, int position) {

        holder.bind(habitsList.get(position));
    }

    private void loadDataFromFirebase(){
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference().child("Habits");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Habit habit;
                habit=(snapshot.getValue(Habit.class));
                if(snapshot.getChildren()!=null)
                    Toast.makeText(context,"DataExists",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context,"No data",Toast.LENGTH_SHORT).show();

                addHabitToSpecificList(habit);
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                notifyDataSetChanged();

            }
        });

    }

    @Override
    public int getItemCount() {
        return habitsList.size();
    }

    private void addHabitToSpecificList(Habit habit){

        if (habit.getFrequency().equals(frequencyType))
            habitsList.add(habit);

    }

}
