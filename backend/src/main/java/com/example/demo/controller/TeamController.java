package com.example.demo.controller;

import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourseNotFoundException;
import com.example.demo.model.Match;
import com.example.demo.model.Simulation;
import com.example.demo.model.Team;
import com.example.demo.repository.MatchRepository;
import com.example.demo.repository.TeamRepository;

@CrossOrigin(origins = "http://localhost:3000")
@Validated
@RestController
@RequestMapping("/api/V1/")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MatchRepository matchRepository;

    // static fixture guarantees each team will play
    // each other 2 times (as home team and away team)
    // it can be generated randomly in the next version
    public Map<Integer, List<Integer>> fixture = new HashMap<Integer, List<Integer>>() {
        {
            put(1, Arrays.asList(0, 1, 2, 3));
            put(2, Arrays.asList(0, 2, 1, 3));
            put(3, Arrays.asList(0, 3, 1, 2));
            put(4, Arrays.asList(1, 0, 3, 2));
            put(5, Arrays.asList(2, 0, 3, 1));
            put(6, Arrays.asList(3, 0, 2, 1));
        }
    };

    // get all teams
    @GetMapping("/teams")
    public List<Team> getAllTeams() {
        // sort by score in descending order
        return teamRepository.findAll(Sort.by(Sort.Direction.DESC, "points"));
    }

    // create and save team
    @PostMapping("/teams")
    public Team addInitialTeam(@RequestBody Team addedTeam) throws NoSuchProviderException {
        return teamRepository.save(addedTeam);
    }

    // resets all database
    @DeleteMapping("/teams")
    public void deleteDatabase() {
        teamRepository.deleteAll();
        matchRepository.deleteAll();
    }

    // plays next week's fixture with the random scores
    @PostMapping("/playNextWeekRandomly/{numOfWeek}")
    public List<Match> playNextWeek(@PathVariable int numOfWeek) {

        System.out.println("numOfWeek: " + numOfWeek);
        List<Integer> matches = fixture.get(numOfWeek);
        System.out.println(matches);
        System.out.println("get: " + matches.get(1));
        List<Team> teams = teamRepository.findAll();

        // first matches in that week
        Team firstHomeTeam = teams.get(matches.get(0));
        Team firstAwayTeam = teams.get(matches.get(1));

        List<Match> results = new ArrayList<>();

        Match firstMatch = new Match(
                firstHomeTeam.getTeamName(),
                firstAwayTeam.getTeamName(),
                firstHomeTeam.getTeamId(),
                firstAwayTeam.getTeamId(),
                (int) (Math.random() * (4 - 0)) + 0,
                (int) (Math.random() * (4 - 0)) + 0);

        doMatches(firstMatch);

        // second matches in that week
        Team secondHomeTeam = teams.get(matches.get(2));
        Team secondAwayTeam = teams.get(matches.get(3));

        Match secondMatch = new Match(
                secondHomeTeam.getTeamName(),
                secondAwayTeam.getTeamName(),
                secondHomeTeam.getTeamId(),
                secondAwayTeam.getTeamId(),
                (int) (Math.random() * (4 - 0)) + 0,
                (int) (Math.random() * (4 - 0)) + 0);
        doMatches(secondMatch);

        // add matches to array in order to see in ui in a proper format
        results.add(firstMatch);
        results.add(secondMatch);

        // save matches to db
        matchRepository.save(firstMatch);
        matchRepository.save(secondMatch);

        return results;
    }

    // simulates left matches based on each team's pts they have already earned
    @GetMapping("/teams/simulate/{numOfWeek}")
    public Map<String, Integer> simulate(@PathVariable int numOfWeek) {

        List<Match> matches = matchRepository.findAll();
        List<Team> teams = teamRepository.findAll();

        Simulation simulation = new Simulation(matches, teams, numOfWeek);
        return simulation.iterate();
    }

    // do nth week matches with the random scores
    public void doMatches(Match match) {
        Team homeTeam = teamRepository.findById(match.getHomeTeamId())
                .orElseThrow(() -> new ResourseNotFoundException("team Not Found"));
        Team awayTeam = teamRepository.findById(match.getAwayTeamId())
                .orElseThrow(() -> new ResourseNotFoundException("team Not Found"));

        int homeTeamGoal = match.getHomeTeamGoal();
        int awayTeamGoal = match.getAwayTeamGoal();

        // set average of teams
        homeTeam.setAverage(homeTeam.getAverage() + homeTeamGoal - awayTeamGoal);
        awayTeam.setAverage(awayTeam.getAverage() + awayTeamGoal - homeTeamGoal);

        // set goals of teams
        homeTeam.setNumOfGoalsFor(homeTeam.getNumOfGoalsFor() + homeTeamGoal);
        homeTeam.setNumOfGoalsAgainst(homeTeam.getNumOfGoalsAgainst() + awayTeamGoal);

        awayTeam.setNumOfGoalsFor(awayTeam.getNumOfGoalsFor() + awayTeamGoal);
        awayTeam.setNumOfGoalsAgainst(awayTeam.getNumOfGoalsAgainst() + homeTeamGoal);

        // set number of played matches
        homeTeam.setNumOfPlayed(homeTeam.getNumOfPlayed() + 1);
        awayTeam.setNumOfPlayed(awayTeam.getNumOfPlayed() + 1);

        /*
         * distribute points
         * win => 3 pts
         * draw => 1 pts
         * lose => 0 pts
         */
        if (homeTeamGoal > awayTeamGoal) {
            homeTeam.setPoints(homeTeam.getPoints() + 3);
            homeTeam.setNumOfWon(homeTeam.getNumOfWon() + 1);
            awayTeam.setNumOfLost(awayTeam.getNumOfLost() + 1);
        } else if (homeTeamGoal < awayTeamGoal) {
            awayTeam.setPoints(awayTeam.getPoints() + 3);
            homeTeam.setNumOfLost(homeTeam.getNumOfLost() + 1);
            awayTeam.setNumOfWon(awayTeam.getNumOfWon());
        } else {
            awayTeam.setPoints(awayTeam.getPoints() + 1);
            homeTeam.setPoints(homeTeam.getPoints() + 1);
            homeTeam.setNumOfDrawn(homeTeam.getNumOfDrawn());
            awayTeam.setNumOfDrawn(awayTeam.getNumOfDrawn());
        }

        // save updated entities (teams)
        teamRepository.save(homeTeam);
        teamRepository.save(awayTeam);
    }

    // update match
    @PutMapping("/teams/")
    public ResponseEntity<Team> updateUser(@PathVariable Integer id, @RequestBody Match match) {
        Team homeTeam = teamRepository.findById(match.getHomeTeamId())
                .orElseThrow(() -> new ResourseNotFoundException("team Not Found"));
        Team awayTeam = teamRepository.findById(match.getAwayTeamId())
                .orElseThrow(() -> new ResourseNotFoundException("team Not Found"));

        int homeTeamGoal = match.getHomeTeamGoal();
        int awayTeamGoal = match.getAwayTeamGoal();

        // set average of teams
        homeTeam.setAverage(homeTeam.getAverage() + homeTeamGoal - awayTeamGoal);
        awayTeam.setAverage(awayTeam.getAverage() + awayTeamGoal - homeTeamGoal);

        // set goals of teams
        homeTeam.setNumOfGoalsFor(homeTeam.getNumOfGoalsFor() + homeTeamGoal);
        homeTeam.setNumOfGoalsAgainst(homeTeam.getNumOfGoalsAgainst() + awayTeamGoal);

        awayTeam.setNumOfGoalsFor(awayTeam.getNumOfGoalsFor() + awayTeamGoal);
        awayTeam.setNumOfGoalsAgainst(awayTeam.getNumOfGoalsAgainst() + homeTeamGoal);

        // set number of played matches
        homeTeam.setNumOfPlayed(homeTeam.getNumOfPlayed() + 1);
        awayTeam.setNumOfPlayed(awayTeam.getNumOfPlayed() + 1);

        /*
         * distribute points
         * win => 3 pts
         * draw => 1 pts
         * lose => 0 pts
         */
        if (homeTeamGoal > awayTeamGoal) {
            homeTeam.setPoints(homeTeam.getPoints() + 3);
            homeTeam.setNumOfWon(homeTeam.getNumOfWon() + 1);
            awayTeam.setNumOfLost(awayTeam.getNumOfLost() + 1);
        } else if (homeTeamGoal < awayTeamGoal) {

            awayTeam.setPoints(awayTeam.getPoints() + 3);
            homeTeam.setNumOfLost(homeTeam.getNumOfLost() + 1);
            awayTeam.setNumOfWon(awayTeam.getNumOfWon());
        } else {
            awayTeam.setPoints(awayTeam.getPoints() + 1);
            homeTeam.setPoints(homeTeam.getPoints() + 1);
            homeTeam.setNumOfDrawn(homeTeam.getNumOfDrawn());
            awayTeam.setNumOfDrawn(awayTeam.getNumOfDrawn());
        }

        // save updated entities (teams)
        Team updatedHomeTeam = teamRepository.save(homeTeam);
        Team updatedAwayTeam = teamRepository.save(awayTeam);

        return ResponseEntity.ok(updatedHomeTeam);
    }

}
