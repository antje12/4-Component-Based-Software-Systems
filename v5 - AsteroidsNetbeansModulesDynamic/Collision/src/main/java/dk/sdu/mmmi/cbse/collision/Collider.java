package dk.sdu.mmmi.cbse.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.SplitterPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IPostEntityProcessingService.class),})
public class Collider implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity e1 : world.getEntities()) {
            for (Entity e2 : world.getEntities()) {
                if (e1.getID().equals(e2.getID())) {
                    continue;
                }

                LifePart e1l = e1.getPart(LifePart.class);
                LifePart e2l = e2.getPart(LifePart.class);

                if (circleCollision(e1, e2)) {

                    // test if player/enemy
                    ShootingPart e1shot = e1.getPart(ShootingPart.class);
                    ShootingPart e2shot = e2.getPart(ShootingPart.class);

                    // test if asteroid                
                    SplitterPart e1split = e1.getPart(SplitterPart.class);
                    SplitterPart e2split = e2.getPart(SplitterPart.class);

                    // asteroid hit asteroid
                    if (e1split != null && e2split != null) {
                        continue;
                    }

                    // player hit asteroid
                    if (e1shot != null && e2split != null) {
                        e1l.setLife(0);
                    // player hit asteroid
                    } else if (e2shot != null && e1split != null) {
                        e2l.setLife(0);
                    // something else colided
                    } else {
                        e1l.setIsHit(true);
                        e2l.setIsHit(true);
                    }
                }
            }
        }
    }

    private boolean circleCollision(Entity entity1, Entity entity2) {
        PositionPart e1p = entity1.getPart(PositionPart.class);
        PositionPart e2p = entity2.getPart(PositionPart.class);

        float dx = e1p.getX() - e2p.getX();
        float dy = e1p.getY() - e2p.getY();

        // a^2 + b^2 = c^2
        // c = sqrt(a^2 + b^2)
        double distance = Math.sqrt(dx * dx + dy * dy);

        // if radius overlap
        if (distance < entity1.getRadius() + entity2.getRadius()) {
            // Collision!
            return true;
        }

        return false;
    }
}
