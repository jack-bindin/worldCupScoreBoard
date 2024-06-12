package com.example.footballscoreboard;

import lombok.Getter;

public enum ScoreBoardException {
    TEAM_ALREADY_EXISTS_IN_ONGOING_MATCH ("Team already has match in progress"),
    HOME_TEAM_AND_AWAY_TEAM_ARE_THE_SAME ("Home team and away team have the same name");
    @Getter
    private final String errorMessage;
    ScoreBoardException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}