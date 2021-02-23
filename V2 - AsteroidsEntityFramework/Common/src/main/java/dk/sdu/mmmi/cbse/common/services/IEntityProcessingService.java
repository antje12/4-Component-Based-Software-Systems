package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * Interface defining Entity Processing methods. 
 * Used by entities to ensure they are processed by game cycles.
 * All component systems (movement, shape, ...) must inherit this type
 * The process method activates / runs the systems purpose / function
 * 
 * Pre-conditions: A component of the right type is instantiated and registered on the world object
 * 
 * Post-conditions: The component of the right type is processed according to the systems purpose
 */
public interface IEntityProcessingService {

    void process(GameData gameData, World world);
}
