package net.earthcomputer.libstructure;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.earthcomputer.libstructure.impl.LibStructureImpl;
import net.earthcomputer.libstructure.mixin.FlatChunkGeneratorConfigAccessor;
import net.earthcomputer.libstructure.mixin.StructureFeatureAccessor;
import net.earthcomputer.libstructure.mixin.StructuresConfigAccessor;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class LibStructure {

    public static <FC extends FeatureConfig, F extends StructureFeature<FC>> void registerStructure(
            Identifier id,
            F structure,
            GenerationStep.Feature step,
            StructureConfig defaultStructureConfig,
            ConfiguredStructureFeature<FC, ? extends StructureFeature<FC>> superflatFeature
    ) {
        StructureFeatureAccessor.callRegister(id.toString(), structure, step);
        StructuresConfigAccessor.setDefaultStructures(ImmutableMap.<StructureFeature<?>, StructureConfig>builder()
                .putAll(StructuresConfig.DEFAULT_STRUCTURES)
                .put(structure, defaultStructureConfig)
                .build());
        if (superflatFeature != null) {
            FlatChunkGeneratorConfigAccessor.getStructureToFeatures().put(structure, superflatFeature);
        }
        LibStructureImpl.upToDateConfigs.clear();
    }

    public static <FC extends FeatureConfig, F extends StructureFeature<FC>> void registerSurfaceAdjustingStructure(
            Identifier id,
            F structure,
            GenerationStep.Feature step,
            StructureConfig defaultStructureConfig,
            ConfiguredStructureFeature<FC, ? extends StructureFeature<FC>> superflatFeature
    ) {
        registerStructure(id, structure, step, defaultStructureConfig, superflatFeature);
        StructureFeatureAccessor.setSurfaceAdjustingStructures(ImmutableList.<StructureFeature<?>>builder()
                .addAll(StructureFeature.field_24861)
                .add(structure)
                .build());
    }

    /**
     * @deprecated Bad, misleading name. If your structure requires surface adjustment, use
     * {@link #registerSurfaceAdjustingStructure(Identifier, StructureFeature, GenerationStep.Feature, StructureConfig, ConfiguredStructureFeature)}
     * if your structure needs to adjust the surface noise map, otherwise use
     * {@link #registerStructure(Identifier, StructureFeature, GenerationStep.Feature, StructureConfig, ConfiguredStructureFeature)}.
     */
    @Deprecated
    public static <FC extends FeatureConfig, F extends StructureFeature<FC>> void registerStructureWithPool(
            Identifier id,
            F structure,
            GenerationStep.Feature step,
            StructureConfig defaultStructureConfig,
            ConfiguredStructureFeature<FC, ? extends StructureFeature<FC>> superflatFeature
    ) {
        registerSurfaceAdjustingStructure(id, structure, step, defaultStructureConfig, superflatFeature); // what it was before
    }

}
