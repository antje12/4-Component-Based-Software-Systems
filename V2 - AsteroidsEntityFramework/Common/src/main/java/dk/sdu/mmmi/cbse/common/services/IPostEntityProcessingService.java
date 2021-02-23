package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * Entity processing methods to be called post game cycles. 
 * Used for post processing based on the result of all the entities main processing.
 * 
 * Pre-conditions: A component of the right type is instantiated and registered on the world object
 * 
 * Post-conditions: The component of the right type is processed according to the systems purpose
 */
public interface IPostEntityProcessingService  {
        void process(GameData gameData, World world);
}
