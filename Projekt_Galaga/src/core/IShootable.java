package core;

import java.util.List;

public interface IShootable extends IColisionable {
    public List<IColisionable> getMissiles();
}
