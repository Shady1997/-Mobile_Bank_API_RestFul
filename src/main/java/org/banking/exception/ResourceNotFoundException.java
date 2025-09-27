/*
 * Author: Shady Ahmed
 * Date: 2025-09-27
 * Project: Mobile Banking API
 * My Linked-in: https://www.linkedin.com/in/shady-ahmed97/.
 */
package org.banking.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}