package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public class ShootingPart implements EntityPart {
    
    private boolean shooting;
    private final String shooterId;
    
    public ShootingPart(String shooterId) {
        this.shooterId = shooterId.toString();
    }

    public String getId() {
        return this.shooterId;
    }
    
    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }
    
    public boolean isShooting() {
        return this.shooting;
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
        
    }
}
