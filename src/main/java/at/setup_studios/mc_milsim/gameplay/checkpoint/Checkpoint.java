package at.setup_studios.mc_milsim.gameplay.checkpoint;

import at.setup_studios.mc_milsim.gameplay.player.Team;
import at.setup_studios.mc_milsim.util.ModLogger;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

public class Checkpoint {
    private final String name;
    private final int x;
    private final int z;
    private final int maxY;
    private final int r;
    private int orderPos;
    private String status;
    private Team ownedBy;
    private final int maxPoints;
    // idea, -1000 points == team blue, 1000 points team red
    HashMap<Team, Integer> points = new HashMap<>();

    public Checkpoint (String name, int x, int z, int maxY, int radius, int maxPoints, int position) {
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
        if (name == null) {
            ModLogger.error("Name cannot be null");
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (maxPoints <= 0) {
            this.maxPoints = 500;
        }
        else this.maxPoints = maxPoints;
        this.name = name;
        this.x = x;
        this.z = z;
        this.r = radius;
        this.orderPos = position; // Default value indicating not set
        if (maxY > 0) this.maxY = maxY;
        else this.maxY = 99999;
        status = "neutral";
    }

    public void addPoints (Team team, int amount) {
        if (team == getTeamWithHighestValue()) {
            if (points.get(team) == maxPoints) return;
            points.put(team, points.getOrDefault(team, 0)+amount);
            checkOwnedBy();
            checkStatus();
            return;
        }
        if (amount >= points.get(getTeamWithHighestValue())) {
            int diff = amount - points.get(getTeamWithHighestValue());
            points.put(getTeamWithHighestValue(), 0);
            points.put(team, points.getOrDefault(team, 0) + diff);
            return;
        }
        else {
            points.put(getTeamWithHighestValue(), points.get(getTeamWithHighestValue())-amount);
        }
        if (points.get(getTeamWithHighestValue())==0) {
            points.put(team, points.get(team) + amount);
            checkOwnedBy();
            checkStatus();
        }
    }

    public void setOrderPos (int position) {
        this.orderPos = position;
    }

    public int getOrderPos () {
        return orderPos;
    }

    public void checkOwnedBy () {
        if (points.get(getTeamWithHighestValue()) == maxPoints) setOwnedBy(getTeamWithHighestValue());
        else setOwnedBy(null);
    }

    public void checkStatus () {
        if (points.get(getTeamWithHighestValue()) == maxPoints) setStatus(getTeamWithHighestValue().getName());
        else setStatus("Neutral");
    }

    public Team getTeamWithHighestValue() {
        return points.isEmpty() ? null : Collections.max(points.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public void setOwnedBy (Team ownedBy) {
        this.ownedBy = ownedBy;
    }
    public Team getOwnedBy () {
        return ownedBy;
    }
    public void setStatus (String status) {
        this.status = status;
    }
    public String getStatus () {
        return status;
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
