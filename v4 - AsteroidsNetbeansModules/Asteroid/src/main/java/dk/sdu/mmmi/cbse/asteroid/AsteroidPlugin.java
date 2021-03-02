package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.SplitterPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class AsteroidPlugin implements IGamePluginService
{
  private Entity asteroid;

    @Override
    public void start(GameData gameData, World world) {
       Entity asteroid = createAsteroid(gameData, 3);
       world.addEntity(asteroid);
    }

    public static Entity createAsteroid(GameData gameData, int size) {
        Random rand = new Random();
        // display / 12
        float x = gameData.getDisplayWidth() / (rand.nextInt(12) + 1);
        float y = gameData.getDisplayHeight() / (rand.nextInt(12) + 1);
        return createAsteroid(gameData, size, x, y);
    }

    public static Entity createAsteroid(GameData gameData, int size, float x, float y) {

        float deacceleration = 10;
        float acceleration = 50;
        float maxSpeed = 100;
        float rotationSpeed = 5;

        Random rand = new Random();
        
        // direction = 1/12 of unity circle
        float radians = (3.1415f / 6) * (rand.nextInt(12) + 1);

        Entity asteroid = new Asteroid();
        asteroid.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        asteroid.add(new PositionPart(x, y, radians));
        asteroid.add(new LifePart(size));
        asteroid.add(new SplitterPart());
        asteroid.setRadius(8 * size);

        return asteroid;
    }
    
    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(asteroid);
    }
}
