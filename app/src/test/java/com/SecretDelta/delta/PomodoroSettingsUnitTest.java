package com.SecretDelta.delta;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PomodoroSettingsUnitTest {

    // test case for Focus Duration
    @Test
    public void Focus_Check() {
        int input1 = 25;
        int input2 = 0;
        int input3 = -25;
        int input4 = 70;

        assertEquals(true, validateFocus(input1));
        assertEquals(false, validateFocus(input2));
        assertEquals(false, validateFocus(input3));
        assertEquals(false, validateFocus(input4));
    }

    private boolean validateFocus(int input1) {
        return (input1 > 0 && input1 <= 60);
    }

    // test case for Short Break Duration
    @Test
    public void Short_Break_Check() {
        int input1 = 5;
        int input2 = 0;
        int input3 = -5;
        int input4 = 70;

        assertEquals(true, validateShortBreak(input1));
        assertEquals(false, validateShortBreak(input2));
        assertEquals(false, validateShortBreak(input3));
        assertEquals(false, validateShortBreak(input4));
    }

    private boolean validateShortBreak(int input1) {
        return (input1 > 0 && input1 <= 60);
    }




}
