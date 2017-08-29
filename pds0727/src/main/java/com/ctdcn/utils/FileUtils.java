package com.ctdcn.utils;

import java.io.File;

public class FileUtils {

	public static void deleteFile(File f) {
		if (f.isFile()) {
			f.delete();
		} else if (f.isDirectory()) {
			File[] f1 = f.listFiles();
			for (File f2 : f1) {
				deleteFile(f2);
			}
			f.delete();
		}
	}
}
