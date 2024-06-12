package com.example.footballscoreboard;

import lombok.Getter;

@Getter
public enum ScoreBoardException {
    TEAM_ALREADY_EXISTS_IN_ONGOING_MATCH("Team already has match in progress"),
    HOME_TEAM_AND_AWAY_TEAM_ARE_THE_SAME("Home team and away team have the same name"),
    MATCH_DOES_NOT_EXIST("Match id used does not exist"),
    MATCH_ALREADY_FINISHED("Match has already finished"),
    INVALID_SCORE("Invalid score");

    private final String errorMessage;

    ScoreBoardException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
