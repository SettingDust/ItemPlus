package com.ItemPlus.Core.v1_0_0.Ability;

import com.ItemPlus.Event.Item.Ability.AbilitySpellEvent;
import com.ItemPlus.Item.Ability.Ability;
import com.ItemPlus.Item.Ability.AbilityInfo;
import com.ItemPlus.ItemPlus;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

import static org.bukkit.Bukkit.getServer;

/**
 * 传送
 * <p/>
 * 向前方传送一段距离。
 * Teleport forwards the specified distance.
 * <p/>
 *
 * @author SettingDust
 */
public final class Teleport extends Ability {
    private final int distance;

    /**
     * 构造技能
     * <p/>
     *
     * @param info     技能信息
     * @param distance 传送距离
     */
    public Teleport(AbilityInfo info, int distance) {
        super(info);
        this.distance = distance;
    }

    @Override
    public void onSpell() {
        if (this.getAbilityInfo().getLocation() != null) {
            AbilitySpellEvent event = new AbilitySpellEvent(this);
            getServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return;
            }

            ItemPlus.getAbilityManager().getAbilityMap().put(this.getUniqueId(), this);
        /*
         * 获取玩家及位置数据。
         * Get the Player and Location state.
         */
            Player player = this.getAbilityInfo().getPlayer();
            World world = player.getWorld();
            Location start = player.getLocation();
            start.setY(start.getY() + 1.6);
            Block lastSafe = world.getBlockAt(start);
            BlockIterator bi = new BlockIterator(player, distance);
        /*
         * 通过循环来判断可传送的位置。
         * Get the valid location through this loop
         */
            while (bi.hasNext()) {
                Block block = bi.next();
                if (!block.getType().isSolid() || (block.getType() == Material.AIR)) {
                    lastSafe = block;
                } else {
                    break;
                }
            }
            Location newLoc = lastSafe.getLocation();
            newLoc.setPitch(start.getPitch());
            newLoc.setYaw(start.getYaw());
        /*
         * 传送辣(≥▽≤)!
         * Teleport(≥▽≤)!
         */
            player.teleport(newLoc);
            world.playEffect(newLoc, Effect.ENDER_SIGNAL, 0);
            world.playSound(newLoc, Sound.ENDERMAN_TELEPORT, 1.0f, 0.3f);
            for (Ability ability : ItemPlus.getAbilityManager().getAbilityMap().values()) {
                if (ability instanceof Teleport) {
                    ability.onEnded();
                }
            }
        }
    }

    /**
     * <p/>
     *
     * @return int
     */
    public int getDistance() {
        return distance;
    }
}
