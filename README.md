A library to register modded structures in 1.16. Currently requires 1.16-pre5.

##Example usage
```java
public void onInitialize() {
    System.out.println("Initializing libstructure test");
    // Recreating the village structure as an example, mods would usually implement their own structure class
    VillageFeature feature = new VillageFeature(StructurePoolFeatureConfig.CODEC);
    StructurePoolFeatureConfig plainsConfig = new StructurePoolFeatureConfig(new Identifier("village/plains/town_centers"), 6);
    ConfiguredStructureFeature<StructurePoolFeatureConfig, ? extends StructureFeature<StructurePoolFeatureConfig>> plainsFeature = feature.configure(plainsConfig);
    // If the structure contains jigsaw pools, use the -WithPool register method, otherwise use the normal one.
    LibStructure.registerStructureWithPool(new Identifier("libstructure", "teststructure"), feature, GenerationStep.Feature.SURFACE_STRUCTURES, new StructureConfig(32, 8, 12345), plainsFeature);
    Biomes.MUSHROOM_FIELDS.addStructureFeature(plainsFeature);
}
``` 
For more information on how to create structures see the fabric wiki. The only other difference is that you shouldn't override `getName()`.

##Installation
Put this in your build.gradle:
```groovy
repositories {
    maven {
        name = 'Earthcomputer Mods'
        url = 'https://dl.bintray.com/earthcomputer/mods'
    }
}
dependencies {
    modImplementation 'net.earthcomputer:libstructure:1.0' // Or the latest version
    include 'net.earthcomputer:libstructure:1.0'
}
```
