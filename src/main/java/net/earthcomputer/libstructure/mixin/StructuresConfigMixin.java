package net.earthcomputer.libstructure.mixin;

import net.earthcomputer.libstructure.impl.IStructuresConfig;
import net.earthcomputer.libstructure.impl.LibStructureImpl;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(StructuresConfig.class)
public class StructuresConfigMixin implements IStructuresConfig {
    @Shadow @Final private Map<StructureFeature<?>, StructureConfig> structures;

    @Unique private boolean hasDefaultFeatures = false;

    @Override
    public void libstructure_setHasDefaultFeatures() {
        hasDefaultFeatures = true;
        LibStructureImpl.upToDateConfigs.add((StructuresConfig) (Object) this);
    }

    @Inject(method = "<init>(Z)V", at = @At("RETURN"))
    private void onDefaultInit(CallbackInfo ci) {
        libstructure_setHasDefaultFeatures();
    }

    @Inject(method = "getStructures", at = @At("HEAD"))
    private void onGetStructures(CallbackInfoReturnable<Map<StructureFeature<?>, StructureConfig>> ci) {
        refresh();
    }

    @Inject(method = "method_28600", at = @At("HEAD"))
    private void onGetConfig(StructureFeature<?> structureFeature, CallbackInfoReturnable<StructureConfig> ci) {
        refresh();
    }

    @Unique
    private void refresh() {
        if (hasDefaultFeatures && LibStructureImpl.upToDateConfigs.add((StructuresConfig) (Object) this)) {
            StructuresConfig.DEFAULT_STRUCTURES.forEach(structures::putIfAbsent);
        }
    }
}
