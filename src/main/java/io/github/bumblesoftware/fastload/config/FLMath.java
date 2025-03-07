package io.github.bumblesoftware.fastload.config;

import io.github.bumblesoftware.fastload.util.Bound;

import static io.github.bumblesoftware.fastload.config.DefaultConfig.*;
import static io.github.bumblesoftware.fastload.config.FLConfig.*;
import static io.github.bumblesoftware.fastload.init.FastloadClient.ABSTRACTED_CLIENT;

public class FLMath {

    public static double getCircleArea(final int radius) {
        return Math.PI * radius * radius;
    }

    private static int parseRenderChunkRadius(final int rawRadius, final Bound bound) {
        return bound.minMax(
                rawRadius,
                integer -> Math.min(integer, ABSTRACTED_CLIENT.getViewDistance()),
                integer -> integer
        );
    }

    public static int getLocalRenderChunkRadius() {
        return parseRenderChunkRadius(getRawLocalRenderChunkRadius(), LOCAL_CHUNK_RADIUS_BOUND);
    }
    public static int getServerRenderChunkRadius() {
        return parseRenderChunkRadius(getRawServerRenderChunkRadius(), SERVER_CHUNK_RADIUS_BOUND);
    }
    public static int getLocalRenderChunkArea() {
        return (int)getCircleArea(getLocalRenderChunkRadius());
    }
    public static int getServerRenderChunkArea() {
        return (int)getCircleArea(getServerRenderChunkRadius());
    }
    public static int getChunkTryLimit() {
        return CHUNK_TRY_LIMIT_BOUND.minMax(getRawChunkTryLimit());
    }
    public static Boolean isDebugEnabled() {
        return getRawDebug();
    }
    public static Boolean isInstantLoadEnabled() {
        return getRawInstantLoad();
    }
    public static Boolean isServerRenderEnabled() {
        return getServerRenderChunkArea() > 0;
    }
    public static Boolean isLocalRenderEnabled() {
        return getLocalRenderChunkRadius() > 0;
    }
}
