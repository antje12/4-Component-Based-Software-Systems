package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.SplitterPart;
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
            SplitterPart splitPart = asteroid.getPart(SplitterPart.class);

            movingPart.setUp(true);            
            
            movingPart.process(gameData, asteroid);
            positionPart.process(gameData, asteroid);
            lifePart.process(gameData, asteroid);
            splitPart.process(gameData, asteroid);
            
            if (splitPart.getShouldSplit()) {
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
        
        int numPoints = 6;
        float angle = 0;
        
        float[] shapex = new float[6];
        float[] shapey = new float[6];
        
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        LifePart lifePart = entity.getPart(LifePart.class);
        int size = lifePart.getLife();
        
        for (int i = 0; i < numPoints; i++) {
            shapex[i] = x + (float) Math.cos(angle + radians) * 8 * size;
            shapey[i] = y + (float) Math.sin(angle + radians) * 8 * size;
            angle += 2 * 3.1415f / numPoints;
        }
        
        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
}
