package dk.sdu.mmmi.cbse.core.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.core.managers.GameInputProcessor;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Game implements ApplicationListener {

    private static OrthographicCamera cam;
    private ShapeRenderer sr;

    private final GameData gameData = new GameData();
    private List<IEntityProcessingService> entityProcessors = new ArrayList<>();
    private static List<IPostEntityProcessingService> postEntityProcessors = new ArrayList<>();
    private World world = new World();

    @Override
    public void create() {

        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        sr = new ShapeRenderer();

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));

        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        loadPlugins(context);
        loadEntityProcessors(context);
    }

    public void setCollider(IPostEntityProcessingService collider)
    {
        postEntityProcessors.add(collider);
    }
    
    private void loadPlugins(ApplicationContext context) {
        IGamePluginService plugin;
        plugin = (IGamePluginService) context.getBean("AsteroidPlugin");
        plugin.start(gameData, world);
        plugin = (IGamePluginService) context.getBean("BulletPlugin");
        plugin.start(gameData, world);
        plugin = (IGamePluginService) context.getBean("EnemyPlugin");
        plugin.start(gameData, world);
        plugin = (IGamePluginService) context.getBean("PlayerPlugin");
        plugin.start(gameData, world);
    }

    private void loadEntityProcessors(ApplicationContext context) {
        IEntityProcessingService service;
        service = (IEntityProcessingService) context.getBean("AsteroidSystem");
        entityProcessors.add(service);
        service = (IEntityProcessingService) context.getBean("BulletSystem");
        entityProcessors.add(service);
        service = (IEntityProcessingService) context.getBean("EnemyControlSystem");
        entityProcessors.add(service);
        service = (IEntityProcessingService) context.getBean("PlayerControlSystem");
        entityProcessors.add(service);
    }

    @Override
    public void render() {
        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameData.setDelta(Gdx.graphics.getDeltaTime());
        update();
        draw();
        gameData.getKeys().update();
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : entityProcessors) {
            entityProcessorService.process(gameData, world);
        }

        for (IPostEntityProcessingService postEntityProcessorService : postEntityProcessors) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    private void draw() {
        for (Entity entity : world.getEntities()) {

            sr.setColor(1, 1, 1, 1);
            sr.begin(ShapeRenderer.ShapeType.Line);

            float[] shapex = entity.getShapeX();
            float[] shapey = entity.getShapeY();

            for (int i = 0, j = shapex.length - 1; i < shapex.length; j = i++) {
                sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
            }

            sr.end();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
