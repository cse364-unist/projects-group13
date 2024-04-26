
package com.example.cse364project.feature2;

public class CosineSimilarity {

    /**
     * Calculates the cosine similarity for two given vectors.
     *
     * @param leftVector left vector
     * @param rightVector right vector
     * @return cosine similarity between the two vectors
     */
    public Double cosineSimilarity(final double[] leftVector, final double[] rightVector) {
        if (leftVector == null || rightVector == null) {
            throw new IllegalArgumentException("Vectors must not be null");
        }
        if (leftVector.length != rightVector.length) {
            throw new IllegalArgumentException("Vector size must same");
        }
        final double dotProduct = dot(leftVector, rightVector);

        double d1 = vectorNorm(leftVector);
        double d2 = vectorNorm(rightVector);

        double cosineSimilarity;
        if (d1 <= 0.0 || d2 <= 0.0) {
            cosineSimilarity = 0.0;
        } else {
            cosineSimilarity = dotProduct /(d1 *d2);
        }
        return cosineSimilarity;
    }

    /**
     * Computes the dot product of two vectors. 
     *
     * @param leftVector left vector
     * @param rightVector right vector
     * @return the dot product
     */
    private double dot(final double[] leftVector, final double[] rightVector) {
        long dotProduct = 0;

        for (int i = 0; i < leftVector.length; i++) {
            dotProduct += leftVector[i] * rightVector[i];
        }
        return dotProduct;
    }
    /**
     * Computes the vector norm. 
     *
     * @param vector vector to calculate
     * @return the vector norm
     */
    private double vectorNorm(double[] vector) {
        double sum = 0;
        for (double value : vector) {
            sum += value * value;
        }
        return Math.sqrt(sum);
    }
}
