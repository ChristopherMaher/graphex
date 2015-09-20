package graphex;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
 
public class Regex {
	static String unparsed_regex;
	private String factoredString;
	private static int current_location;
	static String all_transitions="";

	static ArrayDeque<Node> node_holder = new ArrayDeque();
	  static HashSet<Integer> tempSet = new HashSet();
	  Node term5=null;
	  static HashSet<String> character_set_test = new HashSet();  
	 

	public Regex(String unparsed_regex) {
		this.unparsed_regex = unparsed_regex;		
		current_location = 0;
		
		
	
	}
	
	
	public static void regex1(){
			
		term();
		if(current_location <= unparsed_regex.length()-1){

			if(unparsed_regex.charAt(current_location)=='|'){
				current_location++;
				regex1();
				
					Node temp1=node_holder.pop();
					Node temp2 = node_holder.pop();
					Node temp3=(Node.union(temp1,temp2));
					node_holder.push(temp3);
					all_transitions=all_transitions+Node.gettingString(temp3.getName(),temp1.getName(),"\u03B5")+"\n";
					all_transitions=all_transitions+Node.gettingString(temp3.getName(),temp2.getName(),"\u03B5")+"\n";

				
			}
		}
		if(current_location>=unparsed_regex.length()-1){
		//	move()
		}
		
	}
	public static void term( ) {
		
		factor();
	
		
		if(current_location <= unparsed_regex.length()-1){
			if(unparsed_regex.charAt(current_location)!='|'){
					if(unparsed_regex.charAt(current_location)!=')'){
						term();
						Set<Node> tempSet = new HashSet(); 
						Node temp3 = null;
						Node temp1=node_holder.pop();
						Node temp2 = node_holder.pop();
						if(temp2.getMap().values().iterator().hasNext())
						   
							 tempSet=temp2.getMap().values().iterator().next();
							if(tempSet.iterator().hasNext())
								temp3=tempSet.iterator().next();
							
						
						all_transitions=all_transitions+Node.gettingString(temp3.getName(),temp1.getName(),"\u03B5")+"\n";

						node_holder.push(Node.concatenation(temp1, temp2));
				
				}
			}
		}
		
	}

	public static void factor( ) {
		
		base();
		if(current_location <= unparsed_regex.length()-1){
			if(unparsed_regex.charAt(current_location)=='*'){
				Node temp1=node_holder.pop();
				node_holder.push(Node.star(temp1));
				Set<Node> tempSet = new HashSet<Node>(); 
				Node temp3=null;
				all_transitions=all_transitions+Node.gettingString(node_holder.peek().getName(),temp1.getName(),"\u03B5")+"\n";

				if(temp1.getMap().values().iterator().hasNext())
					 tempSet=temp1.getMap().values().iterator().next();
					if(tempSet.iterator().hasNext())
						temp3=tempSet.iterator().next();
				all_transitions=all_transitions+Node.gettingString(temp3.getName(),temp1.getName(),"\u03B5")+"\n";


				current_location++;
			}
		}
			
}
	public static void base(){
				 if(unparsed_regex.charAt(current_location) == '('){
			 current_location++;
			 regex1();
			}
		 
		 		character();
		
		
		
	}
	public static void character(){
		if(current_location <= unparsed_regex.length()-1){
			if(unparsed_regex.charAt(current_location) != '|'){
				if(unparsed_regex.charAt(current_location)!=')'){
				String temp=Character.toString(unparsed_regex.charAt(current_location));
				if(temp != Node.EPSILON){
					character_set_test.add(temp);
					//System.out.print(character_set_test);
				}
				

				
					Node start=new Node(null,null,false);

					Node accept=new Node(null, null, true);
				
					 Set<Node> set = new HashSet<Node>();
					
					 set.add(accept);
							
					start.getMap().put(temp,set);
			
					all_transitions=all_transitions+Node.gettingString(start.getName(),accept.getName(),temp)+"\n";
					
					node_holder.push(start);
									
					current_location++;
		
				}
			}
		}
	}
	public static String getAllTransitions(){
		return all_transitions;
	}
	
	

	
	public String toString(){
		return "this is the Regex" + unparsed_regex.charAt(current_location);
	}


	public static Node the_head_node() {
		Node term5=node_holder.peek();

		return  term5;

	}
	
	}
