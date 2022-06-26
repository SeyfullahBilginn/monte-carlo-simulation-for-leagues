package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int teamId;

    @Column(name = "teamName")
    private String teamName;

    @Column(name = "points")
    private int points;

    @Column(name = "numOfWon")
    private int numOfWon;

    @Column(name = "numOfDrawn")
    private int numOfDrawn;

    @Column(name = "numOfLost")
    private int numOfLost;

    @Column(name = "numOfPlayed")
    private int numOfPlayed;

    @Column(name = "numOfGoalsFor")
    private int numOfGoalsFor;

    @Column(name = "numOfGoalsAgainst")
    private int numOfGoalsAgainst;

    @Column(name = "numOfaverage")
    private int average;

    public Team(
            String teamName,
            int points,
            int numOfWon,
            int numOfDrawn,
            int numOfLost,
            int numOfPlayed,
            int numOfGoalsFor,
            int numOfGoalsAgainst,
            int average) {

        super();
        this.teamName = teamName;
        this.points = points;
        this.numOfWon = numOfWon;
        this.numOfDrawn = numOfDrawn;
        this.numOfLost = numOfLost;
        this.numOfGoalsFor = numOfGoalsFor;
        this.numOfGoalsAgainst = numOfGoalsAgainst;
        this.average = average;
        this.numOfPlayed = numOfPlayed;
    }

    public Team() {
    }

    public int getTeamId() {
        return this.teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return this.teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getNumOfWon() {
        return this.numOfWon;
    }

    public void setNumOfWon(int numOfWon) {
        this.numOfWon = numOfWon;
    }

    public int getNumOfDrawn() {
        return this.numOfDrawn;
    }

    public void setNumOfDrawn(int numOfDrawn) {
        this.numOfDrawn = numOfDrawn;
    }

    public int getNumOfLost() {
        return this.numOfLost;
    }

    public void setNumOfLost(int numOfLost) {
        this.numOfLost = numOfLost;
    }

    public int getNumOfPlayed() {
        return this.numOfPlayed;
    }

    public void setNumOfPlayed(int numOfPlayed) {
        this.numOfPlayed = numOfPlayed;
    }

    public int getNumOfGoalsFor() {
        return this.numOfGoalsFor;
    }

    public void setNumOfGoalsFor(int numOfGoalsFor) {
        this.numOfGoalsFor = numOfGoalsFor;
    }

    public int getNumOfGoalsAgainst() {
        return this.numOfGoalsAgainst;
    }

    public void setNumOfGoalsAgainst(int numOfGoalsAgainst) {
        this.numOfGoalsAgainst = numOfGoalsAgainst;
    }

    public int getAverage() {
        return this.average;
    }

    public void setAverage(int average) {
        this.average = average;
    }

}
