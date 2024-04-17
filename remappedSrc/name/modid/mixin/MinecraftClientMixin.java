package name.modid.mixin;

import name.modid.MineCC;
import name.modid.module.Mod;
import name.modid.module.ModuleManager;
import name.modid.module.world.FastUse;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Shadow
    private int itemUseCooldown;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void onTick(CallbackInfo ci) {
        MineCC.INSTANCE.onTick();
    }

    @Inject(method = "doItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isItemEnabled(Lnet/minecraft/resource/featuretoggle/FeatureSet;)Z"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void onDoItemUseHand(CallbackInfo ci, Hand[] var1, int var2, int var3, Hand hand, ItemStack itemStack) {
        FastUse fastUse = null;
        for (Mod module : ModuleManager.INSTANCE.getModules()) {
            if (module instanceof FastUse) {
                fastUse = (FastUse) module;
                break;
            }
        }
        if (fastUse != null && fastUse.isEnabled()) {
            itemUseCooldown = fastUse.getItemUseCooldown(itemStack);
        }
    }
}
