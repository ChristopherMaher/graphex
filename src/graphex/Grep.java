package graphex;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Grep {

	public static void main(String args[]) {
		
		String regex_command="";

		String nfa_file_name="";
		String dfa_file_name="";
		String out_put_file="";
		if(args.length==6){
			 regex_command=args[4];
			 nfa_file_name=args[1];
			 dfa_file_name=args[3];
			 out_put_file=args[5];
			 
			
		}
		if(args.length==2){
			regex_command=args[0];
			 out_put_file=args[1];

		}
		if(args.length==4)
		for(String s: args){
				if(s.charAt(0)=='-'){
					if(s.charAt(1)=='n'){
				 nfa_file_name=args[1];
				 regex_command=args[2];
				 out_put_file=args[3];
					Regex test = new Regex(regex_command);
					Regex.regex1();
					Node nfa_head_node =Regex.the_head_node();
					System.out.print(Node.create_DFA(nfa_head_node));
					DFA_Node.createGraph(Node.create_DFA(nfa_head_node),"SADF.gv");
					Node.createGraph(Regex.the_head_node(),nfa_file_name);

					}
			}
			
				if(s.charAt(0)=='-'){
					if(s.charAt(1)=='d'){
				 dfa_file_name=args[1];
				 regex_command=args[2];
				 out_put_file=args[3];
				 
				}
			}
		}
	
	}
}
