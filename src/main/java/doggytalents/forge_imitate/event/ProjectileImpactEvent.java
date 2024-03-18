package doggytalents.forge_imitate.event;

import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;

public class ProjectileImpactEvent extends Event {
    
    private Projectile projectile;
    private HitResult hit;

    public ProjectileImpactEvent(Projectile projectile, HitResult hit) {
        this.projectile = projectile;
        this.hit = hit;
    }

    public Projectile getProjectile() {
        return this.projectile;
    }

    public HitResult getRayTraceResult() {
        return this.hit;
    }

}
