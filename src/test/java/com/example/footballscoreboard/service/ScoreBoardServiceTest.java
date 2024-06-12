package com.example.footballscoreboard.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.footballscoreboard.model.Match;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ScoreBoardServiceTest {

    private ScoreBoardServiceImpl uut;

    @BeforeEach
    void setup() {
        uut = new ScoreBoardServiceImpl();
    }

    @Test
    void should_start_first_game_and_return_id_1_for_only_match() {
        // given
        // when
        var result = uut.startGame("ARGENTINA", "BRAZIL");
        // then
        assertEquals(1, result);
    }

    @Test
    void should_return_match_id_2_if_starting_game_when_one_already_in_progress() {
        // given
        uut.startGame("ARGENTINA","BRAZIL");
        // when
        var result = uut.startGame("FRANCE", "CANADA");
        // then
        assertEquals(2, result);
    }

    @Test
    void should_throw_if_the_home_and_away_team_are_the_same() {
        // given
        // when
        // then
        assertThrows(IllegalStateException.class, () -> uut.startGame("ARGENTINA", "ARGENTINA"), "Home team and away team have the same name");
    }

    @Test
    void should_throw_if_trying_to_start_new_game_with_team_already_in_a_match() {
        // given
        uut.startGame("ARGENTINA", "BRAZIL");
        // when
        // then
        assertThrows(IllegalStateException.class, () -> uut.startGame("ARGENTINA", "URUGUAY"), "Team already has match in progress");
    }

    @Test
    void should_finish_game_when_correct_match_id_provided() {
        // given
        uut.startGame("ARGENTINA", "BRAZIL");
        uut.startGame("ITALY", "AUSTRALIA");
        // when
        uut.finishGame(1);
        // then
        assertEquals(uut.getSummaryOfMatches().get(1).getMatchId(), 2);
    }

    @Test
    void should_throw_if_match_id_does_not_exist_when_trying_to_end_game() {
        // given
        uut.startGame("ARGENTINA", "BRAZIL");
        uut.startGame("ITALY", "AUSTRALIA");
        // when
        // then
        assertThrows(IllegalStateException.class, () -> uut.finishGame(5), "Match id does not exist");
    }

    @Test
    void should_update_score_of_match() {
        // given
        uut.startGame("ARGENTINA", "BRAZIL");
        uut.startGame("GERMANY", "SPAIN");
        uut.startGame("ITALY", "CANADA");
        // when
        uut.updateScore(2, 1, 0);
        // then
        var uneditedGame1 = uut.getSummaryOfMatches().get(0);
        var uneditedGame2 = uut.getSummaryOfMatches().get(2);
        var updatedGame = uut.getSummaryOfMatches().get(1);
        assertAll(
                () -> assertEquals(updatedGame.getHomeTeam(), "GERMANY"),
                () -> assertEquals(updatedGame.getAwayTeam(), "SPAIN"),
                () -> assertEquals(updatedGame.getHomeScore(), 1),
                () -> assertEquals(updatedGame.getAwayScore(), 0),
                () -> assertEquals(uneditedGame1.getHomeTeam(), "ARGENTINA"),
                () -> assertEquals(uneditedGame1.getAwayTeam(), "BRAZIL"),
                () -> assertEquals(uneditedGame1.getHomeScore(), 0),
                () -> assertEquals(uneditedGame1.getAwayScore(), 0),
                () -> assertEquals(uneditedGame2.getHomeTeam(), "ITALY"),
                () -> assertEquals(uneditedGame2.getAwayTeam(), "CANADA"),
                () -> assertEquals(uneditedGame2.getHomeScore(), 0),
                () -> assertEquals(uneditedGame2.getAwayScore(), 0)
        );
    }

    @Test
    void should_throw_if_trying_to_update_match_that_does_not_exist() {
        // given
        uut.startGame("ARGENTINA", "BRAZIL");
        // when
        // then
        assertThrows(IllegalStateException.class, () -> uut.updateScore(2, 1, 0), "Match id does not exist");

    }

    @Test
    void should_throw_if_trying_to_update_match_that_is_already_finished() {
        // given
        uut.startGame("ARGENTINA", "BRAZIL");
        uut.finishGame(1);
        // when
        // then
        assertThrows(IllegalStateException.class, () -> uut.updateScore(1, 1, 0), "Match already finished");

    }

    @ParameterizedTest
    @CsvSource(value = {
            "0,0",
            "3,0",
            "1,2"
    })
        // I know we have VAR now so scores can be removed, but wanted to add just in case
    void should_throw_if_trying_to_decrease_score_or_add_more_than_plus_one_score_for_either_team(int homeScore, int awayScore) {
        // given
        uut.startGame("ARGENTINA", "BRAZIL");
        uut.updateScore(1, 1, 0);
        // when
        // then
        assertThrows(IllegalStateException.class, () -> uut.updateScore(1, homeScore, awayScore), "Invalid score");

    }

    @Test
    void should_return_summary_of_matches() {
        // given
        uut.startGame("ARGENTINA", "BRAZIL");
        uut.startGame("GERMANY", "AUSTRALIA");
        uut.startGame("FRANCE", "MEXICO");

        // when
        // then
        assertThat(uut.getSummaryOfMatches()).hasSize(3);
    }

    @Test
    void should_get_summary_of_matches_sorted_by_time() {
        ScoreBoardServiceImpl scoreBoardService = new ScoreBoardServiceImpl();

        Match match1 = new Match(1, "ARGENTINA", "BRAZIL");
        match1.setStartTime(LocalDateTime.now().minusHours(2));  // Earlier start time
        Match match2 = new Match(2, "GERMANY", "AUSTRALIA");
        match2.setStartTime(LocalDateTime.now().plusHours(1));  // Later start time
        Match match3 = new Match(3, "FRANCE", "MEXICO");
        match3.setStartTime(LocalDateTime.now());

        scoreBoardService.addToGameList(match2);
        scoreBoardService.addToGameList(match3);
        scoreBoardService.addToGameList(match1);

        List<Match> sortedMatches = scoreBoardService.getSummaryOfMatches();

        assertAll(
                () -> assertEquals(sortedMatches.get(0).getMatchId(), 1),
                () -> assertEquals(sortedMatches.get(1).getMatchId(), 3),
                () -> assertEquals(sortedMatches.get(2).getMatchId(), 2)
        );
    }
}
