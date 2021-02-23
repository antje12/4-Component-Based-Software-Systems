package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public class SplitPart implements EntityPart {

    private boolean shouldSplit = false;
    
    public SplitPart() {
        
    }
    
    public boolean getSplit() {
        return shouldSplit;
    }

    public void setSplit(boolean shouldSplit) {
        this.shouldSplit = shouldSplit;
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
        
    }
}
