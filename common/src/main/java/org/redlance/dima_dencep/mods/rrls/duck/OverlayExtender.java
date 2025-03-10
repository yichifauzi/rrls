/*
 * Copyright 2023 - 2024 dima_dencep.
 *
 * Licensed under the Open Software License, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *     https://spdx.org/licenses/OSL-3.0.txt
 */

package org.redlance.dima_dencep.mods.rrls.duck;

import org.redlance.dima_dencep.mods.rrls.utils.OverlayHelper;
import net.minecraft.client.gui.GuiGraphics;

@SuppressWarnings("unused")
public interface OverlayExtender {
    OverlayHelper.State rrls$getState();
    void rrls$setState(OverlayHelper.State state);

    default void rrls$miniRender(GuiGraphics graphics, float partialTick) {
        throw new UnsupportedOperationException("The '" + getClass().getCanonicalName() + "' overlay doesn't have a mini-render!");
    }

    default void rrls$resetProgress() {
        // NO-OP
    }
}
