package com.audiointerface;

/**
 * Created by gblea on 2016-03-19.
 */
public class PacketSizeManager {
    public static byte [] addSize(byte [] data) {
        int size = data.length;
        byte [] byteSize = intToBytes(size);

        byte [] result = new byte[size + 4];
        System.arraycopy(byteSize, 0, result, 0, 4);
        System.arraycopy(data, 0, result, 4, size);

        return result;
    }

    public static byte [] stripSize(byte [] data) {
        byte [] result = new byte[data.length - 4];
        System.arraycopy(data, 4, result, 0, result.length);
        return result;
    }

    public static boolean isValidSize(byte [] data) {
        int size = bytesToInt(data, 0) + 4;

        for (int i = size; i < data.length; i++) {
            if(data[i] != 0) {
                return false;
            }
        }

        return true;
    }

    private static byte [] intToBytes(int num) {
        return new byte[] {
                (byte)(num >>> 24),
                (byte)(num >>> 16),
                (byte)(num >>> 8),
                (byte)(num)};
    }

    private static int bytesToInt(byte [] bytes, int idx) {
        int num = bytes[idx + 0];
        num <<= 8;
        num += bytes[idx + 1];
        num <<= 8;
        num += bytes[idx + 2];
        num <<= 8;
        num += bytes[idx + 3];

        return num;
    }
}
