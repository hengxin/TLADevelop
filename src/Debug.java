package debug;
import util.FileUtil;
import util.FilenameToStream;
import util.SimpleFilenameToStream;
import util.ToolIO;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Future;

import tlc2.TLCGlobals;
import tlc2.output.StatePrinter;
import tlc2.tool.StateVec;
import tlc2.tool.TLCState;
import tlc2.tool.fp.FPSet;
import tlc2.tool.fp.FPSetConfiguration;
import tlc2.tool.fp.FPSetFactory;
import tlc2.tool.ITool;
import tlc2.tool.impl.Tool;
import tlc2.util.IStateWriter;
import tlc2.util.NoopStateWriter;


public class Debug {

	/* Fields */
	private ITool tool;
	private String mainFile;
	private String configFile;
	private String metadir;
	private IStateWriter stateWriter = new NoopStateWriter();
	private boolean deadlock;
	private String fromChkpt;
	//private FPSetConfiguration fpSetConfig;
	private FilenameToStream resolver;
	private long startTime;
	
	private final DebugChecker mainChecker;
	
	public Debug(String specPath) throws Exception, IOException {
		if(specPath.endsWith(".tla")) {
			this.mainFile = specPath.substring(0, specPath.length() - 4);
		}
		final File f = new File(mainFile);
		String specDir = "";
		if(f.isAbsolute()){
			specDir = f.getParent() + FileUtil.separator;
			this.mainFile = f.getName();
			//System.out.println("mainFile" + this.mainFile);
			this.configFile = mainFile;
			final String dir = FileUtil.parseDirname(mainFile);
			this.resolver = new SimpleFilenameToStream(dir);
			ToolIO.setUserDir(specDir);
		}
//		this.tool = new Tool(mainFile, configFile, resolver);
		
		this.startTime = System.currentTimeMillis();
		
		this.metadir = FileUtil.makeMetaDir(new Date(startTime), specDir, fromChkpt);
		
		FPSetConfiguration fpSetConfiguration = new FPSetConfiguration();
		Future<FPSet> future = FPSetFactory.getFPSetInitialized(
				fpSetConfiguration, metadir, mainFile);
			
		final String dir = FileUtil.parseDirname(mainFile);
		System.out.println(dir);
        if (!dir.isEmpty()) {
        	this.resolver = new SimpleFilenameToStream(dir);
        } else {
        	this.resolver = new SimpleFilenameToStream();
        }
		//this.traceFile = "";
		this.tool = new Tool(mainFile, configFile, resolver);
		
		mainChecker = new DebugChecker(tool, metadir, stateWriter, deadlock, 
				fromChkpt, future, startTime);
	}
	
	public void test() {
		mainChecker.debug();
		System.out.println("ends");
	}
	
}
