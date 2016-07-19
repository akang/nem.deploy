package org.nem.deploy;

import java.io.ByteArrayInputStream;
import org.nem.core.serialization.Deserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.AbstractMessageConverter;

public class DeserializerSocketMessageConverter extends AbstractMessageConverter
{
    private final SerializationPolicy policy;

    @Autowired(required=true)
    public DeserializerSocketMessageConverter(SerializationPolicy policy)
    {
        super(policy.getMediaType());
        this.policy = policy;
    }

    protected boolean supports(Class<?> clazz)
    {
        return Deserializer.class.isAssignableFrom(clazz);
    }

    public Object convertToInternal(Object payload, MessageHeaders headers)
    {
        throw new UnsupportedOperationException();
    }

    public Object convertFromInternal(Message<?> message, Class<?> targetClass)
    {
        byte[] bytes = (byte[])(byte[])message.getPayload();
        return this.policy.fromStream(new ByteArrayInputStream(bytes));
    }
}