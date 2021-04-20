package dk.sdu.mmmi.cbse.osgiplayer;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerSystemTest {

    public PlayerSystemTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of process method, of class PlayerSystem.
     */
    @Test
    public void testProcess() { // not using mock
        System.out.println("process");

        GameData gameData = new GameData();
        gameData.getKeys().setKey(GameKeys.UP, true);
        gameData.setDelta(1);
        gameData.setDisplayWidth(800);
        gameData.setDisplayHeight(600);

        World world = new World();
        PlayerPlugin playerPlugin = new PlayerPlugin();
        playerPlugin.start(gameData, world);

        Entity player = world.getEntities(Player.class).get(0);
        PositionPart pos = player.getPart(PositionPart.class);

        float startX = pos.getX();
        float startY = pos.getY();

        PlayerSystem instance = new PlayerSystem();
        instance.process(gameData, world);

        float endX = pos.getX();
        float endY = pos.getY();

        assertEquals((int) startX, (int) endX);
        assertNotEquals((int) startY, (int) endY);
    }
}
