package at.setup_studios.mc_milsim.util;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

/**
 * Custom logger utility for the MILSIM mod.
 * Provides standardized logging methods with consistent prefix formatting.
 * Wraps SLF4J logger functionality with mod-specific formatting.
 */
public class ModLogger {
    /** The underlying SLF4J logger instance */
    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Logs an informational message.
     * Prefixes the message with [MILSIM] tag.
     * Use for general operational information.
     *
     * @param message The message to be logged at INFO level
     */
    public static void info(String message) {
        LOGGER.info("[MILSIM] " + message);
    }

    /**
     * Logs a warning message.
     * Prefixes the message with [MILSIM] tag.
     * Use for potentially harmful situations.
     *
     * @param message The message to be logged at WARN level
     */
    public static void warn(String message) {
        LOGGER.warn("[MILSIM] " + message);
    }

    /**
     * Logs an error message.
     * Prefixes the message with [MILSIM] tag.
     * Use for error events that might still allow the application to continue running.
     *
     * @param message The message to be logged at ERROR level
     */
    public static void error(String message) {
        LOGGER.error("[MILSIM] " + message);
    }

    /**
     * Logs a debug message.
     * Prefixes the message with [MILSIM] tag.
     * Use for detailed information for debugging purposes.
     *
     * @param message The message to be logged at DEBUG level
     */
    public static void debug(String message) {
        LOGGER.debug("[MILSIM] " + message);
    }
}