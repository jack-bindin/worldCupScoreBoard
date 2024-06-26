package com.example.footballscoreboard.service;

import com.example.footballscoreboard.ScoreBoardException;
import com.example.footballscoreboard.model.Match;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ScoreBoardServiceImpl implements ScoreBoardService {

    private final Map<Integer, Match> gameMap = new ConcurrentHashMap<>();

    @Override
    public synchronized int startGame(String homeTeam, String awayTeam) {
        validateStartingTeams(homeTeam, awayTeam);

        return addMatchToScoreBoard(homeTeam, awayTeam);
    }

    @Override
    public synchronized void finishGame(int matchId) {
        Match matchToUpdate = getMatchById(matchId);

        if (matchToUpdate.isFinished()) {
            throw new IllegalStateException(
                    ScoreBoardException.MATCH_ALREADY_FINISHED.getErrorMessage());
        }

        matchToUpdate.setFinished(true);
    }

    @Override
    public synchronized void updateScore(int matchId, int homeScore, int awayScore) {
        Match matchToUpdate = getMatchById(matchId);

        if (matchToUpdate.isFinished()) {
            throw new IllegalStateException(
                    ScoreBoardException.MATCH_ALREADY_FINISHED.getErrorMessage());
        }

        validateAndUpdateScore(matchToUpdate, homeScore, awayScore);
    }

    @Override
    public List<Match> getSummaryOfMatches() {
        return getGameMap().values().stream()
                .filter(match -> !match.isFinished())
                .sorted(Comparator.comparing(Match::getStartTime))
                .toList();
    }

    private Map<Integer, Match> getGameMap() {
        return gameMap;
    }

    void addToGameList(int matchId, Match match) {
        gameMap.put(matchId, match);
    }

    private Match getMatchById(int matchId) {
        return getGameMap().computeIfAbsent(matchId, key -> {
            throw new IllegalStateException(
                    ScoreBoardException.MATCH_DOES_NOT_EXIST.getErrorMessage());
        });
    }

    private void validateStartingTeams(String homeTeam, String awayTeam) {
        if (homeTeam.equals(awayTeam)) {
            throw new IllegalStateException(
                    ScoreBoardException.HOME_TEAM_AND_AWAY_TEAM_ARE_THE_SAME.getErrorMessage());
        }
        if (getGameMap().values().stream()
                .filter(match -> !match.isFinished())
                .anyMatch(match -> match.getHomeTeam().equals(homeTeam)
                        || match.getHomeTeam().equals(awayTeam)
                        || match.getAwayTeam().equals(homeTeam)
                        || match.getAwayTeam().equals(awayTeam))) {
            throw new IllegalStateException(
                    ScoreBoardException.TEAM_ALREADY_EXISTS_IN_ONGOING_MATCH.getErrorMessage());
        }
    }

    private int addMatchToScoreBoard(String homeTeam, String awayTeam) {
        int matchId = getGameMap().size() + 1;
        addToGameList(matchId, new Match(matchId, homeTeam, awayTeam));
        return matchId;
    }

    private void validateAndUpdateScore(Match match, int homeScore, int awayScore) {
        if (homeScore < match.getHomeScore()
                || homeScore > match.getHomeScore() + 1
                || awayScore < match.getAwayScore()
                || awayScore > match.getAwayScore() + 1) {
            throw new IllegalStateException(ScoreBoardException.INVALID_SCORE.getErrorMessage());
        }

        match.setHomeScore(homeScore);
        match.setAwayScore(awayScore);
    }
}
