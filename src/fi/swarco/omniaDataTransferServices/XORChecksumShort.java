package fi.swarco.omniaDataTransferServices;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import org.apache.log4j.Logger;
public class XORChecksumShort {
    static Logger logger = Logger.getLogger(XORChecksumShort.class.getName());
    public static void main(String[] args) {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the data to calculate XOR checksum on:");
        String str = "0";
        try {
            str = bf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] b = new byte[str.length()];
        b = str.getBytes();
        byte result = xor(b);
        System.out.println("XOR checksum: " + result);
        byte strResult = xor(str);
        System.out.println("XOR checksum using string param: "+strResult);
        byte charResult = xor(str.toCharArray());
        System.out.println("XOR checksum using char param: "+charResult);
    }
    public static byte xor(byte[] data) {
        String xorData = new String(data.clone());
        return xor(xorData);
    }
    public static byte xor(String data) {
        char[] chars = data.toCharArray();

        return xor(chars);
    }
    public static byte xor(char[] data) {
        char[] chars=Arrays.copyOf(data, data.length);
        for (int i = 1; i < chars.length; i++) {
            chars[0] ^= chars[i];
        }
        return (byte) chars[0];
    }
}