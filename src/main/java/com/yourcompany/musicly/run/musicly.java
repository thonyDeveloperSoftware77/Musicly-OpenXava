package com.yourcompany.musicly.run;

import org.openxava.util.*;

/**
 * Execute this class to start the application.
 *
 * With OpenXava Studio/Eclipse: Right mouse button > Run As > Java Application
 */

public class musicly {

	public static void main(String[] args) throws Exception {
		//DBServer.start("musicly-db"); // To use your own database comment this line and configure src/main/webapp/META-INF/context.xml
		AppServer.run(
				"musicly"); // Use AppServer.run("") to run in root context
	}

}
