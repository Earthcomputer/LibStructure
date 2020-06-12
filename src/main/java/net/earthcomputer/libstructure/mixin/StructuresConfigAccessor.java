package net.earthcomputer.libstructure.mixin;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StructuresConfig.class)
public interface StructuresConfigAccessor {
    @Mutable
    @Accessor("DEFAULT_STRUCTURES")
    static void setDefaultStructures(ImmutableMap<StructureFeature<?>, StructureConfig> defaultStructures) {
        throw new UnsupportedOperationException();
    }
}
