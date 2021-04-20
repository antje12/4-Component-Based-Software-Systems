package dk.sdu.mmmi.cbse.asteroidsnetbeansmodules;

import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.io.IOException;
import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import junit.framework.Test;
import org.netbeans.junit.NbModuleSuite;
import org.netbeans.junit.NbTestCase;
import org.openide.util.Lookup;

public class ApplicationTest extends NbTestCase {

    private final String ADD_ASTEROID_UPDATES_FILE = 
            "d:\\Git\\Asteroids\\v8 - AsteroidsNetbeansTest\\asteroid_updates.xml";
    private final String REM_ASTEROID_UPDATES_FILE = 
            "d:\\Git\\Asteroids\\v8 - AsteroidsNetbeansTest\\no_asteroid_updates.xml";
    private final String UPDATES_FILE = 
            "d:\\Git\\Asteroids\\v8 - AsteroidsNetbeansTest\\netbeans_modules\\updates.xml";

    public static Test suite() {
        return NbModuleSuite.createConfiguration(ApplicationTest.class).
                gui(false).
                failOnMessage(Level.WARNING). // works at least in RELEASE71
                failOnException(Level.INFO).
                enableClasspathModules(false).
                clusters(".*").
                suite(); // RELEASE71+, else use NbModuleSuite.create(NbModuleSuite.createConfiguration(...))
    }

    public ApplicationTest(String n) {
        super(n);
    }

    public void testApplication() throws InterruptedException, IOException {
        // pass if there are merely no warnings/exceptions
        /* Example of using Jelly Tools (additional test dependencies required) with gui(true):
        new ActionNoBlock("Help|About", null).performMenu();
        new NbDialogOperator("About").closeByButton();
         */
        
        // setup
        List<IEntityProcessingService> processors = new CopyOnWriteArrayList<>();
        List<IGamePluginService> plugins = new CopyOnWriteArrayList<>();
        waitForUpdate(processors, plugins);

        // test that no modules are loaded
        assertEquals("No processors", 0, processors.size());
        assertEquals("No plugins", 0, plugins.size());

        // load Asteroid module
        copy(get(ADD_ASTEROID_UPDATES_FILE), get(UPDATES_FILE), REPLACE_EXISTING);
        waitForUpdate(processors, plugins);

        // test that Asteroid module is loaded
        assertEquals("One processor", 1, processors.size());
        assertEquals("One plugin", 1, plugins.size());

        // unload Asteroid module
        copy(get(REM_ASTEROID_UPDATES_FILE), get(UPDATES_FILE), REPLACE_EXISTING);
        waitForUpdate(processors, plugins);

        // test that no modules are loaded
        assertEquals("No processors", 0, processors.size());
        assertEquals("No plugins", 0, plugins.size());
    }

    private void waitForUpdate(
            List<IEntityProcessingService> processors,
            List<IGamePluginService> plugins) throws InterruptedException {

        Thread.sleep(10000);

        processors.clear();
        processors.addAll(Lookup.getDefault().lookupAll(IEntityProcessingService.class));

        plugins.clear();
        plugins.addAll(Lookup.getDefault().lookupAll(IGamePluginService.class));
    }
}
