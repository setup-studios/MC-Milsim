package at.setup_studios.mc_milsim.util;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

public class ModLogger {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void info(String message) {
        LOGGER.info("[MILSIM] " + message);
    }

    public static void warn(String message) {
        LOGGER.warn("[MILSIM] " + message);
    }

    public static void error(String message) {
        LOGGER.error("[MILSIM] " + message);
    }

    public static void debug(String message) {
        LOGGER.debug("[MILSIM] " + message);
    }
}
