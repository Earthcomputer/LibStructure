package net.earthcomputer.libstructure.mixin;

import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;
import java.util.Map;

@Mixin(StructureFeature.class)
public interface StructureFeatureAccessor {
    @Accessor("field_24861")
    @Mutable
    static void setPoolStructures(List<StructureFeature<?>> poolStructures) {
        throw new UnsupportedOperationException();
    }

    @Invoker
    static <F extends StructureFeature<?>> F callRegister(String name, F structureFeature, GenerationStep.Feature step) {
        throw new UnsupportedOperationException();
    }
}
