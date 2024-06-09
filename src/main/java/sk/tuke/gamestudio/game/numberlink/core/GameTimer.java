package sk.tuke.gamestudio.game.numberlink.core;

import lombok.Getter;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    private final Field field;
    @Getter
    private int secondsLeft;
    private Timer timer;

    public GameTimer(Field field, int seconds) {
        this.field = field;
        this.secondsLeft = seconds;
    }

    public void start() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (secondsLeft > 0) {
                    secondsLeft--;
                } else {
                    timer.cancel();
                    field.setState(GameState.FAILED);
                }
            }
        }, 0, 1000);
    }
}
