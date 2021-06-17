package cat.itacademy.proyectoerp.util;

import java.util.UUID;

public class UUIDconverter {

	public static UUID convertFromBytes(byte[] bytes) {
	    if (bytes.length != 16) {
	        throw new IllegalArgumentException("The hex to convert should be Binary(16). Check it's column at Mysql.");
	    }
	    int i = 0;
	    long msl = 0;
	    for (; i < 8; i++) {
	        msl = (msl << 8) | (bytes[i] & 0xFF);
	    }
	    long lsl = 0;
	    for (; i < 16; i++) {
	        lsl = (lsl << 8) | (bytes[i] & 0xFF);
	    }
	    return new UUID(msl, lsl);
	}
	
	public static UUID convertFromHexWithoutSpaces(String hex) {
		if (hex.length() != 32) throw new IllegalArgumentException("The hex to convert should be Binary(16). Check it's column at Mysql.");
		String uuid = hex.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
		return UUID.fromString(uuid);
	}	
}
