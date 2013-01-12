package com.exam.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import javax.imageio.ImageIO;

public class ExamUtils {
	
	// compress image to byte array
	public static byte[] getCompressedImage(BufferedImage image){
		byte[] imageData = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", baos);
			imageData = baos.toByteArray();
		} catch (IOException ex) {
			imageData = null;
			ex.printStackTrace();
		}
		return imageData;
	}

	// convert byte array to buffered image
	public static BufferedImage getDecompressedImage(byte[] imageData){
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
			return ImageIO.read(bais);
		} catch (IOException ex) {
			return null;
		}
	}
	
	// Encode Object to Binary file
	public static void encodeBinary(Object obj, String path){
		try{
			FileOutputStream fo = new FileOutputStream(path);
			ObjectOutputStream so = new ObjectOutputStream(fo);
			so.writeObject(obj);
			so.close();

		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	// Decode Binary file to Object
	public static Object decodeBinary(String path){
		Object o = null;
		try{
			FileInputStream fi = new FileInputStream(path);
			ObjectInputStream si = new ObjectInputStream(fi);
			try{
				o = si.readObject();
			}
			catch(ClassNotFoundException e){
				e.printStackTrace();
			}
			si.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return o;
	}
	
	// set the system properties
	public static void setSystemProperties(Properties p){
		try {
		    p.store(new FileOutputStream("exam.properties"), "Exam System Properties.");
		    
		    } catch (IOException ex) {
		    	ex.printStackTrace();
		    }  
	}
	
	// retrieve the system properties
	public static Properties getSystemProperties(){
		 
		 Properties p = new Properties();
		 try{
			 InputStream is = new BufferedInputStream(new FileInputStream("exam.properties"));
		     p.load(is);
		    }catch(Exception e){
		      e.printStackTrace();
		    }
		    return p;
	}
	
	// generate the random arraylist
	public static ArrayList<String> getRandomSortList(int totalCount, int selectedCount){
		ArrayList<String> gArray = new ArrayList<String>();
		ArrayList<String> sArray = new ArrayList<String>();
		
		for (int i=0; i<totalCount; i++){
			sArray.add(""+i); // initial an Array
		}
		
		Random r = new Random(); // Random function
		
		for (int i=0; i<selectedCount; i++){
			int index = (int) (r.nextDouble() * sArray.size());
			gArray.add(sArray.get(index));
			sArray.remove(index);
		}
		
		return gArray;
	}
}
