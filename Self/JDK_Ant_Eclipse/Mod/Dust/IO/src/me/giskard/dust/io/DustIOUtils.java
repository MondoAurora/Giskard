package me.giskard.dust.io;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;

public class DustIOUtils extends GiskardUtils implements DustIOConsts {

	// https://stackoverflow.com/questions/53270963/read-text-stream-codepoint-by-codepoint
	public static int read(Reader reader) throws IOException {
		int unit0 = reader.read();
		if ( (unit0 < 0 ) || !Character.isHighSurrogate((char) unit0) ) {
			return unit0;
		}
		
		int unit1 = reader.read();
		if ( unit1 < 0 ) {
			return unit1; // EOF
		}
		
		if ( !Character.isLowSurrogate((char) unit1) ) {
			throw new RuntimeException("Invalid surrogate pair");
		}
		
		return Character.toCodePoint((char) unit0, (char) unit1);
	}
	
	public static boolean visitString(String string, CodepointVisitor visitor) {
		int ch;
	  int i;
	  for (i = 0; i < string.length(); i += Character.charCount(ch)) {
	    ch = string.codePointAt(i);
	    if (!visitor.process(ch)) {
	      return false;
	    }
	  }
	  
	  return true;
	}
	
	public static String extendName(String fileName) {
		if ( !fileName.startsWith("/") ) {
			String root = GiskardUtils.getRoot();

			if ( !GiskardUtils.isEmpty(root) ) {
				fileName = root + "/" + fileName;
			}
		}
		return fileName;
	}

	public static Reader getReader(String fName) throws Exception {
		fName = extendName(fName);
		Giskard.log(MiNDEventLevel.Trace, "Start reading file", fName, "...");
		return new FileReader(fName);
	}

}
