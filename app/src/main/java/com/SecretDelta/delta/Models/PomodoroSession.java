package com.SecretDelta.delta.Models;

public class PomodoroSession {
    private long focusTime;
    private String date;
    private String time;
    private String focus;

    public PomodoroSession() {

    }

    public long getFocusTime() {
        return focusTime;
    }

    public void setFocusTime(long focusTime) {
        this.focusTime = focusTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimes() {
        return time;
    }

    public void setTimes(String time) {
        this.time = time;
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }
}
