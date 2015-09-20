package graphex;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DFA_Node {
	  static Integer counter=0;
	  static Set<Node> name;
	  String graphName;
	  Boolean state;
	  Boolean checked;
	  Boolean marked;
	  Map<String, DFA_Node> dfaNodeHash = new HashMap<String,DFA_Node>();
	  static  Set<DFA_Node> all_dfa = new HashSet<DFA_Node>();
	public DFA_Node(String character, DFA_Node destination,Boolean state){
		dfaNodeHash.put(character,destination);
	    this.state = state;
	    this.checked=checked;
	}

	public DFA_Node(Set<Node> name){
		
		this.name = name;
		graphName = "q"+ counter;
		counter++;
	}
	public static String create_string(DFA_Node head){
		String transitions;
		ArrayDeque<DFA_Node> temp_stack = new ArrayDeque<DFA_Node>();
		Set<DFA_Node> temp_set2 = new HashSet<DFA_Node>();
		temp_stack.add(head);
		temp_set2.add(head);
		while(!temp_stack.isEmpty()){
			DFA_Node current= temp_stack.pop();
			for(String character:Regex.character_set_test){
			
			}
		}	
		return null;
	}
	public static void createGraph(DFA_Node head,String file){
		String first_transition="\"\""+ "->"+head.name;		
		Set<Node> temp_set2 = new HashSet<Node>();
		
			try {			
			ArrayDeque<Node> temp_array = new ArrayDeque<Node>();
			ArrayDeque<Node> temp_array2 = new ArrayDeque<Node>();		
		PrintWriter out = new PrintWriter(file);
		String one = "rankdir=LR;";
		String two = "node [shape = none]; \"\";";		
		String three = "node [shape = doublecircle]; ";
		String four = 	"node [shape = circle];";
		String accept_states= "";				
		 	
		out.println("digraph NFA {"+"\n" + one+"\n"+two+"\n"+three+accept_states+"\n"+four+"\n"+"\"\""+"->"+head.graphName+"\n"+Node.all_transitions_DFA+"\n" +"}");
		out.close();
		
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}

	}
	
}
