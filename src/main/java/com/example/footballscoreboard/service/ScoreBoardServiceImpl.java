package com.example.footballscoreboard.service;

import com.example.footballscoreboard.model.Match;
import java.util.List;

public class ScoreBoardServiceImpl implements ScoreBoardService {

    @Override
    public int startGame(String homeTeam, String awayTeam) {
        return 0;
    }

    @Override
    public void finishGame(int matchId) {

    }

    @Override
    public void updateScore(int matchId, int homeScore, int awayScore) {

    }

    @Override
    public List<Match> getSummaryOfMatches() {
        return null;
    }
}
