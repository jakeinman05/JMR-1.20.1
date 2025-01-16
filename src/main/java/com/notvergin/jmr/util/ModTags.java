package com.notvergin.jmr.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import static com.notvergin.jmr.JohnModResurrected.MODID;

public class ModTags
{
    public static class Blocks {

        public static final TagKey<Block> NEEDS_IMMORTAL_TOOL = tag("needs_immortal_tool");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(MODID, name));
        }
    }
}
