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

    // get read user
    @GetMapping("/teams")
    public List<Team> getAllTeams() {
        return teamRepository.findAll(Sort.by(Sort.Direction.DESC, "points"));
    }

    // get read user
    @GetMapping("/teams/{id}")
    public Team getTeam(@PathVariable int id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new ResourseNotFoundException("team Not Found"));
    }

    // create team
    @PostMapping("/teams")
    public Team addInitialTeam(@RequestBody Team addedTeam) throws NoSuchProviderException {

        // Team team = new Team(teamName, 0, 0, 0, 0, 0);
        return teamRepository.save(addedTeam);
    }

    @DeleteMapping("/teams")
    public void deleteDatabase() {
        teamRepository.deleteAll();
        matchRepository.deleteAll();
    }

    @PostMapping("/playNextWeekRandomly/{numOfWeek}")
    public List<Match> playNextWeek(@PathVariable int numOfWeek) {
        HashMap<Integer, List<Integer>> fixture = new HashMap<>();
        List<Integer> firstWeek = Arrays.asList(0, 1, 2, 3);
        List<Integer> secondWeek = Arrays.asList(0, 2, 1, 3);
        List<Integer> thirdWeek = Arrays.asList(0, 3, 1, 2);
        List<Integer> fourthWeek = Arrays.asList(1, 0, 3, 2);
        List<Integer> fifthWeek = Arrays.asList(2, 0, 3, 1);
        List<Integer> sixthhWeek = Arrays.asList(3, 0, 2, 1);

        fixture.put(1, firstWeek);
        fixture.put(2, secondWeek);
        fixture.put(3, thirdWeek);
        fixture.put(4, fourthWeek);
        fixture.put(5, fifthWeek);
        fixture.put(6, sixthhWeek);

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
                firstHomeTeam.teamName,
                firstAwayTeam.teamName,
                firstHomeTeam.teamId,
                firstAwayTeam.teamId,
                (int) (Math.random() * (10 - 0)) + 0,
                (int) (Math.random() * (10 - 0)) + 0);

        doMatches(firstMatch);

        Team secondHomeTeam = teams.get(matches.get(2));
        Team secondAwayTeam = teams.get(matches.get(3));

        Match secondMatch = new Match(
                secondHomeTeam.teamName,
                secondAwayTeam.teamName,
                secondHomeTeam.teamId,
                secondAwayTeam.teamId,
                (int) (Math.random() * (10 - 0)) + 0,
                (int) (Math.random() * (10 - 0)) + 0);
        doMatches(secondMatch);
        // doMatches(secondHomeTeam, secondAwayTeam);
        results.add(firstMatch);
        results.add(secondMatch);

        matchRepository.save(firstMatch);
        matchRepository.save(secondMatch);

        return results;
    }

    @GetMapping("/simulate/{numOfWeek}")
    public Map<String, Integer> simulate(@PathVariable int numOfWeek) {
        System.out.println("SIMULATE");
        List<Match> matches = matchRepository.findAll();
        List<Team> teams = teamRepository.findAll();
        Simulation simulation = new Simulation(matches, teams, numOfWeek);
        return simulation.iterate();
    }

    public void doMatches(Match match) {
        Team homeTeam = teamRepository.findById(match.homeTeamId)
                .orElseThrow(() -> new ResourseNotFoundException("team Not Found"));
        Team awayTeam = teamRepository.findById(match.awayTeamId)
                .orElseThrow(() -> new ResourseNotFoundException("team Not Found"));

        System.out.println("homeTeamName: " + homeTeam.teamName + "homeTeamGoal: " + match.homeTeamGoal);
        System.out.println("awayTeamName: " + awayTeam.teamName + "awayTeamGoal: " + match.awayTeamGoal);

        int homeTeamGoal = match.homeTeamGoal;
        int awayTeamGoal = match.awayTeamGoal;

        homeTeam.average = homeTeam.average + homeTeamGoal - awayTeamGoal;
        awayTeam.average = awayTeam.average + awayTeamGoal - homeTeamGoal;

        if (homeTeamGoal > awayTeamGoal) {
            homeTeam.points = homeTeam.points + 3;
            homeTeam.numOfWon++;
            awayTeam.numOfLost++;
        } else if (homeTeamGoal < awayTeamGoal) {
            awayTeam.points = awayTeam.points + 3;
            homeTeam.numOfLost++;
            awayTeam.numOfWon++;
        } else {
            awayTeam.points = awayTeam.points + 1;
            homeTeam.points = homeTeam.points + 1;
            homeTeam.numOfDrawn++;
            awayTeam.numOfDrawn++;
        }

        Team updatedHomeTeam = teamRepository.save(homeTeam);
        Team updatedAwayTeam = teamRepository.save(awayTeam);
    }

    public void doMatches(Team homeTeam, Team awayTeam) {

        int homeTeamGoal = (int) (Math.random() * (7 - 0)) + 0;
        int awayTeamGoal = (int) (Math.random() * (7 - 0)) + 0;

        System.out.println("homeTeamName: " + homeTeam.teamName + "homeTeamGoal: " + homeTeamGoal);
        System.out.println("awayTeamName: " + awayTeam.teamName + "awayTeamGoal: " + awayTeamGoal);

        homeTeam.average = homeTeam.average + homeTeamGoal - awayTeamGoal;
        awayTeam.average = awayTeam.average + awayTeamGoal - homeTeamGoal;

        if (homeTeamGoal > awayTeamGoal) {
            homeTeam.points = homeTeam.points + 3;
            homeTeam.numOfWon++;
            awayTeam.numOfLost++;
        } else if (homeTeamGoal < awayTeamGoal) {
            awayTeam.points = awayTeam.points + 3;
            homeTeam.numOfLost++;
            awayTeam.numOfLost++;
        } else {
            awayTeam.points = awayTeam.points + 1;
            homeTeam.points = homeTeam.points + 1;
            homeTeam.numOfDrawn++;
            awayTeam.numOfDrawn++;
        }

        Team updatedHomeTeam = teamRepository.save(homeTeam);
        Team updatedAwayTeam = teamRepository.save(awayTeam);

        List<Team> teams = new ArrayList<>();

        teams.add(updatedHomeTeam);
        teams.add(updatedAwayTeam);
    }

    // update user
    @PutMapping("/teams/")
    public ResponseEntity<Team> updateUser(@PathVariable Integer id, @RequestBody Match match) {
        Team homeTeam = teamRepository.findById(match.homeTeamId)
                .orElseThrow(() -> new ResourseNotFoundException("team Not Found"));
        Team awayTeam = teamRepository.findById(match.awayTeamId)
                .orElseThrow(() -> new ResourseNotFoundException("team Not Found"));

        int homeTeamGoal = match.homeTeamGoal;
        int awayTeamGoal = match.awayTeamGoal;

        homeTeam.average = homeTeam.average + homeTeamGoal - awayTeamGoal;
        awayTeam.average = homeTeam.average + awayTeamGoal - homeTeamGoal;

        if (homeTeamGoal > awayTeamGoal) {
            homeTeam.points = homeTeam.points + 3;
            homeTeam.numOfWon++;
            awayTeam.numOfLost--;
        } else if (homeTeamGoal < awayTeamGoal) {
            awayTeam.points = awayTeam.points + 3;
            homeTeam.numOfWon--;
            awayTeam.numOfLost++;
        } else {
            awayTeam.points = awayTeam.points + 1;
            homeTeam.points = homeTeam.points + 1;
            homeTeam.numOfDrawn++;
            awayTeam.numOfDrawn++;
        }

        Team updatedHomeTeam = teamRepository.save(homeTeam);
        Team updatedAwayTeam = teamRepository.save(awayTeam);

        return ResponseEntity.ok(updatedHomeTeam);
    }

}
