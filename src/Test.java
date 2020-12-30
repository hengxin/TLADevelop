//import tlc2.TLC;
//import tlc2.tool.*;
package debug;

import tlc2.*;

public class Test {

	public static void main(String[] args) throws Exception{
		String specPath = "D:\\Eclipse\\workspace\\TLADebug\\spec\\TPaxos.tla";
		//String configPath = "D:\\TPaxos";
		
		//tlc2.TLC.main(new String[] {specPath, configPath});
		Debug debug = new Debug(specPath);
		debug.test();
		//Debug debugChecker = new Debug(specPath, configPath);
	//	debugChecker.debug();
	}
	
}
