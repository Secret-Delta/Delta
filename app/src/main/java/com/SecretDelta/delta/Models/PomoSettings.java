package com.SecretDelta.delta.Models;

public class PomoSettings {

    private String pomodoroLength;
    private String BreakLength;

    public PomoSettings() {
    }

    public String getPomodoroLength() {
        return pomodoroLength;
    }

    public void setPomodoroLength(String pomodoroLength) {
        this.pomodoroLength = pomodoroLength;
    }

    public String getShortBreakLength() {
        return BreakLength;
    }

    public void setShortBreakLength(String BreakLength) {
            this.BreakLength = BreakLength;
        }

}
