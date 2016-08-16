package net.aleksandrfgames;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;
import java.util.Random;

public class Bloom<T> {

	private BitSet bf;
	private int bitArraySize = 10000;
	private int numHashFunc = 7;
	
	public Bloom () {
		bf = new BitSet(bitArraySize);
	}
	
	public void add(T obj) {
		int[] indexes = getHashIndexes(obj);
		for (int index : indexes) 
			bf.set(index);
	}
	
	public boolean contains(T obj) {
		int[] indexes = getHashIndexes(obj);
		for (int index : indexes)
			if (!bf.get(index))
				return false;
		return true;
	}
	
	private int[] getHashIndexes(T obj) {
		int indexes[] = new int[numHashFunc];
		long seed = 0;
		byte[] digest;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(obj.toString().getBytes());
			digest = md.digest();
			for (int i = 0; i < numHashFunc; i++) 
				seed = seed ^ (((long)digest[i] & 0xFF))<<(8*i);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		Random gen = new Random(seed);
		for (int i = 0; i < numHashFunc; i++) 
			indexes[i] = gen.nextInt(bitArraySize);
		return indexes;
	}

	
	public static void main(String[] args) {
		Bloom<Integer> bl = new Bloom<Integer>();
		bl.add(81);
		bl.add(90892);
		bl.add(112);
		
		System.out.println("containt 111 = " + bl.contains(111));
		System.out.println("containt 81 = " + bl.contains(81));
		System.out.println("containt 222 = " + bl.contains(222));
		System.out.println("containt 112 = " + bl.contains(112));

	}
	

}











