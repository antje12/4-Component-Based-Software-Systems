package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public class LifePart implements EntityPart {

    private int life;
    private boolean isHit = false;

    public LifePart(int life) {
        this.life = life;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
        if (isHit) {
            life = life - 1;
            isHit = false;
            
            SplitterPart split = entity.getPart(SplitterPart.class);
            if (split != null) {
                boolean shouldSplit = life > 0;
                split.setShouldSplit(shouldSplit);
            }
        }
    }
}
