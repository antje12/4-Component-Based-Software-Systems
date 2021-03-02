package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public class SplitterPart implements EntityPart {

    private boolean shouldSplit = false;
    
    public SplitterPart() {
        
    }
    
    public boolean getShouldSplit() {
        return shouldSplit;
    }

    public void setShouldSplit(boolean shouldSplit) {
        this.shouldSplit = shouldSplit;
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
        
    }
}
