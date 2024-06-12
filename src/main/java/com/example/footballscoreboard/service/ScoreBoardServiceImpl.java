package com.example.footballscoreboard.service;

import com.example.footballscoreboard.ScoreBoardException;
import com.example.footballscoreboard.model.Match;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ScoreBoardServiceImpl implements ScoreBoardService {
    private List<Match> gameList = new CopyOnWriteArrayList<>();
    @Override
    public synchronized int startGame(String homeTeam, String awayTeam) {
        validateStartingTeams(homeTeam, awayTeam);

        return addMatchToScoreBoard(homeTeam, awayTeam);
    }

    @Override
    public synchronized void finishGame(int matchId) {

        for (Match match : getSummaryOfMatches()) {
            if (match.getMatchId() == matchId) {
                if (match.isFinished()) {
                    throw new IllegalStateException(ScoreBoardException.MATCH_ALREADY_FINISHED.getErrorMessage());
                } else {
                    match.setFinished(true);
                    return;
                }
            }
        }

        throw new IllegalStateException(ScoreBoardException.MATCH_DOES_NOT_EXIST.getErrorMessage());
    }

    @Override
    public synchronized void updateScore(int matchId, int homeScore, int awayScore) {

    }

    @Override
    public List<Match> getSummaryOfMatches() {
        return gameList;
    }

    private void validateStartingTeams(String homeTeam, String awayTeam) {
        if(homeTeam.equals(awayTeam)) {
            throw new IllegalStateException(ScoreBoardException.HOME_TEAM_AND_AWAY_TEAM_ARE_THE_SAME.getErrorMessage());
        }
        if (getSummaryOfMatches().stream()
                .anyMatch(match -> match.getHomeTeam().equals(homeTeam)
                        || match.getHomeTeam().equals(awayTeam)
                        || match.getAwayTeam().equals(homeTeam)
                        || match.getAwayTeam().equals(awayTeam))) {
            throw new IllegalStateException(ScoreBoardException.TEAM_ALREADY_EXISTS_IN_ONGOING_MATCH.getErrorMessage());
        }
    }

    private int addMatchToScoreBoard(String homeTeam, String awayTeam) {
        int matchId = getSummaryOfMatches().size() + 1;
        getSummaryOfMatches().add(new Match(matchId, homeTeam, awayTeam));
        return matchId;
    }
}
