package io.aif.fuzzy.set;


import io.aif.fuzzy.bool.FuzzyBoolean;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

public class FuzzySetUnionTest {

    @Test
    public void testUnion() throws Exception {
        final IFuzzySet<Integer> left = mock(IFuzzySet.class);
        final IFuzzySet<Integer> right = mock(IFuzzySet.class);

        final FuzzyBoolean leftFB = new FuzzyBoolean(0.2);
        final FuzzyBoolean rightFB = new FuzzyBoolean(0.4);

        when(left.contains(1)).thenReturn(leftFB);
        when(right.contains(1)).thenReturn(rightFB);

        final IFuzzySet<Integer> unionResult = FuzzySetUnion.union(left, right);
        final FuzzyBoolean result = unionResult.contains(1);

        assertEquals(rightFB, result);
        verify(left, times(1)).contains(1);
        verify(right, times(1)).contains(1);
    }

}
