package scclient.mods.impl;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import scclient.hud.ScreenPosition;
import scclient.mods.ModDraggable;

public class ArmorStatus extends ModDraggable
{
    @Override
    public int getWidth()
    {
        return 64;
    }

    @Override
    public int getHeight()
    {
        return 64;
    }

    @Override
    public void render(ScreenPosition pos)
    {
        for (int i = 0; i < Minecraft.getMinecraft().player.inventory.armorInventory.size(); i++)
        {
            ItemStack itemStack = Minecraft.getMinecraft().player.inventory.armorInventory.get(i);
            renderItemStack(pos, i, itemStack);
        }
    }

    @Override
    public void renderDummy(ScreenPosition pos)
    {
        renderItemStack(pos, 3, new ItemStack(Items.DIAMOND_HELMET));
        renderItemStack(pos, 2, new ItemStack(Items.DIAMOND_CHESTPLATE));
        renderItemStack(pos, 1, new ItemStack(Items.DIAMOND_LEGGINGS));
        renderItemStack(pos, 0, new ItemStack(Items.DIAMOND_BOOTS));
    }

    private void renderItemStack(ScreenPosition pos, int i, ItemStack is)
    {
        if (is == null)
        {
            return;
        }

        int yAdd = (-16 * i) + 48;

        if (is.getItem().isDamageable())
        {
            double damage = ((is.getMaxDamage() - is.getItemDamage()) / (double) is.getMaxDamage()) * 100;
            font.drawString(String.format("%.2f%%", damage), pos.getAbsoluteX() + 20, pos.getAbsoluteY() + yAdd + 5, -1);
        }

        RenderHelper.enableGUIStandardItemLighting();
        Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(is, pos.getAbsoluteX(), pos.getAbsoluteY() + yAdd);
    }
}
