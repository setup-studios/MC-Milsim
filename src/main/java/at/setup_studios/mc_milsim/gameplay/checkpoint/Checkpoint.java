package at.setup_studios.mc_milsim.gameplay.checkpoint;

import at.setup_studios.mc_milsim.util.ModLogger;

public class Checkpoint {
    private final int x;
    private final int z;
    private final int maxY;
    private final int r;

    public Checkpoint (int x, int z, int radius, int maxY) {
        if (x <= 0) {
            ModLogger.error("X coordinates cannot be 0");
            throw new IllegalArgumentException("X coordinates cannot be 0");
        }
        if (z <= 0) {
            ModLogger.error("Z coordinates cannot be 0");
            throw new IllegalArgumentException("Z coordinates cannot be 0");
        }
        if (radius <= 0) {
            ModLogger.error("Radius cannot be 0");
            throw new IllegalArgumentException("Radius cannot be 0");
        }
        this.x = x;
        this.z = z;
        this.r = radius;
        if (maxY > 0) this.maxY = maxY;
        else this.maxY = 99999;
    }

    public int getX() {
        return x;
    }
    public int getZ() {
        return z;
    }
    public int getMaxY() {
        return maxY;
    }
    public int getRadius() {
        return r;
    }
}
