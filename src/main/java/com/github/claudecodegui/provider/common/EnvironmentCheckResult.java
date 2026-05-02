package com.github.claudecodegui.provider.common;

/**
 * Result of environment check with specific error details.
 */
public class EnvironmentCheckResult {

    public enum Status {
        /** All checks passed */
        OK,
        /** Node.js executable not found or invalid */
        NODE_NOT_FOUND,
        /** Node.js version too old */
        NODE_VERSION_TOO_OLD,
        /** Bridge directory not ready (extraction in progress) */
        BRIDGE_NOT_READY,
        /** Bridge core file (channel-manager.js) missing */
        BRIDGE_CORE_FILE_MISSING,
        /** Bridge node_modules missing */
        BRIDGE_NODE_MODULES_MISSING,
        /** Unknown error */
        UNKNOWN_ERROR
    }

    private final Status status;
    private final String message;
    private final String detail;

    private EnvironmentCheckResult(Status status, String message, String detail) {
        this.status = status;
        this.message = message;
        this.detail = detail;
    }

    public static EnvironmentCheckResult ok() {
        return new EnvironmentCheckResult(Status.OK, null, null);
    }

    public static EnvironmentCheckResult nodeNotFound(String nodePath) {
        return new EnvironmentCheckResult(
            Status.NODE_NOT_FOUND,
            "Node.js executable not found or invalid",
            "Path: " + (nodePath != null ? nodePath : "auto-detect failed")
        );
    }

    public static EnvironmentCheckResult nodeVersionTooOld(String currentVersion, int minVersion) {
        return new EnvironmentCheckResult(
            Status.NODE_VERSION_TOO_OLD,
            "Node.js version too old",
            "Current: " + currentVersion + ", Required: " + minVersion + "+"
        );
    }

    public static EnvironmentCheckResult bridgeNotReady() {
        return new EnvironmentCheckResult(
            Status.BRIDGE_NOT_READY,
            "Bridge extraction in progress",
            "Please wait..."
        );
    }

    public static EnvironmentCheckResult bridgeCoreFileMissing(String filePath) {
        return new EnvironmentCheckResult(
            Status.BRIDGE_CORE_FILE_MISSING,
            "Bridge core file missing",
            "channel-manager.js not found at: " + filePath
        );
    }

    public static EnvironmentCheckResult bridgeNodeModulesMissing(String dirPath) {
        return new EnvironmentCheckResult(
            Status.BRIDGE_NODE_MODULES_MISSING,
            "Bridge dependencies missing",
            "node_modules not found in: " + dirPath
        );
    }

    public static EnvironmentCheckResult unknownError(String detail) {
        return new EnvironmentCheckResult(
            Status.UNKNOWN_ERROR,
            "Environment check failed",
            detail
        );
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getDetail() {
        return detail;
    }

    public boolean isOk() {
        return status == Status.OK;
    }

    public boolean isBridgeNotReady() {
        return status == Status.BRIDGE_NOT_READY;
    }

    @Override
    public String toString() {
        if (isOk()) {
            return "EnvironmentCheckResult[OK]";
        }
        return "EnvironmentCheckResult[" + status + ": " + message + (detail != null ? " (" + detail + ")" : "") + "]";
    }
}
