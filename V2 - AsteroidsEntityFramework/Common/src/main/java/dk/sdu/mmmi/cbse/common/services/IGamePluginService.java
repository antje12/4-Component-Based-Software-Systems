package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * Interface used to define game plugins which is allowed to be installed/uninstalled during runtime
 * All plugins / components must inherit this type
 * The startup method must instantiate and register the plugin object
 * The stop method must de-register the plugin object
 * 
 * Pre-conditions: The gameData and world objects are instantiated
 * 
 * Post-conditions: An instantiation of the plugin object is 
 * registered on the world object
 */
public interface IGamePluginService {
    void start(GameData gameData, World world);

    void stop(GameData gameData, World world);
}
