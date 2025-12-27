package com.billing.exception;


public class ProductCustomExceptions 
{

    public static class ResourceNotFoundException extends RuntimeException 
    {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

    public static class UnauthorizedException extends RuntimeException 
    {
        public UnauthorizedException(String message) {
            super(message);
        }
    }

    public static class BadRequestException extends RuntimeException 
    {
        public BadRequestException(String message) {
            super(message);
        }
    }

    public static class MethodNotAllowedException extends RuntimeException 
    {
        public MethodNotAllowedException(String message) {
            super(message);
        }
    }
}
