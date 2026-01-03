package org.llschall.ardwloop;

/**
 * The status of the Ardwloop connection.
 */
public enum ArdwloopStatus {
    /**
     * Connection not started
     */
    STARTED,
    /**
     * Connecting
     */
    CONNECTING,
    /**
     * Connection failed
     */
    CONNECTION_RETRY,
    /**
     * Connection aborted
     */
    CONNECTION_ABORTED,
    /**
     * Connection ready
     */
    CONNECTED,
}
