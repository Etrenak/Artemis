/*
 * Copyright © Wynntils 2022-2023.
 * This file is released under LGPLv3. See LICENSE for full license details.
 */
package com.wynntils.screens.base.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import com.wynntils.utils.mc.ComponentUtils;
import com.wynntils.utils.mc.TooltipUtils;
import com.wynntils.utils.render.FontRenderer;
import com.wynntils.utils.render.RenderUtils;
import com.wynntils.utils.render.Texture;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;

public class BasicTexturedButton extends WynntilsButton {
    private final Texture texture;

    private final Consumer<Integer> onClick;
    private List<Component> tooltip;

    private boolean renderTooltipAboveMouse;
    private boolean scaleTexture;

    public BasicTexturedButton(
            int x, int y, int width, int height, Texture texture, Consumer<Integer> onClick, List<Component> tooltip) {
        this(x, y, width, height, texture, onClick, tooltip, true, false);
    }

    public BasicTexturedButton(
            int x,
            int y,
            int width,
            int height,
            Texture texture,
            Consumer<Integer> onClick,
            List<Component> tooltip,
            boolean renderTooltipAboveMouse,
            boolean scaleTexture) {
        super(x, y, width, height, Component.literal("Basic Button"));
        this.texture = texture;
        this.onClick = onClick;
        this.renderTooltipAboveMouse = renderTooltipAboveMouse;
        this.scaleTexture = scaleTexture;
        this.setTooltip(tooltip);
    }

    @Override
    public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        if (scaleTexture) {
            RenderUtils.drawScalingTexturedRect(
                    poseStack,
                    texture.resource(),
                    this.getX(),
                    this.getY(),
                    0,
                    getWidth(),
                    getHeight(),
                    texture.width(),
                    texture.height());
        } else {
            RenderUtils.drawTexturedRect(poseStack, texture, this.getX(), this.getY());
        }

        if (this.isHovered) {
            int renderY = renderTooltipAboveMouse
                    ? mouseY - TooltipUtils.getToolTipHeight(TooltipUtils.componentToClientTooltipComponent(tooltip))
                    : mouseY;

            RenderUtils.drawTooltipAt(
                    poseStack,
                    mouseX,
                    renderY,
                    0,
                    tooltip,
                    FontRenderer.getInstance().getFont(),
                    true);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!isMouseOver(mouseX, mouseY)) return false;

        onClick.accept(button);

        return true;
    }

    @Override
    public void onPress() {}

    public void setTooltip(List<Component> newTooltip) {
        tooltip = ComponentUtils.wrapTooltips(newTooltip, 250);
    }
}
