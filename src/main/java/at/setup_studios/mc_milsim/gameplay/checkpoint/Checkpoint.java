package at.setup_studios.mc_milsim.gameplay.checkpoint;

import at.setup_studios.mc_milsim.gameplay.player.Team;
import at.setup_studios.mc_milsim.util.ModLogger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Checkpoint {
    private final int x;
    private final int z;
    private final int maxY;
    private final int r;
    private String status;
    private Team ownedBy;
    private final int maxPoints;
    // idea, -1000 points == team blue, 1000 points team red
    HashMap<Team, Integer> points = new HashMap<>();

    public Checkpoint (int x, int z, int radius, int maxY, int maxPoints) {
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
        if (maxPoints <= 0) {
            this.maxPoints = 500;
        }
        else this.maxPoints = maxPoints;
        this.x = x;
        this.z = z;
        this.r = radius;
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
        if (amount >= points.get(getTeamWithHighestValue())) points.put(getTeamWithHighestValue(), 0);
        else points.put(getTeamWithHighestValue(), points.get(getTeamWithHighestValue())-amount);
        points.put(team, points.get(team)+amount);
        checkOwnedBy();
        checkStatus();
        return;
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
