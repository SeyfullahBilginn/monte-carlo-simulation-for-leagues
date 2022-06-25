package com.example.demo.controller;

import java.security.NoSuchProviderException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourseNotFoundException;
import com.example.demo.model.Match;
import com.example.demo.model.Team;
import com.example.demo.repository.TeamRepository;

@CrossOrigin(origins = "http://localhost:3000")
@Validated
@RestController
@RequestMapping("/api/V1/")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    // get read user
    @GetMapping("/teams")
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    // get read user
    @GetMapping("/teams/{id}")
    public Team getTeam(@PathVariable int id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new ResourseNotFoundException("team Not Found"));
    }

    // create team
    @PostMapping("/teams")
    public Team addInitialTeam(@RequestBody String teamName) throws NoSuchProviderException {

        Team team = new Team(teamName, 0, 0, 0, 0, 0);
        return teamRepository.save(team);
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
