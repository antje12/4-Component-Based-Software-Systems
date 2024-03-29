package dk.sdu.mmmi.cbse.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Bullet extends SpaceObject {

    private float lifeTime;
    private float lifeTimer;
    private float speed;

    private boolean remove;

    public Bullet(float x, float y, float radians) {
        this.x = x;
        this.y = y;
        this.radians = radians;

        speed = 350;
        dx += MathUtils.cos(radians) * speed;
        dy += MathUtils.sin(radians) * speed;

        lifeTimer = 0;
        lifeTime = 1;
    }

    public boolean shouldRemove() {
        return remove;
    }

    public void update(float dt) {

        // set position
        x += dx * dt;
        y += dy * dt;

        wrap();

        lifeTimer += dt;
        if (lifeTimer > lifeTime) {
            remove = true;
        }
    }

    public void draw(ShapeRenderer sr) {

        sr.setColor(0, 1, 0, 1);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.circle(x - width / 2, y - height / 2, 2);
        sr.end();
    }
}
