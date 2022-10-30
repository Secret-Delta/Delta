package com.SecretDelta.delta.Models;

public class CalEventModel {
    private String id;
    private String eventName, description;
    private int year, month, day, hourOfDayFrom, minuteFrom, hourOfDayTo, minuteTo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHourOfDayFrom() {
        return hourOfDayFrom;
    }

    public void setHourOfDayFrom(int hourOfDayFrom) {
        this.hourOfDayFrom = hourOfDayFrom;
    }

    public int getMinuteFrom() {
        return minuteFrom;
    }

    public void setMinuteFrom(int minuteFrom) {
        this.minuteFrom = minuteFrom;
    }

    public int getHourOfDayTo() {
        return hourOfDayTo;
    }

    public void setHourOfDayTo(int hourOfDayTo) {
        this.hourOfDayTo = hourOfDayTo;
    }

    public int getMinuteTo() {
        return minuteTo;
    }

    public void setMinuteTo(int minuteTo) {
        this.minuteTo = minuteTo;
    }
}
