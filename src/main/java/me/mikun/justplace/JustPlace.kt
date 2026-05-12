package me.mikun.justplace

import net.minecraft.client.Minecraft
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.DiggerItem
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.TickEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.util.ObfuscationReflectionHelper

@Mod(Justplace.MODID)
class Justplace {
    companion object {
        const val MODID: String = "justplace"
        val startUseItem = ObfuscationReflectionHelper.findMethod(
            Minecraft::class.java,
            "m_91277_"
        )
        val rightClickDelay = ObfuscationReflectionHelper.findField(
            Minecraft::class.java,
            "f_91011_"
        )
    }

    init {
        MinecraftForge.EVENT_BUS.register(this)
    }

    @SubscribeEvent
    fun onClientTick(event: TickEvent.ClientTickEvent) {
        if (event.phase == TickEvent.Phase.END) {
            val mc = Minecraft.getInstance()
            if (mc.player == null) return
            fun doPlace() {
                val inv = mc.player!!.inventory
                val currentSlot = inv.selected
                val nextSlot = (currentSlot + 1) % 9

                if (mc.player!!.isShiftKeyDown) return
                if (
                    inv.getItem(currentSlot).item !is DiggerItem
                    || inv.getItem(nextSlot).item !is BlockItem
                ) return

                inv.selected = nextSlot
                startUseItem(mc)
                inv.selected = currentSlot
            }

            if (KeyBindings.PLACE_KEY.isDown) {
                if (KeyBindings.PLACE_KEY.same(mc.options.keyUse)) {
                    if (rightClickDelay.getInt(mc) == 4) {
                        doPlace()
                    }
                } else {
                    //  原版放置键为键盘的时候会绕过放置间隔...
//                    if (rightClickDelay.getInt(mc) == 0) {
                    doPlace()
//                    }
                }
            }

        }
    }
}
