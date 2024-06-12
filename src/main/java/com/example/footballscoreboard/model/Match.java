package com.example.footballscoreboard.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Match {
    int matchId;
    String homeTeam;
    String awayTeam;
    int homeScore;
    int awayScore;
    LocalDateTime startTime;
    @Setter
    boolean finished;

    public Match(int matchId, String homeTeam, String awayTeam) {
        this.matchId = matchId;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.startTime = LocalDateTime.now();
        this.finished = false;
        this.homeScore = 0;
        this.awayScore = 0;
    }
}
