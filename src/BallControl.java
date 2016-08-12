import com.almasb.ents.AbstractControl;
import com.almasb.ents.Entity;
import com.almasb.fxgl.entity.component.PositionComponent;

public class BallControl extends AbstractControl
{
    @Override
    public void onAdded(Entity entity)
    {
        entity.getComponentUnsafe(PositionComponent.class).translate(0, -5);
    }

    @Override
    public void onUpdate(Entity entity, double v)
    {
        entity.getComponentUnsafe(PositionComponent.class).translate(0, -5);
    }
}
