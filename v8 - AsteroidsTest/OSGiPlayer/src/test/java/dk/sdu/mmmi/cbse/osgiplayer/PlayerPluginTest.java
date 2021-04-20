package dk.sdu.mmmi.cbse.osgiplayer;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

public class PlayerPluginTest {
    
    public PlayerPluginTest() {
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
     * Test of start method, of class PlayerPlugin.
     */
    @Test
    public void testStart() { // using mock
        System.out.println("start");
        
        GameData gameData = mock(GameData.class);
        when(gameData.getDisplayWidth()).thenReturn(800);
        when(gameData.getDisplayHeight()).thenReturn(600);
        
        World world = mock(World.class);
        when(world.addEntity(any(Entity.class))).thenReturn("1");
        
        PlayerPlugin instance = new PlayerPlugin();
        instance.start(gameData, world);

        verify(gameData).getDisplayWidth();
        verify(gameData).getDisplayHeight();
        
        verify(world).addEntity(any(Entity.class));
    }

    /**
     * Test of stop method, of class PlayerPlugin.
     */
    @Test
    public void testStop() { // using mock
        System.out.println("stop");        
        
        List<Entity> testPlayers = new ArrayList<Entity>();
        testPlayers.add(new Player());
        
        GameData gameData = mock(GameData.class); 
        
        World world = mock(World.class);
        when(world.getEntities(Player.class)).thenReturn(testPlayers);
        
        PlayerPlugin instance = new PlayerPlugin();
        instance.stop(gameData, world);
        
        verify(world).getEntities(Player.class);
        verify(world).removeEntity(any(Entity.class));
    }
}
