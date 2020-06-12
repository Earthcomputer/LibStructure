package net.earthcomputer.libstructure.mixin;

import net.minecraft.world.gen.chunk.FlatChunkGeneratorConfig;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(FlatChunkGeneratorConfig.class)
public interface FlatChunkGeneratorConfigAccessor {
    @Accessor("STRUCTURE_TO_FEATURES")
    static Map<StructureFeature<?>, ConfiguredStructureFeature<?, ?>> getStructureToFeatures() {
        throw new UnsupportedOperationException();
    }
}
