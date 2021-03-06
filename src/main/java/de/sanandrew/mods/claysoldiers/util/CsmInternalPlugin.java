/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import de.sanandrew.mods.claysoldiers.api.CsmPlugin;
import de.sanandrew.mods.claysoldiers.api.ICsmPlugin;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.effect.IEffectRegistry;
import de.sanandrew.mods.claysoldiers.api.client.IRenderHookRegistry;
import de.sanandrew.mods.claysoldiers.api.client.soldier.ISoldierRender;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.ITeamRegistry;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.IUpgradeRegistry;
import de.sanandrew.mods.claysoldiers.client.eventhandler.ClayModelRotationEventHandler;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.info.LexiconGroupInfo;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.misc.LexiconGroupMisc;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.mount.LexiconGroupMounts;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.soldier.LexiconGroupSoldiers;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.upgrade.LexiconGroupUpgrades;
import de.sanandrew.mods.claysoldiers.client.renderer.soldier.layer.LayerCape;
import de.sanandrew.mods.claysoldiers.client.renderer.soldier.layer.LayerCrown;
import de.sanandrew.mods.claysoldiers.client.renderer.soldier.layer.LayerGoggles;
import de.sanandrew.mods.claysoldiers.client.renderer.soldier.layer.LayerGoldHoodie;
import de.sanandrew.mods.claysoldiers.client.renderer.soldier.layer.LayerGunpowder;
import de.sanandrew.mods.claysoldiers.client.renderer.soldier.layer.LayerLeatherArmor;
import de.sanandrew.mods.claysoldiers.client.renderer.soldier.layer.LayerLilyPants;
import de.sanandrew.mods.claysoldiers.client.renderer.soldier.layer.LayerMagmaCreamCharge;
import de.sanandrew.mods.claysoldiers.client.renderer.soldier.layer.LayerSkull;
import de.sanandrew.mods.claysoldiers.client.renderer.soldier.layer.LayerHeldItem;
import de.sanandrew.mods.claysoldiers.client.renderer.soldier.RenderHookBody;
import de.sanandrew.mods.claysoldiers.client.util.ClientProxy;
import de.sanandrew.mods.claysoldiers.eventhandler.SoldierEventHandler;
import de.sanandrew.mods.claysoldiers.registry.effect.Effects;
import de.sanandrew.mods.claysoldiers.registry.team.Teams;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import de.sanandrew.mods.sanlib.api.client.lexicon.ILexiconInst;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@CsmPlugin
public class CsmInternalPlugin
        implements ICsmPlugin
{
    @Override
    public void registerTeams(ITeamRegistry registry) {
        Teams.initialize(registry);
    }

    @Override
    public void registerUpgrades(IUpgradeRegistry registry) {
        Upgrades.initialize(registry);
    }

    @Override
    public void registerEffects(IEffectRegistry registry) {
        Effects.initialize(registry);
    }

    @Override
    public void registerCsmEvents(EventBus bus) {
        bus.register(new SoldierEventHandler());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerCsmClientEvents(EventBus bus) {
        bus.register(new ClayModelRotationEventHandler());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerSoldierRenderLayer(ISoldierRender<?, ?> renderer) {
        renderer.addRenderLayer(new LayerHeldItem(renderer));
        renderer.addRenderLayer(new LayerGoggles(renderer));
        renderer.addRenderLayer(new LayerLeatherArmor(renderer));
        renderer.addRenderLayer(new LayerMagmaCreamCharge(renderer));
        renderer.addRenderLayer(new LayerCrown(renderer));
        renderer.addRenderLayer(new LayerGunpowder(renderer));
        renderer.addRenderLayer(new LayerSkull(renderer));
        renderer.addRenderLayer(new LayerCape(renderer));
        renderer.addRenderLayer(new LayerLilyPants(renderer));
        renderer.addRenderLayer(new LayerGoldHoodie(renderer));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerSoldierRenderHook(IRenderHookRegistry registry) {
        registry.registerSoldierHook(new RenderHookBody());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerLexicon(ILexiconInst registry) {
        ClientProxy.lexiconInstance = registry;

        LexiconGroupSoldiers.register(registry);
        LexiconGroupUpgrades.register(registry);
        LexiconGroupMounts.register(registry);
        LexiconGroupMisc.register(registry);
        LexiconGroupInfo.register(registry);
    }
}
