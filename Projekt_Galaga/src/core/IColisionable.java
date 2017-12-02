package core;

import java.awt.Rectangle;

public interface IColisionable extends IGameObject {
    public int getX();
    public int getY();
    public Rectangle getBounds();
    public int getLives();
    public void hit();

}
