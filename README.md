# LibStructure
A library to register modded structures in 1.16. Currently requires 1.16-pre5.

## Example usage
```java
public void onInitialize() {
    System.out.println("Initializing libstructure test");
    // Recreating the village structure as an example, mods would usually implement their own structure class
    VillageFeature feature = new VillageFeature(StructurePoolFeatureConfig.CODEC);
    StructurePoolFeatureConfig plainsConfig = new StructurePoolFeatureConfig(new Identifier("village/plains/town_centers"), 6);
    ConfiguredStructureFeature<StructurePoolFeatureConfig, ? extends StructureFeature<StructurePoolFeatureConfig>> plainsFeature = feature.configure(plainsConfig);
    // If the structure needs to adjust the surface terrain to make it look better, use the surface-adjusting register method, otherwise use the normal one.
    LibStructure.registerSurfaceAdjustingStructure(new Identifier("libstructure", "teststructure"), feature, GenerationStep.Feature.SURFACE_STRUCTURES, new StructureConfig(32, 8, 12345), plainsFeature);
    // Uncomment the following line if you are on 1.16.1 or 1.16
    //Biomes.MUSHROOM_FIELDS.addStructureFeature(plainsFeature);
}
```
This is a way to add your structure to biomes for now, until the biome PR is merged to Fabric API.
```java
@Mixin(DefaultBiomeFeatures.class)
public class DefaultBiomeFeaturesMixin {
    @Inject(method = "addMushroomFieldsFeatures", at = @At("HEAD"))
    private static void addExtraMushroomFieldsFeatures(GenerationSettings.Builder builder, CallbackInfo ci) {
        builder.structureFeature(MyStructures.MY_VILLAGE);
        // where MY_VILLAGE is registered to LibStructure in the static {} block of MyStructures.
    }
}
```
For more information on how to create structures see the fabric wiki. The only other difference is that you shouldn't override `getName()`.

## Installation
Put this in your build.gradle:
```groovy
repositories {
    maven {
        name = 'Earthcomputer Mods'
        url = 'https://dl.bintray.com/earthcomputer/mods'
    }
}
dependencies {
    modImplementation 'net.earthcomputer:libstructure:1.5' // Use the version for the latest version table below
    include 'net.earthcomputer:libstructure:1.5'
}
```

### Latest Versions
- Minecraft 1.16-pre5 to 20w28a, including 1.16 and 1.16.1 --- LibStructure 1.3
- Minecraft 20w29a to 20w30a --- LibStructure 1.4.1
- Minecraft 1.16.2-pre1 onwards, including 1.16.2 --- LibStructure 1.5
