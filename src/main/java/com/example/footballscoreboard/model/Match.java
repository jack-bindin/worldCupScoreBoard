package com.example.footballscoreboard.model;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Match {
    int matchId;
    String homeTeam;
    String awayTeam;
    int homeScore;
    int awayScore;
    LocalDateTime startTime;
    boolean finished;
}
