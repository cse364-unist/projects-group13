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
    }
}