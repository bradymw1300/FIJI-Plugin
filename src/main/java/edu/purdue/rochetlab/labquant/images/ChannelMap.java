package edu.purdue.rochetlab.labquant.images;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ChannelMap {
    private final Map<String, Integer> channelIndexes = new LinkedHashMap<String, Integer>();

    public void put(String name, int oneBasedChannelIndex) {
        channelIndexes.put(name, oneBasedChannelIndex);
    }

    public Map<String, Integer> asMap() {
        return Collections.unmodifiableMap(channelIndexes);
    }
}
