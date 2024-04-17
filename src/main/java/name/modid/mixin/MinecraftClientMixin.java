package name.modid.mixin;

import name.modid.MineCC;
import name.modid.module.Mod;
import name.modid.module.ModuleManager;
import name.modid.module.world.FastUse;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.profiler.Profiler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Unique private boolean doItemUseCalled;
    @Unique private boolean rightClick;

    @Shadow protected abstract void doItemUse();
    @Shadow public abstract Profiler getProfiler();

    @Shadow @Nullable public ClientPlayerInteractionManager interactionManager;

    @Shadow private int itemUseCooldown;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo info) {
        MineCC.INSTANCE.onInitializeClient();
    }

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
