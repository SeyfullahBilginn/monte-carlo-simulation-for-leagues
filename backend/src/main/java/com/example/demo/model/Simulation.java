package com.example.demo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

public class Simulation {
    private List<Match> matches;
    private List<Team> teams;
    private List<Integer> results;
    private Map<String, Integer> percentage;
    private int numOfWeek;
    final int NUM_OF_ITERATION = 100;

    public Simulation(List<Match> matches, List<Team> teams, int numOfWeek) {
        this.matches = matches;
        this.teams = teams;
        this.numOfWeek = numOfWeek;
        this.results = new ArrayList<>();
        this.percentage = new HashMap<>();
    };

    public Map<String, Integer> iterate() {
        for (int iteration = 0; iteration < NUM_OF_ITERATION; iteration++) {
            int add = perIteration();
            results.add(add);
        }

        for (int i = 0; i < teams.size(); i++) {
            percentage.put(teams.get(i).getTeamName(), Collections.frequency(results, i) * 100 / NUM_OF_ITERATION);
        }

        return percentage;
    }

    public int perIteration() {
        List<Double> iterationResult = new ArrayList<>();
        for (Team team : teams) {
            double total = team.getPoints();
            double mean = calculateMean(team);
            double std = calculateStd(team);
            for (int i = 0; i < 6 - numOfWeek; i++) {
                double randomResult = generateRandomPoint(mean, std);
                total += randomResult;
            }
            iterationResult.add(total);
        }
        int index = IntStream.range(0, iterationResult.size()).boxed()
                .max(Comparator.comparing(iterationResult::get)).orElse(-1);
        return index;
    }

    public double calculateMean(Team team) {
        return team.getPoints() / numOfWeek;
    }

    public double calculateStd(Team team) {
        double total = 0;
        double mean = calculateMean(team);
        for (Match match : matches) {
            if (team.getTeamId() == match.getHomeTeamId()) {
                if (match.getHomeTeamGoal() > match.getAwayTeamGoal()) {
                    total += Math.pow(3 - mean, 2);
                } else if (match.getHomeTeamGoal() == match.getAwayTeamGoal()) {
                    total += Math.pow(1 - mean, 2);
                } else {
                    total += Math.pow(0 - mean, 2);
                }
            } else if (team.getTeamId() == match.getAwayTeamId()) {
                if (match.getAwayTeamGoal() > match.getHomeTeamGoal()) {
                    total += Math.pow(3 - mean, 2);
                } else if (match.getHomeTeamGoal() == match.getAwayTeamGoal()) {
                    total += Math.pow(1 - mean, 2);
                } else {
                    total += Math.pow(0 - mean, 2);
                }
            }
        }
        return Math.sqrt(total / numOfWeek);
    }

    public double generateRandomPoint(double mean, double std) {
        double val = 0;
        Random r = new Random();
        val += r.nextGaussian() * std + mean;
        return val;
    }

}
