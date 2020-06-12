package net.earthcomputer.libstructure.impl;

import net.minecraft.world.gen.chunk.StructuresConfig;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public class LibStructureImpl {
    public static Set<StructuresConfig> upToDateConfigs = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap<>()));
}
