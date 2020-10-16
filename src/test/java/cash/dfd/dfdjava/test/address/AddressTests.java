package cash.dfd.dfdjava.test.address;

import cash.dfd.dfdjava.address.Address;
import cash.dfd.dfdjava.address.AddressUtil;
import cash.dfd.dfdjava.address.AddressVersion;
import cash.dfd.dfdjava.exceptions.PubKeyInvalidException;
import cash.dfd.dfdjava.pubkey.PubKeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddressTests {
    private static final Logger log = LoggerFactory.getLogger(AddressTests.class);

    @Test
    public void testValidateAddress() {
        String validateAddress = "DKNDuEoTZ2bfaHWS4tp8AadgkfLKQ2Sxht";
        boolean valid = AddressUtil.validateNormalAddress(validateAddress);
        log.info("{} is valid", validateAddress);
        Assert.assertTrue(valid);
    }

    @Test
    public void testGetAddressByPubKey() throws PubKeyInvalidException {
        String pubKeyStr = "D8KeHuxCJwsb8TbingxwfZUaVEYQCkNdZy6diF2gQTT9GiVhihc";
        String validAddr = "DKNDuEoTZ2bfaHWS4tp8AadgkfLKQ2Sxht";
        byte[] pubKeyBytes = PubKeyUtil.getPubKeyBytes(pubKeyStr);
        Address address = Address.fromPubKey(pubKeyBytes, AddressVersion.NORMAL);
        String decodedAddr = address.getValue(Address.ADDRESS_PREFIX);
        log.info("decode address: {}, valid address: {}", decodedAddr, validAddr);
        Assert.assertEquals(validAddr, decodedAddr);
    }
}
