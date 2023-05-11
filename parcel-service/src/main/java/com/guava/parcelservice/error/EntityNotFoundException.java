package com.guava.parcelservice.error;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class entity, Object id) {
        super(entity.getSimpleName() + " with ID: " + id + " ");
    }

}