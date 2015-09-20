package graphex;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
class Node{
	  Node destination;
	  String name;
	  String character;
	  Boolean state;
	  
	  static Set<String> character_set = new HashSet<String>();
	  static Set<String> character_set2 = new HashSet<String>();
	  static int node_counter = 0;
	  static Set<Node> start_states_e = new HashSet(); 
	  Map<String,Set<Node>> nodeHash = new HashMap<String,Set<Node>>();
	  Set<Node> set = new HashSet<Node>();
	  static String transitions ="";
	  static String labelChar ="";
	  Boolean visited;
	  String labels = "";
	  static Set<Node> all_node_set = new HashSet<Node>();
	  static String all_transitions_DFA="";

static final String EPSILON = "\u03B5";

public Node(String character, Node destination,Boolean state) {
    this.destination = destination;
    node_counter++;
    if(destination != null){
		set.add(destination);
		this.character = character;
	    nodeHash.put(character,set);}
    	this.state = state;
 	   String aString = Integer.toString(node_counter);
    	this.name = "Q"+aString;
	    character_set.add(character);

	    this.visited=false;
	    if(character!=EPSILON){
		    character_set2.add(character);
		    System.out.print(character);
	    }
	 
	  }

	public Boolean getState(){
		return state;
		
		}
	public String getName(){
		return name;
	}
	public Node getDestination(){
		return destination;
	}
	public Map<String,Set<Node>> getMap(){
		return nodeHash;
	}
	public Set<Node> getSet(){
		return set;
	}
	public String getCharacter(){
		return character;
	}
	
	public static String gettingString(String node,String node2,String character_thing){
		transitions=node+"->" +node2;
		labelChar="["+"label"+ "="+ "\""+ character_thing + "\""+"]"+";";
		return transitions+labelChar;
	
	}
	public static String gettingString_DFA(String node,String node2,String character_thing){
		transitions=node+"->" +node2;
		labelChar="["+"label"+ "="+ "\""+ character_thing + "\""+"]"+";";
		return transitions+labelChar;
	
	}
	public static Node concatenation(Node temp1,Node temp2){
		
     	Node.gettingString(temp1.getName(),temp2.getName(),temp1.getCharacter());
		 Set<Node> temp_set = new HashSet<Node>();
		Set<Node> temp_set2 = new HashSet<Node>();
		temp_set2.addAll(node_sets(temp2));
		temp_set2.add(temp2);
	
		for(Node the_reference : temp_set2){
			if(the_reference.getState() == true){
				 Set<Node> set = new HashSet<Node>();
				 the_reference.state=false;
				set.add(temp1);
				the_reference.getMap().put(EPSILON,set);
				

			}
		}
		all_node_set.clear();
		
		return temp2;
		
		 
	}
	public static Node star(Node temp1){
		
		Set<Node> temp_set2 = new HashSet<Node>();
		temp_set2.addAll(node_sets(temp1));
		temp_set2.add(temp1);
		for(Node the_reference : temp_set2){
    		if(the_reference.getState() == true){
				 Set<Node> set = new HashSet<Node>();
				 set.add(temp1);
				 the_reference.getMap().put(EPSILON,set);

			}

		}
     
		all_node_set.clear();
		Node new_start=new Node(EPSILON,temp1,true);
		
		return new_start;
		

	}
public static Node union(Node temp1,Node temp2){
		Set<Node> tempSet = new HashSet(); 
		Node new_start=new Node(null,null,false);
		tempSet.add(temp1);
		tempSet.add(temp2);
		new_start.getMap().put(EPSILON,tempSet);
		all_node_set.clear();
			return new_start;
	}

public static Set<Node> node_sets(Node node){
		
		
		if(node.getMap().values().iterator().hasNext())
		for(Set<Node> value : node.getMap().values() ){
		
			
			if(all_node_set.addAll(value)==true)
			node_sets(value.iterator().next());
			
				}
		
		return all_node_set;
}

public static Set<Node> move(String character,Set< Node> the_sets){
	Set<Node> result_set = new HashSet<Node>(); 
	for(Node the_reference : the_sets){
		Set<Node> node_set_temp=the_reference.getMap().get(character);
		if(node_set_temp != null)
		result_set.addAll(node_set_temp);
	}
	return result_set;
	
}

public static Set<Node> eClosure(Node head){
	
	if(head.visited==false){
	start_states_e.add(head);
	}
	if(head.getMap().containsKey(EPSILON)==true){
	if (head.getMap().get(EPSILON).iterator().hasNext()){
		head.getMap().get(EPSILON).iterator().next().getMap();
		if(head.getMap().containsKey(EPSILON)==true){
		if(head.getMap().get(EPSILON).iterator().next().visited == false){
		head.getMap().get(EPSILON).iterator().next().visited = true;
		start_states_e.add(head.getMap().get(EPSILON).iterator().next());
		eClosure(head.getMap().get(EPSILON).iterator().next());
		}
		
		}
	
	}
	
	}
	
	return start_states_e;


}	

public static DFA_Node create_DFA(Node head){
	DFA_Node start = new DFA_Node(eClosure(head));
	ArrayDeque<DFA_Node> DFA_node_holder = new ArrayDeque<DFA_Node>();
	Set<DFA_Node> all_DFA_Nodes_set = new HashSet<DFA_Node>();
	
	DFA_node_holder.add(start);
	all_DFA_Nodes_set.add(start);
	while(!DFA_node_holder.isEmpty() ){
		DFA_Node current_DFA_node = DFA_node_holder.pop();
		for(String character:Regex.character_set_test){

			Set<Node> current_set_NFA = eClosure(move(character,current_DFA_node.name));
			Boolean match = false;
			DFA_Node old_node = null;
			for(DFA_Node trans_node:all_DFA_Nodes_set){
			
				if(trans_node.name.equals(current_set_NFA)){
					match = true;
					old_node=trans_node;
				}
				
			}
		
			if(match == false){
				old_node =new DFA_Node(current_set_NFA);
				DFA_node_holder.push(old_node);
				all_DFA_Nodes_set.add(old_node);

			}
				current_DFA_node.dfaNodeHash.put(character, old_node);
				all_transitions_DFA=all_transitions_DFA+gettingString(current_DFA_node.graphName,old_node.graphName,character)+"\n";

			
		}
	}
	DFA_Node.all_dfa.addAll(all_DFA_Nodes_set);
	return start;

}
public static Set<Node> eClosure(Set<Node> node_set){
	
	Deque<Node> stack_states = new ArrayDeque<Node>();
	Set<Node> result_set = node_set;
	stack_states.addAll(node_set);
	while(!stack_states.isEmpty()){
		Node current_node = stack_states.pop();
		if(current_node.getMap().get(EPSILON)!= null)
		for(Node trans_node:current_node.getMap().get(EPSILON)){
			if(result_set.add(trans_node)){
				stack_states.add(trans_node);
			}
		}
	}
	return result_set;	
}

public static void createGraph(Node head,String file){
		String first_transition="\"\""+ "->"+head.getName();		
		Set<Node> temp_set2 = new HashSet<Node>();
		temp_set2.addAll(node_sets(head));
		temp_set2.add(head);		
			try {			
			ArrayDeque<Node> temp_array = new ArrayDeque<Node>();
			ArrayDeque<Node> temp_array2 = new ArrayDeque<Node>();		
		PrintWriter out = new PrintWriter(file);
		String one = "rankdir=LR;";
		String two = "node [shape = none]; \"\";";		
		String three = "node [shape = doublecircle]; ";
		String four = 	"node [shape = circle];";
		 String accept_states= "";				
		for(Node the_reference : temp_set2){
			if(the_reference.getState() == true){
				accept_states=accept_states+the_reference.getName()+",";
			
				 
			}
			
		}
		accept_states=accept_states.substring(0,accept_states.length()-1) + ";";
	
		out.println("digraph NFA {"+"\n" + one+"\n"+two+"\n"+three+accept_states+"\n"+four+"\n"+first_transition+"\n"+Regex.getAllTransitions()+"\n" +"}");
		out.close();
		
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
			all_node_set.clear();


	}
	
	}