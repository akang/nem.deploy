package org.nem.deploy;

import org.nem.core.serialization.Deserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.AbstractMessageConverter;

public class DeserializableEntitySocketMessageConverter extends AbstractMessageConverter
{
    private final SerializationPolicy policy;
    private final DeserializerSocketMessageConverter deserializerSocketMessageConverter;

    @Autowired(required=true)
    public DeserializableEntitySocketMessageConverter(SerializationPolicy policy)
    {
        super(policy.getMediaType());
        this.policy = policy;
        this.deserializerSocketMessageConverter = new DeserializerSocketMessageConverter(policy);
    }

    protected boolean supports(Class<?> clazz)
    {
        return SerializationUtils.getConstructor(clazz) != null;
    }

    public Object convertFromInternal(Message<?> message, Class<?> aClass)
    {
        Deserializer deserializer = (Deserializer)this.deserializerSocketMessageConverter
                .convertFromInternal(message, aClass);

        return SerializationUtils.createInstance(aClass, deserializer);
    }

    public Object convertToInternal(Object o, MessageHeaders messageHeaders)
    {
        throw new UnsupportedOperationException();
    }
}