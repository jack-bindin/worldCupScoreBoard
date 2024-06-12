package com.example.footballscoreboard;

import com.example.footballscoreboard.model.Match;
import com.example.footballscoreboard.service.ScoreBoardServiceImpl;
import java.util.Scanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FootballScoreBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(FootballScoreBoardApplication.class, args);
        ScoreBoardServiceImpl scoreBoardService = new ScoreBoardServiceImpl();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter your command (start/finish/update/summary/exit): ");
            String command = scanner.nextLine().trim();

            switch (command) {
                case "start":
                    System.out.println("Enter home team: ");
                    String homeTeam = scanner.nextLine().trim();
                    System.out.println("Enter away team: ");
                    String awayTeam = scanner.nextLine().trim();
                    int matchId = scoreBoardService.startGame(homeTeam, awayTeam);
                    System.out.println("Started a new game with ID: " + matchId);
                    break;
                case "finish":
                    System.out.println("Enter match ID to finish: ");
                    int finishMatchId = Integer.parseInt(scanner.nextLine().trim());
                    scoreBoardService.finishGame(finishMatchId);
                    System.out.println("Match with ID " + finishMatchId + " finished");
                    break;
                case "update":
                    System.out.println("Enter match ID to update score: ");
                    int updateMatchId = Integer.parseInt(scanner.nextLine().trim());
                    System.out.println("Enter home score: ");
                    int homeScore = Integer.parseInt(scanner.nextLine().trim());
                    System.out.println("Enter away score: ");
                    int awayScore = Integer.parseInt(scanner.nextLine().trim());
                    scoreBoardService.updateScore(updateMatchId, homeScore, awayScore);
                    System.out.println("Score updated for match ID " + updateMatchId);
                    break;
                case "summary":
                    System.out.println("Summary of Matches:");
                    for (Match match : scoreBoardService.getSummaryOfMatches()) {
                        System.out.println(match.getHomeTeam() + " " + match.getHomeScore() + " - " + match.getAwayTeam() + " " + match.getAwayScore());
                    }
                    break;
                case "exit":
                    System.out.println("Exiting program.");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid command. Please try again.");
            }
        }
    }

}
