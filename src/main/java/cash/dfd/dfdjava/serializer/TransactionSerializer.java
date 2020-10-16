package cash.dfd.dfdjava.serializer;

import cash.dfd.dfdjava.exceptions.DeserializeException;
import cash.dfd.dfdjava.exceptions.SerializeException;
import cash.dfd.dfdjava.operation.ContractInvokeOperation;
import cash.dfd.dfdjava.operation.ContractTransferOperation;
import cash.dfd.dfdjava.operation.IOperation;
import cash.dfd.dfdjava.operation.TransferOperation;
import cash.dfd.dfdjava.transaction.Transaction;
import org.bouncycastle.util.encoders.Hex;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class TransactionSerializer implements ISerializer<Transaction> {
    private final String addressPrefix;

    public TransactionSerializer(String addressPrefix) {
        this.addressPrefix = addressPrefix;
    }

    @Override
    public byte[] serialize(Transaction instance) throws SerializeException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Uint16Serializer uint16Serializer = Uint16Serializer.defaultInstance();
        Uint32Serializer uint32Serializer = Uint32Serializer.defaultInstance();
        TransferOperationSerializer transferOperationSerializer = new TransferOperationSerializer(addressPrefix);
        ContractInvokeOperationSerializer contractInvokeOperationSerializer = new ContractInvokeOperationSerializer(addressPrefix);
        ContractTransferOperationSerializer contractTransferOperationSerializer = new ContractTransferOperationSerializer(addressPrefix);
        try {
            bos.write(uint16Serializer.serialize(instance.getRefBlockNum()));
            bos.write(uint32Serializer.serialize(instance.getRefBlockPrefix()));
            bos.write(uint32Serializer.serialize(instance.getTransientExpiration()));

            // operations
            int operationsSize = instance.getTransientOperations() != null ? instance.getTransientOperations().size() : 0;
            bos.write(operationsSize);
            if(instance.getTransientOperations() != null) {
                for(IOperation operation : instance.getTransientOperations()) {
                    if(operation instanceof TransferOperation) {
                        int opType = operation.getOperationType();
                        bos.write(opType);
                        bos.write(transferOperationSerializer.serialize((TransferOperation) operation));
                    } else if(operation instanceof ContractInvokeOperation) {
                        int opType = operation.getOperationType();
                        bos.write(opType);
                        bos.write(contractInvokeOperationSerializer.serialize((ContractInvokeOperation) operation));
                    } else if(operation instanceof ContractTransferOperation) {
                        int opType = operation.getOperationType();
                        bos.write(opType);
                        bos.write(contractTransferOperationSerializer.serialize((ContractTransferOperation) operation));
                    } else {
                        throw new SerializeException("not supported operation class " + operation.getClass().toString());
                    }
                }
            }
            // extensions
            bos.write(0);
            // signatures
            if(instance.getSignatures() != null && instance.getSignatures().size() > 0) {
                bos.write(instance.getSignatures().size());
            }

            return bos.toByteArray();
        } catch (IOException e) {
            throw new SerializeException(e);
        }
    }

    @Override
    public Transaction deserialize(byte[] bytes) throws DeserializeException {
        return null;
    }
}
