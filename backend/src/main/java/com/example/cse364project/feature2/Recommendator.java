package com.example.cse364project.feature2;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.cse364project.domain.Actor;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

@Service
public class Recommendator {
    // logger
    // private static final Logger log = LoggerFactory.getLogger(Recommendator.class);

    /**
     * Recommend some actors that has higher cosine similarity with request
     * Receive a Request domain and list of all actors in DB, and the max size that this recommendator recommands
     *
     * @param genre vectorized genre that represent movie that want to recommended
     * @param supporters supporting actors
     * @param synergy 0 ~ 100 int that represents rate of supporting vector (0 ~ 0.5)
     * @param plot movie's plot, future task
     * @param actors list of all Actor in DB
     * @param maxSize max size that recommendator recommends
     * @return recommend max size of actors
     */
    public Set<Actor> recommend(double[] genre, List<Actor> supporters, int synergy, String plot, List<Actor> actors, int maxSize) {

        // make a unitvector using genre vector
        RealVector vector = new ArrayRealVector(genre);
        vector.unitVector();

        // if there exist supporting vector make a new vector using Request domain's vector, supporting actor
        if (supporters.size() > 0) {

            RealVector supportingVector= new ArrayRealVector(supporters.get(0).getGenre());

            for (int i = 1; i < supporters.size(); i++) {
                supportingVector.add(new ArrayRealVector(supporters.get(i).getGenre()));
            }

            // make supporting vector unit
            supportingVector.unitVector();

            // percentage of how the suppoter effects to main vector
            // synerge : 0~100 int
            // maximun rate of supporting vector : 0.5 (each vector has same rate)
            vector.mapMultiply(1-synergy/200);
            supportingVector.mapMultiply(synergy/200);

            // combind main vector and supporting vector
            vector.add(supportingVector);
        }

        Map<Actor, Double> scores = new HashMap<>(); // map that has actors and its cosine similiarity
        CosineSimilarity calculator = new CosineSimilarity(); // calculator for cosine similiarity

        for (Actor actor : actors) {
            double similarity = calculator.cosineSimilarity(actor.getGenre(), vector.toArray());

            // process for maintaing top 5 actors that has higher similarity
            if (scores.size() < maxSize) scores.put(actor, similarity);
            else {
                // find the entry that has smallest similarity
                Map.Entry<Actor, Double> minEntry = scores.entrySet()
                    .stream()
                    .min(Comparator.comparing(Map.Entry::getValue))
                    .orElseThrow(() -> new IllegalArgumentException("Map is empty"));
                
                // change entry that has smallest similarity with new entry
                if (similarity > minEntry.getValue()) {
                    scores.remove(minEntry.getKey());
                    scores.put(actor, similarity);   
                }
            }
        }
        // log to lookup similarity
        // log.info(scores.values().toString());

        return scores.keySet();
    }

}


