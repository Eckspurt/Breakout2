import com.almasb.ents.AbstractControl;
import com.almasb.ents.Entity;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.component.PositionComponent;

public class BallControl extends AbstractControl
{
    private PositionComponent position;

    @Override
    public void onAdded(Entity entity)
    {
        position = Entities.getPosition(entity);
    }

    @Override
    public void onUpdate(Entity entity, double v)
    {
        position.translate(1, 1);
    }
}
