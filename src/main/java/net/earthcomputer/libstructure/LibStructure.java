package net.earthcomputer.libstructure;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.earthcomputer.libstructure.impl.LibStructureImpl;
import net.earthcomputer.libstructure.mixin.BiomeAccessor;
import net.earthcomputer.libstructure.mixin.FlatChunkGeneratorConfigAccessor;
import net.earthcomputer.libstructure.mixin.StructureFeatureAccessor;
import net.earthcomputer.libstructure.mixin.StructuresConfigAccessor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibStructure {

    public static <FC extends FeatureConfig, F extends StructureFeature<FC>> void registerStructure(
            Identifier id,
            F structure,
            GenerationStep.Feature step,
            StructureConfig defaultStructureConfig,
            ConfiguredStructureFeature<FC, ? extends StructureFeature<FC>> superflatFeature
    ) {
        // Ensure StructuresConfig class loaded, so the assertion in its static {} block doesn't fail
        StructuresConfig.DEFAULT_STRUCTURES.size();

        StructureFeatureAccessor.callRegister(id.toString(), structure, step);

        if (!id.toString().equals(structure.getName())) {
            // mods should not be overriding getName, but if they do and it's incorrect, this gives an error
            throw new IllegalStateException("Structure " + id + " has mismatching name " + structure.getName() + ". Structures should not override getName.");
        }

        StructuresConfigAccessor.setDefaultStructures(ImmutableMap.<StructureFeature<?>, StructureConfig>builder()
                .putAll(StructuresConfig.DEFAULT_STRUCTURES)
                .put(structure, defaultStructureConfig)
                .build());

        if (superflatFeature != null) {
            FlatChunkGeneratorConfigAccessor.getStructureToFeatures().put(structure, superflatFeature);
        }

        LibStructureImpl.upToDateConfigs.clear();

        for (Biome biome : BuiltinRegistries.BIOME) {
            Map<Integer, List<StructureFeature<?>>> structureLists = ((BiomeAccessor) (Object) biome).getStructureLists();
            if (!(structureLists instanceof HashMap)) {
                // not guaranteed by the standard to be a mutable map
                ((BiomeAccessor) (Object) biome).setStructureLists(structureLists = new HashMap<>(structureLists));
            }
            // not guaranteed by the standard to be mutable lists
            structureLists.compute(step.ordinal(), (k, v) -> makeMutable(v)).add(structure);
        }
    }

    private static List<StructureFeature<?>> makeMutable(List<StructureFeature<?>> mapValue) {
        if (mapValue == null) return new ArrayList<>();
        if (!(mapValue instanceof ArrayList)) return new ArrayList<>(mapValue);
        return mapValue;
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
