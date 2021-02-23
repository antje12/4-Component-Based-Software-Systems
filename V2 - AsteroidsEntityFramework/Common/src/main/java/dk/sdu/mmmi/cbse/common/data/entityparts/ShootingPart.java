/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 *
 * @author Alexander
 */
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
