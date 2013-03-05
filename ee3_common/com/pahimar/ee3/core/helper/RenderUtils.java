package com.pahimar.ee3.core.helper;

import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import org.lwjgl.opengl.GL11;

import com.pahimar.ee3.lib.Sprites;

public class RenderUtils {

    private static int rotationAngle = 0;

    public static void renderRotatingBlockIntoGUI(FontRenderer fontRenderer, RenderEngine renderEngine, ItemStack stack, int x, int y, float zLevel, float scale) {

        RenderBlocks renderBlocks = new RenderBlocks();

        Block block = Block.blocksList[stack.itemID];
        renderEngine.func_98187_b(block.getTextureFile());
        GL11.glPushMatrix();
        GL11.glTranslatef((float) (x - 2), (float) (y + 3), -3.0F + zLevel);
        GL11.glScalef(10.0F, 10.0F, 10.0F);
        GL11.glTranslatef(1.0F, 0.5F, 1.0F);
        GL11.glScalef(1.0F * scale, 1.0F * scale, -1.0F);
        GL11.glRotatef(210.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(0F + 1 * rotationAngle, 0.0F, 1.0F, 0.0F);
        rotationAngle = (rotationAngle + 1) % 360;

        int var10 = Item.itemsList[stack.itemID].getColorFromItemStack(stack, 0);
        float var16 = (float) (var10 >> 16 & 255) / 255.0F;
        float var12 = (float) (var10 >> 8 & 255) / 255.0F;
        float var13 = (float) (var10 & 255) / 255.0F;

        GL11.glColor4f(var16, var12, var13, 1.0F);

        GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        renderBlocks.useInventoryTint = true;
        renderBlocks.renderBlockAsItem(block, stack.getItemDamage(), 1.0F);
        renderBlocks.useInventoryTint = true;
        GL11.glPopMatrix();
    }
    
    public static void renderItemIntoGUI(FontRenderer fontRenderer, RenderEngine renderEngine, ItemStack itemStack, int x, int y, float opacity, float scale) {

        Icon icon = itemStack.getIconIndex();
        GL11.glDisable(GL11.GL_LIGHTING);
    	renderEngine.func_98187_b(Sprites.ITEM_SPRITE_SHEET);
        int overlayColour = itemStack.getItem().getColorFromItemStack(itemStack, 0);
        float red = (float)(overlayColour >> 16 & 255) / 255.0F;
        float green = (float)(overlayColour >> 8 & 255) / 255.0F;
        float blue = (float)(overlayColour & 255) / 255.0F;
        GL11.glColor4f(red, green, blue, opacity);       
        drawTexturedQuad(x, y, icon, 16 * scale, 16 * scale, -90);
        GL11.glEnable(GL11.GL_LIGHTING);

    }
    
    public static void drawTexturedQuad(int x, int y, Icon icon, float width, float height, double zLevel) {
    	
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + height), zLevel, (double)icon.func_94209_e(), (double)icon.func_94210_h());
        tessellator.addVertexWithUV((double)(x + width), (double)(y + height), zLevel, (double)icon.func_94212_f(), (double)icon.func_94210_h());
        tessellator.addVertexWithUV((double)(x + width), (double)(y + 0), zLevel, (double)icon.func_94212_f(), (double)icon.func_94206_g());
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), zLevel, (double)icon.func_94209_e(), (double)icon.func_94206_g());
        tessellator.draw();
    }
}
