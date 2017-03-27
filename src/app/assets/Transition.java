package app.assets;

import javafx.animation.*;
import javafx.scene.Parent;
import javafx.util.Duration;

/**
 * Created by r32427 on 27/03/17.
 */
public class Transition {
    public static void fadeTransition(Parent pane, long milis){
        FadeTransition fadeTransition = new FadeTransition(new Duration(milis), pane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(100);
        fadeTransition.setCycleCount(1);
        fadeTransition.setInterpolator(Interpolator.LINEAR);
        fadeTransition.play();
    }

    public static void translateTransition(Parent pane, long milis){
        TranslateTransition translateTransition = new TranslateTransition(new Duration(milis), pane);
        translateTransition.setFromX(-300f);
        translateTransition.setToX(300f);
        translateTransition.setAutoReverse(true);
    }

    public static void rotateTransition(Parent pane, long milis){
        RotateTransition rotateTransition = new RotateTransition(new Duration(milis), pane);
        rotateTransition.setByAngle(180f);
        rotateTransition.setAutoReverse(true);
    }
}
