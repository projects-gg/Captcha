package me.kyllian.captcha.spigot.player;

import me.kyllian.captcha.spigot.captchas.Captcha;
import me.kyllian.captcha.spigot.captchas.SolveState;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

public class PlayerData {
    private Captcha assignedCaptcha;
    private boolean moved;
    private boolean forced;
    private boolean passed;
    private ItemStack backupItem;
    private Location backupLocation;
    private int fails;
    private BukkitTask delayedTask;
    private String executeAfterFinish;

    public boolean hasAssignedCaptcha() {
        return assignedCaptcha != null;
    }

    public void setAssignedCaptcha(Captcha assignedCaptcha) {
        this.assignedCaptcha = assignedCaptcha;
    }

    public void removeAssignedCaptcha() {
        this.assignedCaptcha = null;
    }

    public Captcha getAssignedCaptcha() {
        return assignedCaptcha;
    }

    public int getFails() {
        return fails;
    }

    public void handleSolveState(SolveState solveState) {
        if (solveState == SolveState.FAIL || solveState == SolveState.LEAVE) {
            fail();
        } else if (solveState == SolveState.OK) {
            passed = true;
        }
    }

    public void fail() {
        passed = false;
        this.fails++;
    }

    public void setBackupItem(ItemStack backupItem) {
        this.backupItem = backupItem;
    }

    public ItemStack getBackupItem() {
        return backupItem;
    }

    public void setDelayedTask(BukkitTask delayedTask) {
        this.delayedTask = delayedTask;
    }

    public void cancel() {
        if (delayedTask == null) return;
        delayedTask.cancel();
    }

    public boolean hasPassed() {
        return passed;
    }

    public boolean hasMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public Location getBackupLocation() {
        return backupLocation;
    }

    public void setBackupLocation(Location backupLocation) {
        this.backupLocation = backupLocation;
    }

    public boolean isForced() {
        return forced;
    }

    public void setForced(boolean forced) {
        this.forced = forced;
    }

    public String getExecuteAfterFinish() {
        return executeAfterFinish;
    }

    public void setExecuteAfterFinish(String executeAfterFinish) {
        this.executeAfterFinish = executeAfterFinish;
    }
}
