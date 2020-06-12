package net.earthcomputer.libstructure.mixin;

import net.earthcomputer.libstructure.impl.IStructuresConfig;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkGeneratorType.Preset.class)
public class ChunkGeneratorTypePresetMixin {
    @Inject(method = "createCavesType", at = @At("RETURN"))
    private static void onCreateCavesType(CallbackInfoReturnable<ChunkGeneratorType> ci) {
        ((IStructuresConfig) ci.getReturnValue().getConfig()).libstructure_setHasDefaultFeatures();
    }
}
