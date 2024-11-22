package com.onsemi.cim.tarlac.triggers.services;

import java.io.FileOutputStream;

public class FileCreate {
	
	public FileCreate(String path, String filename, String fileextension, String filecontent) throws Exception {	
		FileOutputStream fos = new FileOutputStream(path + filename + "." + fileextension);
		fos.write(filecontent.getBytes());
		fos.close();
	}
}
