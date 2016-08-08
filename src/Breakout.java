import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.settings.GameSettings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Breakout extends GameApplication
{
    private enum Type
    {
        PADDLE, BALL, BRICK
    }

    private GameEntity paddle, ball;

    private IntegerProperty bricks;

    @Override
    protected void initSettings(GameSettings gameSettings)
    {
        gameSettings.setTitle("Breakout");
        gameSettings.setVersion("0.1");
        gameSettings.setWidth(640);
        gameSettings.setHeight(700);
        gameSettings.setIntroEnabled(false);
        gameSettings.setMenuEnabled(false);
        gameSettings.setShowFPS(false);
    }

    @Override
    protected void initInput()
    {

    }

    @Override
    protected void initAssets()
    {
        getAssetLoader().cache();
    }

    @Override
    protected void initGame()
    {
        bricks = new SimpleIntegerProperty();
        bricks.set(30);

        initPaddle();
        initBall();
        initBricks();
    }

    private void initPaddle()
    {
        paddle = Entities.builder().type(Type.PADDLE).build();
        // paddle is 128x24
        paddle.setX(getWidth() / 2 - 128 / 2);
        paddle.setY(getHeight() - 24);
        paddle.setViewFromTexture("paddle.png");

        getGameWorld().addEntity(paddle);
    }

    private void initBall()
    {
        ball = Entities.builder().type(Type.BALL).with(new BallControl()).build();
        ball.setX(getWidth() / 2 - 24 / 2);
        ball.setY(getHeight() / 2 - 24 / 2);
        ball.setViewFromTexture("ball.png");

        getGameWorld().addEntity(ball);
    }

    private void initBricks()
    {
        for(int i = 0; i < bricks.get(); i++)
        {
            GameEntity brick = Entities.builder().type(Type.BRICK).build();
            brick.setX(i % 10 * 64);
            brick.setY(30 + i / 10 * 32);
            brick.setViewFromTexture("brick.png");

            getGameWorld().addEntity(brick);
        }
    }

    @Override
    protected void initPhysics()
    {

    }

    @Override
    protected void initUI()
    {

    }

    @Override
    protected void onUpdate(double v)
    {

    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
