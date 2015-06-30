package io.aif.fuzzy.bool;

import org.junit.Test;

import static org.junit.Assert.*;

public class FuzzyBooleanTest {

    @Test
    public void testConstructionWithValue() throws Exception {
        final FuzzyBoolean fb = new FuzzyBoolean(0.4);
        assertTrue(fb.getValue() == 0.4);
    }

    @Test
    public void testConstructionWithValueAndThreshold() throws Exception {
        final FuzzyBoolean fb = new FuzzyBoolean(0.4, 0.3);
        assertTrue(fb.getValue() == 0.4);
        assertTrue(fb.getThreshold() == 0.3);
        assertTrue(fb.isTrue());
    }

    @Test
    public void testEqualsAndHashCode() throws Exception {
        final FuzzyBoolean fb = new FuzzyBoolean(0.4, 0.3);
        final FuzzyBoolean fb2 = new FuzzyBoolean(0.4, 0.3);
        assertTrue(fb.equals(fb2));
        assertEquals(fb.hashCode(), fb2.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeFailsByThreshold() throws Exception {
        final FuzzyBoolean fb = new FuzzyBoolean(0.4, 0.2);
        final FuzzyBoolean fb2 = new FuzzyBoolean(0.4, 0.3);
        assertFalse(fb.equals(fb2));
        assertNotEquals(fb.hashCode(), fb2.hashCode());
    }

    @Test
    public void testEqualsHashCodeFailsByValue() throws Exception {
        final FuzzyBoolean fb = new FuzzyBoolean(0.5, 0.3);
        final FuzzyBoolean fb2 = new FuzzyBoolean(0.4, 0.3);
        assertFalse(fb.equals(fb2));
        assertNotEquals(fb.hashCode(), fb2.hashCode());
    }

    @Test
    public void testIsTrue() throws Exception {
        final FuzzyBoolean fb = new FuzzyBoolean(0.5, 0.3);
        assertTrue(fb.isTrue());
    }

    @Test
    public void testIsTrueWhenValueAndThresholdAreEqual() throws Exception {
        final FuzzyBoolean fb = new FuzzyBoolean(0.3, 0.3);
        assertTrue(fb.isTrue());
    }

    @Test
    public void testIsTrueFail() throws Exception {
        final FuzzyBoolean fb = new FuzzyBoolean(0.2, 0.3);
        assertFalse(fb.isTrue());
    }

}