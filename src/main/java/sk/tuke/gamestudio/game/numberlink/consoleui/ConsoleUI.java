package sk.tuke.gamestudio.game.numberlink.consoleui;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.numberlink.core.Number;
import sk.tuke.gamestudio.game.numberlink.core.*;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class ConsoleUI {
    @Autowired
    private ScoreService scoreService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private CommentService commentService;

    private Field field;
    private int rowCount;
    private int columnCount;
    private String playerName;
    private int difficultyLevel;
    private int score;

    //--------------------------------
    //  GAME INIT PROPERTIES
    //--------------------------------
    public void initGame() {
        askForName();
        askForDifficulty();
        score = 0;
    }

    private void askForName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        playerName = scanner.nextLine().trim();

        while (playerName.isEmpty()) {
            System.out.print("Username cannot be empty. Please enter again: ");
            playerName = scanner.nextLine().trim();
        }
    }

    private void askForDifficulty() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose difficulty level \n  1. Easy \n  2. Medium \n  3. Hard");
        System.out.print("Enter the number only: ");
        difficultyLevel = scanner.nextInt();

        while (difficultyLevel < 1 || difficultyLevel > 3) {
            System.out.println("Invalid input. Please enter a number between 1 and 3.");
            System.out.print("Enter the number only: ");
            difficultyLevel = scanner.nextInt();
        }
    }

    //--------------------------------
    //  GAMEPLAY
    //--------------------------------
    public void play() {
        FieldGenerator fieldGenerator = new FieldGenerator();
        field = fieldGenerator.generateField(difficultyLevel);
        this.rowCount = field.getRowCount();
        this.columnCount = field.getColumnCount();

        field.getGameTimer().start();
        while (field.getState() == GameState.PLAYING) {
            show();
            handleInput();
        }
        show();

        if (field.getState() == GameState.FAILED) {
            System.out.println("Time is up! You failed.. :(");
        } else {
            score += difficultyLevel * 100;
            System.out.println("Congratulations! Game solved!");
        }

        if (continuePlaying()) {
            play();
        } else {
            scoreService.addScore(new Score("numberlink", playerName, score, new Date()));
        }
    }

    private boolean continuePlaying() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Continue playing? (y/n): ");
        String answer = scanner.nextLine();
        return answer.equalsIgnoreCase("y");
    }

    //--------------------------------
    //  GAME CONSOLE PRINT
    //--------------------------------
    public void show() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Time left (seconds): " + field.getGameTimer().getSecondsLeft());
        System.out.println("----------------------------------------------------------------");
        printColumnNumbers(columnCount);
        printDashedLine(columnCount);
        for (int row = 0; row < rowCount; row++) {
            System.out.print((row + 1) + " |");   // print row numbers
            for (int column = 0; column < columnCount; column++) {
                printTile(field, row, column);
            }
            System.out.print("\n");
            printDashedLine(columnCount);
        }
    }

    private void printColumnNumbers(int columnCount) {
        System.out.print("    ");
        for (int column = 0; column < columnCount; column++) System.out.print((column + 1) + "   ");
        System.out.print("\n");
    }

    private void printDashedLine(int columnCount) {
        System.out.print("  ");
        for (int column = 0; column < columnCount; column++) System.out.print("+---");
        System.out.print("+\n");
    }

    private void printTile(Field field, int row, int column) {
        var tile = field.getTile(row, column);
        boolean isTileLinked = field.isTileLinked(row, column);

        if (tile instanceof Number) {
            int value = tile.getValue();
            System.out.print(BackgroundColor.getColor(value) + value
                    + (isTileLinked ? "NL" : "N ") + BackgroundColor.getColor(0) + "|");
        } else if (tile instanceof Link) {
            int value = tile.getValue();
            System.out.print(BackgroundColor.getColor(value) + value
                    + (isTileLinked ? " L" : "  ") + BackgroundColor.getColor(0) + "|");
        } else System.out.print("   |");
    }

    //--------------------------------
    //  END SCREEN
    //--------------------------------
    public void showEndScreen() {
        showScoreboard();
        showRating();
        showComments();
        askForComment();
        askForRating();
        System.out.println("Thanks for playing! Ending.. ");
    }

    public void showScoreboard() {
        List<Score> scores = scoreService.getTopScores("numberlink");
        System.out.println("----------------------------------------------------------------");
        System.out.println(" YOUR SCORE: " + score);
        System.out.println("---+---------------------+-----------+--------------------------");
        System.out.printf("   | %-20s| %-10s| %s%n", "USERNAME", "SCORE", "DATE");
        System.out.println("---+---------------------+-----------+--------------------------");
        for (int i = 0; i < scores.size(); i++) {
            Score score = scores.get(i);
            System.out.printf("%-3d| %-20s| %-10s| %s%n", i + 1, score.getPlayer(), score.getPoints(), score.getPlayedOn());
        }
        System.out.println("---+---------------------+-----------+--------------------------");
    }

    private void showRating() {
        int rating = ratingService.getAverageRating("numberlink");
        System.out.print(" Rating: ");
        for (int i = 1; i <= rating; i++) System.out.print("* ");
        System.out.println("\n----------------------------------------------------------------");
    }

    private void showComments() {
        System.out.println(" -- COMMENTS --");
        List<Comment> comments = commentService.getComments("numberlink");
        for (Comment comment : comments) {
            System.out.printf("%s: %s\n", comment.getPlayer(), comment.getText());
        }
        System.out.println("----------------------------------------------------------------");
    }

    private void askForComment() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Do you want to add a comment? (y/n): ");
        String answer = scanner.nextLine();

        if (answer.equalsIgnoreCase("y")) {
            System.out.print("Enter your comment: ");
            String text = scanner.nextLine();
            commentService.addComment(new Comment("numberlink", playerName, text, new Date()));
        }
    }

    private void askForRating() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Do you want to add a rating? (y/n): ");
        String answer = scanner.nextLine();

        if (answer.equalsIgnoreCase("y")) {
            System.out.print("Enter your rating (1-5): ");
            int value = scanner.nextInt();

            while (value < 1 || value > 5) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                System.out.print("Enter your rating (1-5): ");
                value = scanner.nextInt();
            }
            ratingService.setRating(new Rating("numberlink", playerName, value));
        }
    }

    //--------------------------------
    //  GAME INPUTS
    //--------------------------------
    public void handleInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Pick X, Y, Direction (e.g., 1 2 up): ");
        String input = scanner.nextLine();

        try {
            parseAndValidateInput(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid row/column value. Please enter integers.");
            return;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Position/Direction");
            return;
        }

        String[] parts = input.trim().split("\\s+");

        int row = Integer.parseInt(parts[1]) - 1;
        int column = Integer.parseInt(parts[0]) - 1;
        Direction direction = parseDirection(parts[2]);

        field.linkTile(row, column, direction);
    }

    private void parseAndValidateInput(String input) throws IllegalArgumentException {
        String[] parts = input.trim().split("\\s+");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid input format. Please use: row col direction");
        }

        int row = Integer.parseInt(parts[1]);
        int column = Integer.parseInt(parts[0]);
        if (!field.isPositionValid(row - 1, column - 1) ||
                (!parts[2].equals("up") && !parts[2].equals("down")
                        && !parts[2].equals("left") && !parts[2].equals("right"))) {
            throw new IllegalArgumentException("Invalid Position/Direction");
        }
    }

    private Direction parseDirection(String directionStr) {
        return switch (directionStr) {
            case "up" -> Direction.UP;
            case "down" -> Direction.DOWN;
            case "left" -> Direction.LEFT;
            case "right" -> Direction.RIGHT;
            default -> null;
        };
    }
}