package cash.dfd.dfdjava.address;

public class AddressUtil {

    /**
     * @param address
     * @return
     */

    public static boolean validateNormalAddress(String address) {
        try {
            String prefix = Address.ADDRESS_PREFIX;
            Address dfdAddress = Address.fromString(address, prefix);
            return dfdAddress.getValue(prefix).equals(address) && AddressVersion.NORMAL == dfdAddress.getVersion();
        } catch (Exception e) {
//            e.printStackTrace();
            return false;
        }
    }
}
