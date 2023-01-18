package core;

import java.awt.*;

public class HitBox {
    private Rectangle bounds;

    public HitBox(Rectangle bounds) {
        this.bounds = bounds;
    }

    public boolean collidesWith(CollisionBox other){
        return bounds.intersects(other.getBounds());
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
