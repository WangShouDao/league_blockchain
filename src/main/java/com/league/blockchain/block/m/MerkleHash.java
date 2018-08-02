package com.league.blockchain.block.m;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class MerkleHash {

    /**
     *  Hash value as byte array
     */
    private byte[] value;

    public MerkleHash(){
    }

    /**
     * create a MerkleHash from an array of bytes.
     * @param buffer
     * @return
     */
    public static MerkleHash create(byte[] buffer){
        MerkleHash hash = new MerkleHash();
        hash.computeHash(buffer);
        return hash;
    }

    /**
     *  create a MerkleHash from a string.The string needs first to be in a UTF8
     *  sequence of bytes.Used for leaf hashes.
     * @param buffer
     * @return
     */
    public static MerkleHash create(String buffer){
        return create(buffer.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * create a MerkleHash from two MerkleHashes by concatenation of the byte arrays.
     * Used for internal nodes.
     * @param left
     * @param right
     * @return
     */
    public static MerkleHash create(MerkleHash left, MerkleHash right){
        return create(concatenate(left.getValue(), right.getValue()));
    }

    /**
     * get the byte value of a MerkleHash
     * @return an array of bytes
     */
    public byte[] getValue(){
        return value;
    }

    /**
     *  Compare the MerkleHash with a given byte array.
     * @param hash
     * @return
     */
    public boolean equals(byte[] hash){
        return Arrays.equals(this.value, hash);
    }

    /**
     *  Compare the MerkleHash with a given MerkleHash.
     * @param hash
     * @return
     */
    public boolean equals(MerkleHash hash){
        boolean result = false;
        if(hash != null){
            result = Arrays.equals(this.value, hash.getValue());
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }

    /**
     * Encode in Base64 the MerkleHash
     * @return
     */
    @Override
    public String toString() {
        return Base64.getEncoder().encodeToString(this.value);
    }

    /**
     *  Compute SHA256 hash of a byte array.
     * @param buffer
     */
    public void computeHash(byte[] buffer){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            this.value = digest.digest(buffer);
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    /**
     *  Concatenate two array of bytes.
     * @param a
     * @param b
     * @return
     */
    public static byte[] concatenate(byte[] a, byte[] b){
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }
}
