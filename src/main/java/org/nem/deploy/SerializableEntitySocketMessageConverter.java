package org.nem.deploy;

import org.nem.core.serialization.SerializableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.AbstractMessageConverter;

public class SerializableEntitySocketMessageConverter extends AbstractMessageConverter
{
    private final SerializationPolicy policy;

    @Autowired(required=true)
    public SerializableEntitySocketMessageConverter(SerializationPolicy policy)
    {
        super(policy.getMediaType());
        this.policy = policy;
    }

    protected boolean supports(Class<?> clazz)
    {
        return SerializableEntity.class.isAssignableFrom(clazz);
    }

    public Object convertFromInternal(Message<?> message, Class<?> targetClass)
    {
        throw new UnsupportedOperationException();
    }

    public Object convertToInternal(Object payload, MessageHeaders headers)
    {
        return this.policy.toBytes((SerializableEntity)payload);
    }
}