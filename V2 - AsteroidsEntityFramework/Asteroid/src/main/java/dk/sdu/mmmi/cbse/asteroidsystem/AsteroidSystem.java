package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.SplitPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class AsteroidSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        if (world.getEntities(Asteroid.class).size() == 0) {
            Entity asteroid = AsteroidPlugin.createAsteroid(gameData, 3);
            world.addEntity(asteroid);
        }
        
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
                       
            PositionPart positionPart = asteroid.getPart(PositionPart.class);
            MovingPart movingPart = asteroid.getPart(MovingPart.class);
            LifePart lifePart = asteroid.getPart(LifePart.class);
            SplitPart splitPart = asteroid.getPart(SplitPart.class);

            movingPart.setUp(true);            
            
            movingPart.process(gameData, asteroid);
            positionPart.process(gameData, asteroid);
            lifePart.process(gameData, asteroid);
            splitPart.process(gameData, asteroid);
            
            if (splitPart.getSplit()) {
                int size = lifePart.getLife();
                
                float x = positionPart.getX();
                float y = positionPart.getY();
                
                Entity child1 = AsteroidPlugin.createAsteroid(gameData, size, x, y);
                Entity child2 = AsteroidPlugin.createAsteroid(gameData, size, x, y);
                world.addEntity(child1);
                world.addEntity(child2);
                
                world.removeEntity(asteroid);
            }
            
            if (lifePart.getLife() <= 0) {
                world.removeEntity(asteroid);
            }

            updateShape(asteroid);
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();

        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        LifePart lifePart = entity.getPart(LifePart.class);
        int size = lifePart.getLife();
        
        shapex[0] = (float) (x + Math.cos(radians) * 8 * size);
        shapey[0] = (float) (y + Math.sin(radians) * 8 * size);

        shapex[1] = (float) (x + Math.cos(radians + 3.1415f / 2) * 8 * size);
        shapey[1] = (float) (y + Math.sin(radians + 3.1415f / 2) * 8 * size);

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 8 * size);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 8 * size);

        shapex[3] = (float) (x + Math.cos(radians - 3.1415f / 2) * 8 * size);
        shapey[3] = (float) (y + Math.sin(radians - 3.1415f / 2) * 8 * size);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
}
