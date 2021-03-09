package dk.sdu.mmmi.cbse.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.TimerPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class BulletSystem implements IEntityProcessingService {

    private Entity bullet;
    
    @Override
    public void process(GameData gameData, World world) {

        for (Entity entity : world.getEntities()) {
            ShootingPart shootingPart = entity.getPart(ShootingPart.class);
            if (shootingPart != null && shootingPart.isShooting()) {
                PositionPart positionPart = entity.getPart(PositionPart.class);
                bullet = createBullet(positionPart.getX(), positionPart.getY(), positionPart.getRadians(), shootingPart.getId());
                shootingPart.setShooting(false);
                world.addEntity(bullet);
            }
        }
        
        for (Entity bullet : world.getEntities(Bullet.class)) {
            
            PositionPart positionPart = bullet.getPart(PositionPart.class);
            MovingPart movingPart = bullet.getPart(MovingPart.class);
            LifePart lifePart = bullet.getPart(LifePart.class);
            TimerPart timerPart = bullet.getPart(TimerPart.class);

            movingPart.setUp(true);
            
            movingPart.process(gameData, bullet);
            positionPart.process(gameData, bullet);
            lifePart.process(gameData, bullet);
            timerPart.process(gameData, bullet);
            
            if (lifePart.getLife() <= 0) {
                world.removeEntity(bullet);
            }

            updateShape(bullet);
        }
    }

    private Entity createBullet(float x, float y, float radians, String uuid) {
        Entity b = new Bullet();

        b.add(new MovingPart(0, 10000, 500, 0));
        
        x = (float) (x + Math.cos(radians) * 10);
        y = (float) (y + Math.sin(radians) * 10);
        
        b.add(new PositionPart(x, y, radians));
        b.add(new LifePart(1));
        b.add(new TimerPart(1));
        b.setRadius(2);

        return b;
    }
    
    private void updateShape(Entity entity) {
        float[] shapex = new float[4];
        float[] shapey = new float[4];
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * entity.getRadius());
        shapey[0] = (float) (y + Math.sin(radians) * entity.getRadius());

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * entity.getRadius());
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * entity.getRadius());

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * entity.getRadius() * 0.5);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * entity.getRadius() * 0.5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * entity.getRadius());
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * entity.getRadius());

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
}
