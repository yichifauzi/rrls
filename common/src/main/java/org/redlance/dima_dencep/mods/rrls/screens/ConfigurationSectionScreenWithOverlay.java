package org.redlance.dima_dencep.mods.rrls.screens;

import com.electronwill.nightconfig.core.UnmodifiableConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ConfigurationSectionScreenWithOverlay extends ConfigurationScreen.ConfigurationSectionScreen implements ReloadInstance {
    protected final LoadingOverlay overlay;
    protected float currentProgress;

    public ConfigurationSectionScreenWithOverlay(Context parentContext, Screen parent, Map<String, Object> valueSpecs, String key, Set<? extends UnmodifiableConfig.Entry> entrySet, Component title) {
        super(parentContext, parent, valueSpecs, key, entrySet, title);
        this.overlay = new LoadingOverlay(Minecraft.getInstance(), this, th -> {}, false);
    }

    public void resetProgress() {
        this.currentProgress = 0F;
        this.overlay.rrls$resetProgress();
        if (this.minecraft != null && this.minecraft.overlay == null) {
            this.minecraft.setOverlay(this.overlay);
        }
    }

    @Override
    protected void onChanged(@NotNull String key) {
        super.onChanged(key);
        resetProgress();
    }

    @Override
    public @NotNull ConfigurationSectionScreenWithOverlay rebuild() {
        super.rebuild();
        return this;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.minecraft != null && this.minecraft.overlay == this.overlay) {
            this.currentProgress += 0.1F;
        }
    }

    @Override
    public void added() {
        super.added();
        resetProgress();
    }

    @Override
    public void removed() {
        super.removed();
        if (this.minecraft != null && this.minecraft.overlay == this.overlay) {
            this.minecraft.setOverlay(null);
        }
    }

    @Override
    public @NotNull CompletableFuture<?> done() {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public float getActualProgress() {
        return Mth.clamp(this.currentProgress, 0.0F, 1.0F);
    }

    @Override
    public boolean isDone() {
        return this.minecraft != null && this.minecraft.overlay == this.overlay && this.currentProgress > 1.5F;
    }
}
