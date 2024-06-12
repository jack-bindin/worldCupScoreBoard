package com.example.footballscoreboard.service;

import com.example.footballscoreboard.model.Match;
import java.util.List;

public interface ScoreBoardService {

    int startGame(String homeTeam, String awayTeam) throws IllegalAccessException;

    void finishGame(int matchId);

    void updateScore(int matchId, int homeScore, int awayScore);

    List<Match> getSummaryOfMatches();
}
