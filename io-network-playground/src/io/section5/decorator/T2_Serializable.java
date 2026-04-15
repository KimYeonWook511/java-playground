package io.section5.decorator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;

public class T2_Serializable {
	static class DataObject implements Serializable {
		@Serial
		private static final long serialVersionUID = 19990511L;

		private int type = 0;
		private final String data;

		public DataObject(int type, String data) {
			this.type = type;
			this.data = data;
		}

		public long getSerialVersionUID() {
			return serialVersionUID;
		}

		public int getType() {
			return type;
		}

		public String getData() {
			return data;
		}

		@Override
		public String toString() {
			return "DataObject{" +
				"version=" + serialVersionUID +
				", type=" + type +
				", data='" + data + '\'' +
				'}';
		}
	}

	private static final String currentPath = System.getProperty("user.dir");
	private static final String targetPath = currentPath + "/tmp/test.dat";

	public static DataObject readObject() {
		DataObject dto;
		try {
			FileInputStream fis = new FileInputStream(targetPath);
			ObjectInputStream ois = new ObjectInputStream(fis);
			dto = (DataObject) ois.readObject();
		} catch (Exception e) {
			dto = null;
		}
		return dto;
	}

	public static boolean wrtieObject(DataObject dto) {
		try {
			FileOutputStream fos = new FileOutputStream(targetPath);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(dto);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void main(String[] args) {
		if (wrtieObject(new DataObject(415, "test_data"))) { // 저장
			System.out.println("writeObject(): true");
		} else {
			System.out.println("writeObject(): false");
		}

		DataObject dto = readObject();
		if (dto != null) {
			System.out.println(dto);
		} else {
			System.out.println("Error: Failed to read Object!");
		}
	}

}
