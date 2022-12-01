package com.SecretDelta.delta.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Habit implements Parcelable {

    private int id;
    private String name;
    private String frequency;
    private boolean isChecked;
    private String goalDays;
    private String remindTime;

    public Habit() {
    }

    public Habit(String name, String frequency, boolean isChecked, String goalDays, String remindTime) {
        this.name = name;
        this.frequency = frequency;
        this.isChecked=isChecked;
        this.goalDays = goalDays;
        this.remindTime = remindTime;
    }

    protected Habit(Parcel in) {
        id = in.readInt();
        name = in.readString();
        frequency = in.readString();
        isChecked = in.readByte() != 0;
        goalDays = in.readString();
        remindTime = in.readString();
    }

    public static final Creator<Habit> CREATOR = new Creator<Habit>() {
        @Override
        public Habit createFromParcel(Parcel in) {
            return new Habit(in);
        }

        @Override
        public Habit[] newArray(int size) {
            return new Habit[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getGoalDays() {
        return goalDays;
    }

    public void setGoalDays(String goalDays) {
        this.goalDays = goalDays;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(frequency);
        dest.writeByte((byte) (isChecked ? 1 : 0));
        dest.writeString(goalDays);
        dest.writeString(remindTime);
    }
}
