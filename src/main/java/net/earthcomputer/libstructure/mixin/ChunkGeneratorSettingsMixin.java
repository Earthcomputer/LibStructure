package net.earthcomputer.libstructure.mixin;

import net.earthcomputer.libstructure.impl.IStructuresConfig;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkGeneratorSettings.class)
public class ChunkGeneratorSettingsMixin {
    @Inject(method = "method_30641", at = @At("RETURN"))
    private static void onCreateCavesType(CallbackInfoReturnable<ChunkGeneratorSettings> ci) {
        ((IStructuresConfig) ci.getReturnValue().getStructuresConfig()).libstructure_setHasDefaultFeatures();
    }
}
