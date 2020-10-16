package cash.dfd.dfdjava.serializer;

import cash.dfd.dfdjava.exceptions.DeserializeException;
import cash.dfd.dfdjava.exceptions.SerializeException;

public interface ISerializer<T> {
    byte[] serialize(T instance) throws SerializeException;

    T deserialize(byte[] bytes) throws DeserializeException;
}
