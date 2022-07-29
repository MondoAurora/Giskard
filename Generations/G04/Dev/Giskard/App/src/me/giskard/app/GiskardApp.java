package me.giskard.app;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import me.giskard.Giskard;

public abstract class GiskardApp implements GiskardAppConsts {

	protected static String[] ARGS;

	public static void main(String[] args) throws Exception {
		ARGS = Arrays.copyOf(args, args.length);
		
		InputStream is = ClassLoader.getSystemResourceAsStream("GiskardBootModules.cfg");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line;
		while ((line = reader.readLine()) != null) {
			if ( line.startsWith("#") ) {
				continue;
			}
			
			String[] mod = line.split("\\s+");
			Giskard.log(null, "Loading module", mod);
			
			try {
				GiskardAppModuleServices.loadModule(mod[0], mod[1], mod[2]);
			} catch (Exception e) {
				Giskard.log(null, "exception", e);
			}
		}

		Giskard.log(null, "Success. Or at least, no exception, exiting Giskard.main() :-)");
	}

}
