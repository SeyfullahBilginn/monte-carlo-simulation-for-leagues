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
    public int matchId;
    @Column(name = "homeTeamId")
    public int homeTeamId;
    @Column(name = "awayTeamId")
    public int awayTeamId;
    @Column(name = "homeTeamGoal")
    public int homeTeamGoal;
    @Column(name = "awayTeamGoal")
    public int awayTeamGoal;

    public Match(int homeTeamId, int awayTeamId, int homeTeamGoal, int awayTeamGoal) {
        super();

        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        this.homeTeamGoal = homeTeamGoal;
        this.awayTeamGoal = awayTeamGoal;
    }

    public Match() {
    }

}
