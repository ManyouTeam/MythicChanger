package cn.superiormc.mythicchanger.paper.adapts.v26_2;

import cn.superiormc.mythicchanger.paper.adapts.ComponentCodecRegistry;
import cn.superiormc.mythicchanger.paper.adapts.VersionCodecs;

public class Paper262Codecs implements VersionCodecs {

    @Override
    public void registerCodecs(ComponentCodecRegistry registry) {
        registry.register(new SulfurCubeContentCodec());
    }

}
