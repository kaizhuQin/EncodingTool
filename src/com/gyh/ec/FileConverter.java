package com.gyh.ec;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileConverter {
	private String toEncoding;
	private String fromEncoding;
	private String path;

	public FileConverter(String path, String toEncoding) {
		this.path = path;
		this.toEncoding = toEncoding;

	}

	public void start() {
		File fileA = null;
		fileA = new File(path);
		String dirPath = null;
		if (fileA.isFile()) { // if args[0] is a file
			fileA = fileA.getAbsoluteFile();
			dirPath = fileA.getParent() + File.separator + "converted";
			convert(fileA, dirPath);
			System.out.println("...done");
		} else if (fileA.isDirectory()) {// if args[0] is a directory
			dirPath = fileA.getAbsolutePath() + File.separator + "converted";
			for (File file : fileA.listFiles()) {
				convert(file, dirPath);
				System.out.println("...done");
			}
		} else {
			System.out
					.println("please set a correct file name or a directory name");
			System.exit(0);
		}
	}

	public void convert(File fileA, String dirPath) {
		BufferedReader br = null;
		BufferedWriter bw = null;
		fromEncoding = "UTF-8".equalsIgnoreCase(toEncoding) ? "GBK" : "UTF-8";
		try {
			FileInputStream fis = new FileInputStream(fileA);
			InputStreamReader isr = new InputStreamReader(fis, fromEncoding);
			br = new BufferedReader(isr);

			File newDir = new File(dirPath);
			if (!newDir.exists()) {
				newDir.mkdir();
			}

			File fileB = new File(dirPath + File.separator + fileA.getName());
			FileOutputStream fos = new FileOutputStream(fileB);

			OutputStreamWriter osr = new OutputStreamWriter(fos, toEncoding);
			bw = new BufferedWriter(osr);
			String buf;
			while ((buf = br.readLine()) != null) {
				bw.write(buf);
				bw.write("\r\n");
			}
		} catch (IOException e) {
			System.out.println("...error");
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		boolean flag=false;
		if (args.length == 2) {
			if (!new File(args[0]).exists()) {
				System.out.println("---------the path dose not exist");
			} else if (!"utf-8".equalsIgnoreCase(args[1])
					&& !"gbk".equalsIgnoreCase(args[1])) {
				System.out.println("---------you can only choose utf-8 and gbk");
			} else {
				flag=true;
				FileConverter converter = new FileConverter(args[0], args[1]);
				converter.start();
			}
		}
		if(!flag){
			System.out.println("please append path of the file and the Encoding witch you want to covert the file to");
			System.out.println("e.g.  java -jar EncodingTool xxxxx.txt UTF-8 ");
		}
	}
}
