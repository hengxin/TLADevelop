package debug;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import tlc2.output.StatePrinter;
import tlc2.tool.Action;
import tlc2.tool.EvalException;
import tlc2.tool.ITool;
import tlc2.tool.ModelChecker;
import tlc2.tool.StateVec;
import tlc2.tool.TLCState;
import tlc2.tool.fp.FPSet;
import tlc2.tool.fp.FPSetConfiguration;
import tlc2.util.Context;
import tlc2.util.IStateWriter;
import tlc2.util.RandomGenerator;

public class DebugChecker extends ModelChecker{

	//private final String traceFile;
	
	private int numWorkers = 1;
	
	//private StateVec initStates;
	
	private TLCState curState;
	
	private StateVec stateTrace;
	
	//private final List<SimulationWorker> workers;

	public DebugChecker(ITool tool, String metadir, final IStateWriter stateWriter, boolean deadlock, String fromChkpt,
            final Future<FPSet> future, long startTime) throws EvalException, IOException, InterruptedException, ExecutionException {
    	super(tool, metadir, stateWriter, deadlock, fromChkpt, future, startTime);
    }
	
	public DebugChecker(ITool tool, String metadir, IStateWriter stateWriter, boolean deadlock, String fromChkpt,
			FPSetConfiguration fpSetConfig, long startTime) throws EvalException, IOException {
		super(tool, metadir, stateWriter, deadlock, fromChkpt, fpSetConfig, startTime);
		// TODO Auto-generated constructor stub
	}

	public void printAction(Context con) {
		String val = con.toString();
		StringBuffer sb = new StringBuffer("(");
		Map<String, String> map = new HashMap<String, String>();
		String reg = "\\[|\\]|,|\\s+";
		String[] arrs = val.split(reg);
		for(String str: arrs) {
			if(str.equals(" ") || str.length() < 1) continue;
			String[] keyVal = str.split("->");
			if(keyVal.length != 2) {
				System.out.print(str);
				System.out.println(" var-val is not matched");
			}
			else {
				map.put(keyVal[0], keyVal[1]);
			}
			
		}
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		Entry<String, String> newKeyVal = it.next();
		sb.append(newKeyVal.getKey());
		sb.append("->");
		sb.append(newKeyVal.getValue());
		while(it.hasNext()) {
			newKeyVal = it.next();
			sb.append(", ");
			sb.append(newKeyVal.getKey());
			sb.append("->");
			sb.append(newKeyVal.getValue());
		}
		sb.append(")");
		System.out.println(sb);
	}
	
	
	
	public int getIdx(Scanner sc, int length) {
		int idx = sc.nextInt();
		while(idx >= length) {
			System.out.println("Invalid input");
			idx = sc.nextInt();
		}
		if(idx == -1) System.exit(0);
		return idx;
	}
	
	public void debug(){
		Scanner sc = new Scanner(System.in);
		
		StateVec initStates = this.tool.getInitStates();
		
		final RandomGenerator rng = new RandomGenerator();
		
		int len = initStates.size();
		int index = (int) Math.floor(rng.nextDouble() * len);
		curState = initStates.elementAt(index);
		StatePrinter.printState(curState);
		boolean inConstraints = tool.isInModel(curState);
		while(true) {
			
			Action[] actions = this.tool.getActions();
			int actLen = actions.length;
			List<Action> allAct = new ArrayList<Action>();
			for(int i = 0; i < actLen; i++) {
				StateVec nextStates = this.tool.getNextStates(actions[i], curState);
				if(!nextStates.empty()) {
					allAct.add(actions[i]);
				}
			}
			System.out.println("******choose actions:******");
			for(int i = 0;i < allAct.size(); i++) {
				System.out.print(i + ": ");
				Action act = allAct.get(i);
				System.out.print(act.getName());
				printAction(allAct.get(i).con);
			}
			if(allAct.size() < 1) break;
			int chooseIdx = getIdx(sc, allAct.size());
			StateVec nextStates = this.tool.getNextStates(allAct.get(chooseIdx), curState);
			TLCState nextState = nextStates.elementAt(0);
			StatePrinter.printState(nextState);
			//Check invariants.
			int idx = 0;
			for(idx = 0; idx < this.tool.getInvariants().length; idx++) {
				if(!tool.isValid(this.tool.getInvariants()[idx], nextState)) {
					 System.out.println("INVARIANT_VIOLATED: " + tool.getInvNames()[idx]);
					 System.exit(0);
				}
			}
			
			//check action properites.
			for(idx = 0;idx < this.tool.getImpliedActions().length; idx ++) {
				if(!tool.isValid(this.tool.getImpliedActions()[idx], curState, nextState)) {
					System.out.println("ACTION_PROPERTY_VIOLATED: " + tool.getImpliedActNames()[idx]);
					System.exit(0);
				}
			}
			inConstraints = (tool.isInModel(nextState) && tool.isInActions(curState, nextState));
			if(!inConstraints) {
				break;
			}
			curState = nextState;
		}
		sc.close();
	}
}
