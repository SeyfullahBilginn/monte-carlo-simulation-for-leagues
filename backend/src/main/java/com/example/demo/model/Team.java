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
    public int teamId;

    @Column(name = "teamName")
    public String teamName;

    @Column(name = "points")
    public int points;

    @Column(name = "numOfWon")
    public int numOfWon;

    @Column(name = "numOfDrawn")
    public int numOfDrawn;

    @Column(name = "numOfLost")
    public int numOfLost;

    @Column(name = "numOfPlayed")
    public int numOfPlayed;

    @Column(name = "numOfaverage")
    public int average;

    public Team(String teamName, int points, int numOfWon, int numOfDrawn, int numOfLost, int numOfPlayed,
            int average) {
        super();

        this.teamName = teamName;
        this.points = points;
        this.numOfWon = numOfWon;
        this.numOfDrawn = numOfDrawn;
        this.numOfLost = numOfLost;
        this.average = average;
        this.numOfPlayed = numOfPlayed;
    }

    public Team() {
    }

}
