package me.mikun.justplace

import com.mojang.blaze3d.platform.InputConstants
import net.minecraft.client.KeyMapping
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.RegisterKeyMappingsEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import org.lwjgl.glfw.GLFW

@Mod.EventBusSubscriber(modid = Justplace.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
object KeyBindings {
    val PLACE_KEY = KeyMapping(
        "key.justplace.place_next",
        InputConstants.Type.MOUSE,
        GLFW.GLFW_MOUSE_BUTTON_2,
        "key.categories.justplace"
    )

    @SubscribeEvent
    @JvmStatic
    fun registerKeys(event: RegisterKeyMappingsEvent) {
        event.register(PLACE_KEY)
    }

}