import com.almasb.ents.Entity;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.entity.component.CollidableComponent;
import com.almasb.fxgl.entity.component.PositionComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.settings.GameSettings;
import com.badlogic.gdx.Game;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.shape.Rectangle;

public class Breakout extends GameApplication
{
    public enum Type
    {
        PADDLE, BALL, BRICK, SCREEN
    }

    private GameEntity paddle, ball;

    private IntegerProperty bricks;

    private int x, y;

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

        initScreenBounds();
        initPaddle();
        initBall();
        initBricks();
    }

    private void initScreenBounds()
    {
        //top
        Entities.builder()
                .type(Type.SCREEN)
                .at(0, -1)
                .viewFromNodeWithBBox(new Rectangle(getWidth(), 1))
                .with(new CollidableComponent(true))
                .buildAndAttach(getGameWorld());

        //left
        Entities.builder()
                .type(Type.SCREEN)
                .at(-1, 0)
                .viewFromNodeWithBBox(new Rectangle(1, getHeight()))
                .with(new CollidableComponent(true))
                .buildAndAttach(getGameWorld());

        //right
        Entities.builder()
                .type(Type.SCREEN)
                .at(getWidth(), 0)
                .viewFromNodeWithBBox(new Rectangle(1, getHeight()))
                .with(new CollidableComponent(true))
                .buildAndAttach(getGameWorld());

        //bottom
        Entities.builder()
                .type(Type.SCREEN)
                .at(0, getHeight() + 1)
                .viewFromNodeWithBBox(new Rectangle(getWidth(), 1))
                .with(new CollidableComponent(true))
                .buildAndAttach(getGameWorld());
    }


    private void initPaddle()
    {
        paddle = Entities.builder().type(Type.PADDLE).build();
        // paddle is 128x24
        paddle.setX(getWidth() / 2 - 128 / 2);
        paddle.setY(getHeight() - 24);
        paddle.setViewFromTextureWithBBox("paddle.png");
        paddle.addComponent(new CollidableComponent(true));

        getGameWorld().addEntity(paddle);
    }

    private void initBall()
    {
        x = 1;
        y = -5;

        ball = Entities.builder().type(Type.BALL).build();
        ball.setX(getWidth() / 2 - 24 / 2);
        ball.setY(getHeight() / 2 - 24 / 2);
        ball.setViewFromTextureWithBBox("ball.png");
        ball.addComponent(new CollidableComponent(true));

        getGameWorld().addEntity(ball);
    }

    private void initBricks()
    {
        for(int i = 0; i < bricks.get(); i++)
        {
            GameEntity brick = Entities.builder().type(Type.BRICK).build();
            brick.setX(i % 10 * 64);
            brick.setY(30 + i / 10 * 32);
            brick.setViewFromTextureWithBBox("brick.png");
            brick.addComponent(new CollidableComponent(true));

            getGameWorld().addEntity(brick);
        }
    }

    @Override
    protected void initPhysics()
    {
        PhysicsWorld physics = getPhysicsWorld();

        physics.addCollisionHandler(new CollisionHandler(Type.BALL, Type.SCREEN)
        {
            @Override
            protected void onHitBoxTrigger(Entity a, Entity b, HitBox boxA, HitBox boxB) {}

            @Override
            protected void onCollisionBegin(Entity a, Entity b)
            {
                y = -y;
            }

            @Override
            protected void onCollision(Entity a, Entity b) {}

            @Override
            protected void onCollisionEnd(Entity a, Entity b) {}
        });

        physics.addCollisionHandler(new CollisionHandler(Type.BALL, Type.BRICK)
        {
            @Override
            protected void onHitBoxTrigger(Entity a, Entity b, HitBox boxA, HitBox boxB) {}

            @Override
            protected void onCollisionBegin(Entity a, Entity b)
            {
                getGameWorld().removeEntity(b);
                y = -y;
            }

            @Override
            protected void onCollision(Entity a, Entity b) {}

            @Override
            protected void onCollisionEnd(Entity a, Entity b) {}
        });

        physics.addCollisionHandler(new CollisionHandler(Type.BALL, Type.PADDLE)
        {
            @Override
            protected void onHitBoxTrigger(Entity a, Entity b, HitBox boxA, HitBox boxB) {}

            @Override
            protected void onCollisionBegin(Entity a, Entity b)
            {
                y = -y;
            }

            @Override
            protected void onCollision(Entity a, Entity b) {}

            @Override
            protected void onCollisionEnd(Entity a, Entity b) {}
        });
    }

    @Override
    protected void initUI()
    {

    }

    @Override
    protected void onUpdate(double v)
    {
        ball.translateX(x);
        ball.translateY(y);
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
