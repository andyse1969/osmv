package org.xmlrpc.android;

/**
 * A Base64 Encoder/Decoder.
 * 
 * <p>
 * This class is used to encode and decode data in Base64 format as described in
 * RFC 1521.
 * 
 * <p>
 * This is "Open Source" software and released under the <a
 * href="http://www.gnu.org/licenses/lgpl.html">GNU/LGPL</a> license.<br>
 * It is provided "as is" without warranty of any kind.<br>
 * Copyright 2003: Christian d'Heureuse, Inventec Informatik AG, Switzerland.<br>
 * Home page: <a href="http://www.source-code.biz">www.source-code.biz</a><br>
 * 
 * <p>
 * Version history:<br>
 * 2003-07-22 Christian d'Heureuse (chdh): Module created.<br>
 * 2005-08-11 chdh: Lincense changed from GPL to LGPL.<br>
 * 2006-11-21 chdh:<br>
 * &nbsp; Method encode(String) renamed to encodeString(String).<br>
 * &nbsp; Method decode(String) renamed to decodeString(String).<br>
 * &nbsp; New method encode(byte[],int) added.<br>
 * &nbsp; New method decode(String) added.<br>
 */

class Base64Coder {

    // Mapping table from 6-bit nibbles to Base64 characters.
    private static char[] map1 = new char[64];
    static {
        int i = 0;
        for (char c = 'A'; c <= 'Z'; c++) {
            Base64Coder.map1[i++] = c;
        }
        for (char c = 'a'; c <= 'z'; c++) {
            Base64Coder.map1[i++] = c;
        }
        for (char c = '0'; c <= '9'; c++) {
            Base64Coder.map1[i++] = c;
        }
        Base64Coder.map1[i++] = '+';
        Base64Coder.map1[i++] = '/';
    }

    // Mapping table from Base64 characters to 6-bit nibbles.
    private static byte[] map2 = new byte[128];
    static {
        for (int i = 0; i < Base64Coder.map2.length; i++) {
            Base64Coder.map2[i] = -1;
        }
        for (int i = 0; i < 64; i++) {
            Base64Coder.map2[Base64Coder.map1[i]] = (byte) i;
        }
    }

    /**
     * Encodes a string into Base64 format. No blanks or line breaks are
     * inserted.
     * 
     * @param s
     *            a String to be encoded.
     * @return A String with the Base64 encoded data.
     */
    static String encodeString(final String s) {
        return new String(encode(s.getBytes()));
    }

    /**
     * Encodes a byte array into Base64 format. No blanks or line breaks are
     * inserted.
     * 
     * @param in
     *            an array containing the data bytes to be encoded.
     * @return A character array with the Base64 encoded data.
     */
    static char[] encode(final byte[] in) {
        return encode(in, in.length);
    }

    /**
     * Encodes a byte array into Base64 format. No blanks or line breaks are
     * inserted.
     * 
     * @param in
     *            an array containing the data bytes to be encoded.
     * @param iLen
     *            number of bytes to process in <code>in</code>.
     * @return A character array with the Base64 encoded data.
     */
    static char[] encode(final byte[] in, final int iLen) {
        final int oDataLen = ((iLen * 4) + 2) / 3; // output length without
                                                   // padding
        final int oLen = ((iLen + 2) / 3) * 4; // output length including
                                               // padding
        final char[] out = new char[oLen];
        int ip = 0;
        int op = 0;
        while (ip < iLen) {
            final int i0 = in[ip++] & 0xff;
            final int i1 = ip < iLen ? in[ip++] & 0xff : 0;
            final int i2 = ip < iLen ? in[ip++] & 0xff : 0;
            final int o0 = i0 >>> 2;
            final int o1 = ((i0 & 3) << 4) | (i1 >>> 4);
            final int o2 = ((i1 & 0xf) << 2) | (i2 >>> 6);
            final int o3 = i2 & 0x3F;
            out[op++] = Base64Coder.map1[o0];
            out[op++] = Base64Coder.map1[o1];
            out[op] = op < oDataLen ? Base64Coder.map1[o2] : '=';
            op++;
            out[op] = op < oDataLen ? Base64Coder.map1[o3] : '=';
            op++;
        }
        return out;
    }

    /**
     * Decodes a string from Base64 format.
     * 
     * @param s
     *            a Base64 String to be decoded.
     * @return A String containing the decoded data.
     * @throws IllegalArgumentException
     *             if the input is not valid Base64 encoded data.
     */
    static String decodeString(final String s) {
        return new String(decode(s));
    }

    /**
     * Decodes a byte array from Base64 format.
     * 
     * @param s
     *            a Base64 String to be decoded.
     * @return An array containing the decoded data bytes.
     * @throws IllegalArgumentException
     *             if the input is not valid Base64 encoded data.
     */
    static byte[] decode(final String s) {
        return decode(s.toCharArray());
    }

    /**
     * Decodes a byte array from Base64 format. No blanks or line breaks are
     * allowed within the Base64 encoded data.
     * 
     * @param in
     *            a character array containing the Base64 encoded data.
     * @return An array containing the decoded data bytes.
     * @throws IllegalArgumentException
     *             if the input is not valid Base64 encoded data.
     */
    static byte[] decode(final char[] in) {
        int iLen = in.length;
        if ((iLen % 4) != 0) {
            throw new IllegalArgumentException(
                    "Length of Base64 encoded input string is not a multiple of 4.");
        }
        while ((iLen > 0) && (in[iLen - 1] == '=')) {
            iLen--;
        }
        final int oLen = (iLen * 3) / 4;
        final byte[] out = new byte[oLen];
        int ip = 0;
        int op = 0;
        while (ip < iLen) {
            final int i0 = in[ip++];
            final int i1 = in[ip++];
            final int i2 = ip < iLen ? in[ip++] : 'A';
            final int i3 = ip < iLen ? in[ip++] : 'A';
            if ((i0 > 127) || (i1 > 127) || (i2 > 127) || (i3 > 127)) {
                throw new IllegalArgumentException(
                        "Illegal character in Base64 encoded data.");
            }
            final int b0 = Base64Coder.map2[i0];
            final int b1 = Base64Coder.map2[i1];
            final int b2 = Base64Coder.map2[i2];
            final int b3 = Base64Coder.map2[i3];
            if ((b0 < 0) || (b1 < 0) || (b2 < 0) || (b3 < 0)) {
                throw new IllegalArgumentException(
                        "Illegal character in Base64 encoded data.");
            }
            final int o0 = (b0 << 2) | (b1 >>> 4);
            final int o1 = ((b1 & 0xf) << 4) | (b2 >>> 2);
            final int o2 = ((b2 & 3) << 6) | b3;
            out[op++] = (byte) o0;
            if (op < oLen) {
                out[op++] = (byte) o1;
            }
            if (op < oLen) {
                out[op++] = (byte) o2;
            }
        }
        return out;
    }

    // Dummy constructor.
    private Base64Coder() {
    }
}
