package com.jonfreer.wedding.infrastructure.exceptions;

/**
 *
 */
public class ResourceNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;
    private int resourceId;

    /**
     * Constructs an instance with the message and identifier of
     * the resource provided.
     * @param message The message that explains in more detail the
     *                circumstance of the exception.
     * @param resourceId The identifier of the resource that could not be found.
     */
    public ResourceNotFoundException(String message, int resourceId) {
        super(message);
        this.resourceId = resourceId;
    }

    /**
     * Retrieves the identifier for the resource that could not be found.
     * @return The identifier of the resource that could not be found.
     */
    public int getResourceId() {
        return this.resourceId;
    }
}
