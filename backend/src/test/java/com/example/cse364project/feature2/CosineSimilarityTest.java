package com.example.cse364project.feature2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CosineSimilarityTest {
    @Test
    void testCosineSimilarity() {
        CosineSimilarity cosineSimilarity = new CosineSimilarity();

        // Test case 1: Vectors with positive similarity
        double[] vector1 = {1.0, 2.0, 3.0};
        double[] vector2 = {2.0, 4.0, 6.0};
        double expectedSimilarity1 = 1.0;
        double actualSimilarity1 = cosineSimilarity.cosineSimilarity(vector1, vector2);
        assertEquals(expectedSimilarity1, actualSimilarity1, 0.0001);

        // Test case 2: Vectors with zero similarity
        double[] vector3 = {1.0, 0.0, 0.0};
        double[] vector4 = {0.0, 1.0, 0.0};
        double expectedSimilarity2 = 0.0;
        double actualSimilarity2 = cosineSimilarity.cosineSimilarity(vector3, vector4);
        assertEquals(expectedSimilarity2, actualSimilarity2, 0.0001);

        // Test case 3: Vectors with negative similarity
        double[] vector5 = {1.0, 2.0, 3.0};
        double[] vector6 = {-1.0, -2.0, -3.0};
        double expectedSimilarity3 = -1.0;
        double actualSimilarity3 = cosineSimilarity.cosineSimilarity(vector5, vector6);
        assertEquals(expectedSimilarity3, actualSimilarity3, 0.0001);

        // Test case 4: Vectors with 0
        double[] vector7 = {0.0, 0.0, 0.0};
        double[] vector8 = {0.0, 0.0, 0.0};
        double[] vector78 = {1.0, 1.0, 1.0};
        double expectedSimilarity4 = 0.0;
        double actualSimilarity4 = cosineSimilarity.cosineSimilarity(vector7, vector8);
        double actualSimilarity41 = cosineSimilarity.cosineSimilarity(vector78, vector8);
        double actualSimilarity42 = cosineSimilarity.cosineSimilarity(vector7, vector78);
        assertEquals(expectedSimilarity4, actualSimilarity4, 0.0001);
        assertEquals(0.0, actualSimilarity41, 0.0001);
        assertEquals(0.0, actualSimilarity42, 0.0001);

        // Test case 5: Vectors with null
        double[] vector9 = null;
        double[] vector10 = null;
        double[] vector910 = {0.0, 0.0, 0.0};
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> {
            cosineSimilarity.cosineSimilarity(vector9, vector10);
        });
        assertEquals("Vectors must not be null", exception1.getMessage());

        IllegalArgumentException exception11 = assertThrows(IllegalArgumentException.class, () -> {
            cosineSimilarity.cosineSimilarity(vector9, vector910);
        });
        assertEquals("Vectors must not be null", exception11.getMessage());
        
        IllegalArgumentException exception12 = assertThrows(IllegalArgumentException.class, () -> {
            cosineSimilarity.cosineSimilarity(vector910, vector10);
        });
        assertEquals("Vectors must not be null", exception12.getMessage());
        
        // Test case 6: Vectors with different size
        double[] vector11 = {0.0, 0.0, 0.0};
        double[] vector12 = {0.0, 0.0};
        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> {
            cosineSimilarity.cosineSimilarity(vector11, vector12);
        });
        assertEquals("Vector size must same", exception2.getMessage());
    }
}