package io.aif.associations.calculators.vertex;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompositeWeightCalculatorTest {

    @Test
    public void testWeightCalculator() throws Exception {
        final IVertexWeightCalculator<Integer> vertexWeightCalculator1 = mock(IVertexWeightCalculator.class);
        when(vertexWeightCalculator1.calculate(1)).thenReturn(.0);
        when(vertexWeightCalculator1.calculate(2)).thenReturn(1.0);

        final IVertexWeightCalculator<Integer> vertexWeightCalculator2 = mock(IVertexWeightCalculator.class);
        when(vertexWeightCalculator2.calculate(1)).thenReturn(1.0);
        when(vertexWeightCalculator2.calculate(2)).thenReturn(.0);

        final Map<IVertexWeightCalculator<Integer>, Double> calculators = new HashMap<>();
        calculators.put(vertexWeightCalculator1, .5);
        calculators.put(vertexWeightCalculator2, 1.);

        final CompositeWeightCalculator<Integer> compositeWeightCalculator = new CompositeWeightCalculator<>(calculators);
        final Double actualWeight1 = compositeWeightCalculator.calculate(1);
        final Double actualWeight2 = compositeWeightCalculator.calculate(2);

        assertEquals(actualWeight1, (Double).5);
        assertEquals(actualWeight2, (Double).25);
    }

}