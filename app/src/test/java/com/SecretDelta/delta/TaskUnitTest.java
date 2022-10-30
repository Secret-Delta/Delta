package com.SecretDelta.delta;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TaskUnitTest {

    // Test case for Task
    @Test
    public void Task_Check() {
        String input1 = "Watch the tutorial";
        String input2 = "";

        assertEquals(true, validateTask(input1));
        assertEquals(false, validateTask(input2));
    }

    // Test case for Sub Task
    @Test
    public void Sub_Task_Check() {
        String input1 = "Question 1";
        String input2 = "2Question";
        String input3 = "";

        assertEquals(true, validateSubTask(input1));
        assertEquals(false, validateSubTask(input2));
        assertEquals(false, validateSubTask(input3));
    }

    // Test case for task time
    @Test
    public void Task_Time_Check() {
        assertEquals(true, validateTime("0:00"));
        assertEquals(true, validateTime("5:00"));
        assertEquals(true, validateTime("11:00"));
        assertEquals(true, validateTime("18:00"));
        assertEquals(false, validateTime("7:60"));
        assertEquals(false, validateTime("2:61"));
        assertEquals(false, validateTime("14:60"));
        assertEquals(false, validateTime("15:61"));
    }

    // Test case for task date
    @Test
    public void testDateValidation() {
        assertEquals(true, validateDate("01-01-2022"));
        assertEquals(true, validateDate("30-10-2022"));
        assertEquals(true, validateDate("31-10-2100"));
        assertEquals(false, validateDate("30-10-2020"));
        assertEquals(false, validateDate("35-10-2022"));
        assertEquals(false, validateDate("10-30-2022"));
    }

    // Test case for reminder time
    @Test
    public void Reminder_Check() {
        String input1 = "05";
        String input2 = "10";
        String input3 = "A";

        assertEquals(true, validateReminder(input1));
        assertEquals(true, validateReminder(input2));
        assertEquals(false, validateReminder(input3));
    }


    public boolean checkDigit(String string) {
        // converts the string to an array
        char[] arr = string.toCharArray();

        // iterate through all the characters/elements in the array
        for (char c : arr) {
            if (Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    // Method to validate Task
    private boolean validateTask(String task) {
        return (checkDigit(task)) && (!task.isEmpty());
    }


    // Method to validate Sub Task
    public boolean validateSubTask(String subTask){
        return (checkDigit(subTask)) && (!subTask.isEmpty());
    }

    // Validation method for task time
    public boolean validateTime(String string) {
        if (string.length() == 4) {
            return string.charAt(1) == ':' && Integer.parseInt((Character.toString(string.charAt(0)))) > -1 && Integer.parseInt(string.substring(2)) > -1 && Integer.parseInt(string.substring(2)) < 60;
        } else if (string.length() == 5) {
            return string.charAt(2) == ':' && Integer.parseInt(string.substring(0, 2)) > -1 && Integer.parseInt(string.substring(0, 2)) < 24 && Integer.parseInt(string.substring(3)) > -1 && Integer.parseInt(string.substring(3)) < 60;
        } else {
            return false;
        }
    }

    // Validation method for task date
    public boolean validateDate(String date) {
        if (date.length() == 10) {
            if ((date.charAt(2) == '-' && date.charAt(5) == '-') || (date.charAt(2) == '/' && date.charAt(5) == '/')) {
                return Integer.parseInt(date.substring(0, 2)) > 0 && Integer.parseInt(date.substring(0, 2)) < 32 && Integer.parseInt(date.substring(3, 5)) > 0 && Integer.parseInt(date.substring(3, 5)) < 13 && Integer.parseInt(date.substring(6, 10)) > 2021 && Integer.parseInt(date.substring(6, 8)) < 10000;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    // Validation method for task reminder
    public boolean validateReminder(String reminder) {
        int isTaskValid = Integer.parseInt(reminder);
        return isTaskValid > 0 && isTaskValid <= 60;
    }
}