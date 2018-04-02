/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.mount;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.doll.IDollType;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.util.CsmConfiguration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

import java.util.Arrays;
import java.util.Locale;

public enum EnumTurtleType
        implements IDollType
{
    COBBLE(true,     40.0F, 0.8F, false, 0x4D3225, 0x919191, "cobble1", "cobble2"),
    MOSSY(true,      45.0F, 0.7F, false, 0x4D3225, 0x86A384, "mossy"),
    NETHERRACK(true, 35.0F, 0.8F, true,  0x4D3225, 0xCC3131, "netherrack"),
    MELON(true,      25.0F, 0.9F, false, 0xED5A5A, 0x0DDB11, "melon1", "melon2"),
    SANDSTONE(true,  30.0F, 0.8F, false, 0x4D3225, 0xFFF5AB, "sandstone1", "sandstone2", "sandstone3"),
    ENDSTONE(true,   45.0F, 0.8F, false, 0x4D3225, 0xDBD5A2, "endstone"),
    PUMPKIN(true,    25.0F, 0.9F, false, 0x4D3225, 0xF2BD0F, "pumpkin1", "pumpkin2"),
    LAPIS(true,      35.0F, 0.9F, false, 0x4D3225, 0x270AC9, "lapis"),
    CAKE(true,       30.0F, 0.9F, false, "cake", 0xAD5D0C, 0xFFFFFF, "cake"),

    KAWAKO(false, 50.0F, 0.85F, true, 0x0, 0x0, "spec_kawako"),
    UNKNOWN(false, 0.0F, 0.0F, false, 0x0, 0x0);

    public static final EnumTurtleType[] VALUES = values();

    public float maxHealth;
    public float movementFactor;
    public boolean fireproof;
    public final boolean visible;
    public final int itemColorBody;
    public final int itemColorShell;
    public final String cstItemSuffix;
    public final ResourceLocation[] textures;

    EnumTurtleType(boolean visible, float maxHealth, float movementFactor, boolean fireproof, String cstItemSuffix, int itemColorBody, int itemColorShell, String... textures) {
        if (textures == null) {
            textures = new String[0];
        }

        this.visible = visible;
        this.maxHealth = maxHealth;
        this.movementFactor = movementFactor;
        this.fireproof = fireproof;
        this.itemColorBody = itemColorBody;
        this.itemColorShell = itemColorShell;
        this.textures = Arrays.stream(textures).map(s -> new ResourceLocation(CsmConstants.ID, String.format("textures/entities/mount/turtles/%s.png", s)))
                              .toArray(ResourceLocation[]::new);
        this.cstItemSuffix = cstItemSuffix;
    }

    EnumTurtleType(boolean visible, float maxHealth, float movementFactor, boolean fireproof, int itemColorBody, int itemColorShell, String... textures) {
        this(visible, maxHealth, movementFactor, fireproof, null, itemColorBody, itemColorShell, textures);
    }

    @Override
    public boolean isVisible() {
        return this.visible;
    }

    @Override
    public boolean isValid() {
        return this != UNKNOWN;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public int getItemColor() {
        return this.itemColorShell;
    }

    @Override
    public ItemStack getTypeStack() {
        return ItemRegistry.DOLL_TURTLE.getTypeStack(this);
    }

    public static void updateConfiguration(Configuration config) {
        final String category = CsmConfiguration.CAT_ENTITY_VALS + Configuration.CATEGORY_SPLITTER + "Turtles";
        config.getCategory(category).setRequiresWorldRestart(true);

        for( EnumTurtleType type : VALUES ) {
            if( type == UNKNOWN ) {
                continue;
            }
            String typeNameL = type.getName().toLowerCase(Locale.ENGLISH);

            type.maxHealth = config.getFloat(typeNameL + "TurtleMaxHealth", category, type.maxHealth, 0.0F, 1024.0F,
                                             "Maximum health of a " + typeNameL + " turtle");
            type.movementFactor = config.getFloat(typeNameL + "TurtleMovementFactor", category, type.movementFactor, 0.0F, 1024.0F,
                                                  "Movement factor of a " + typeNameL + " turtle");
            type.fireproof = config.getBoolean(typeNameL + "TurtleFireproof", category, type.fireproof,
                                               "Wether or not a " + typeNameL + " turtle is fireproof");
        }
    }
}