package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int matchId;

    @Column(name = "homeTeamName")
    private String homeTeamName;

    @Column(name = "awayTeamName")
    private String awayTeamName;

    @Column(name = "homeTeamId")
    private int homeTeamId;

    @Column(name = "awayTeamId")
    private int awayTeamId;

    @Column(name = "homeTeamGoal")
    private int homeTeamGoal;

    @Column(name = "awayTeamGoal")
    private int awayTeamGoal;

    public Match(
            String homeTeamName,
            String awayTeamName,
            int homeTeamId,
            int awayTeamId,
            int homeTeamGoal,
            int awayTeamGoal) {
        super();

        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        this.homeTeamGoal = homeTeamGoal;
        this.awayTeamGoal = awayTeamGoal;
    }

    public Match() {
    }

    public int getMatchId() {
        return this.matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public String getHomeTeamName() {
        return this.homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getAwayTeamName() {
        return this.awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public int getHomeTeamId() {
        return this.homeTeamId;
    }

    public void setHomeTeamId(int homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public int getAwayTeamId() {
        return this.awayTeamId;
    }

    public void setAwayTeamId(int awayTeamId) {
        this.awayTeamId = awayTeamId;
    }

    public int getHomeTeamGoal() {
        return this.homeTeamGoal;
    }

    public void setHomeTeamGoal(int homeTeamGoal) {
        this.homeTeamGoal = homeTeamGoal;
    }

    public int getAwayTeamGoal() {
        return this.awayTeamGoal;
    }

    public void setAwayTeamGoal(int awayTeamGoal) {
        this.awayTeamGoal = awayTeamGoal;
    }

}
