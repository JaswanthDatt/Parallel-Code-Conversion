import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


public class while_for_Expander {
		
		 	      
	      public void Expand_while_for( String file) throws IOException
	      {
	    	  
	      String path = System.getProperty("user.dir");
	      String folder_name = path + "\\" + "Outputs" + "\\"
					+ file.substring(0, file.indexOf("."));	      
	      File directory = new File(folder_name);
			directory.mkdirs();
			// Reading the input from the file
			BufferedReader br = new BufferedReader(new FileReader(file));
			String file_Name = file.substring(0, file.indexOf("."))
					+ "_Task_1.txt";
			PrintWriter pw = new PrintWriter(folder_name+"\\"+file_Name);

			String line, while_condition, for_condition = null;
			int counter = 0,counter_orginal=0;
			int endwhile_count=0,endfor_count=0,while_count=0,for_count=0;;
			Stack<String> stk = new Stack<String>();
			
			HashMap<String,HashMap<Integer,HashMap<String, Integer>>> hmap_outer=new HashMap<String,HashMap<Integer,HashMap<String, Integer>>>();
			HashMap<Integer,HashMap<String, Integer>> hmap_intermediate_for=new HashMap<Integer,HashMap<String, Integer>>();
			HashMap<Integer,HashMap<String, Integer>> hmap_intermediate_while=new HashMap<Integer,HashMap<String, Integer>>();
			HashMap<String,Integer> hmap_inner=null;

			while ((line = br.readLine()) != null) {
				
				line=line.trim();
				
				if (line.startsWith("while") && !line.contains("endwhile")) {
					
					//stk.push(line);
					while_condition = line.trim();
					hmap_inner=new HashMap<String, Integer>();
					hmap_inner.put(while_condition, counter);	
					
					
					hmap_intermediate_while.put(++while_count,hmap_inner);
					hmap_outer.put("while", hmap_intermediate_while);
					
					pw.println("goto L_" + counter);
					pw.println("label L_" + (counter + 1));
					
					counter=counter+2;
				} 
				
				else if (line.startsWith("for") && !line.contains("endf  or")) {
					//stk.push(line);
					for_condition = line.trim();				
					
					hmap_inner=new HashMap<String, Integer>();
					hmap_inner.put(for_condition, counter);	
					hmap_intermediate_for.put(++for_count,hmap_inner);
					hmap_outer.put("for", hmap_intermediate_for);
									
					String id = for_condition.substring(
							for_condition.indexOf("for") + 3,
							for_condition.indexOf("="));
					String factor1 = for_condition.substring(
							for_condition.indexOf("=") + 1,
							for_condition.indexOf("to"));
					pw.println("let " + id + " = " + factor1);
					pw.println("label L_" + counter);
								
					counter++;
				}

				else if (line.startsWith("endwhile")) {
					
					++endwhile_count;
					HashMap<Integer,HashMap<String, Integer>> pair=hmap_outer.get("while");
					HashMap<String, Integer> pair1=pair.get(endwhile_count);
					Map.Entry<String, Integer> pair2=pair1.entrySet().iterator().next();
					while_condition=pair2.getKey();
					counter_orginal=pair2.getValue();						
					
					//while_condition = stk.pop();
					while_condition = while_condition.trim();

					String condition = while_condition.substring(
							while_condition.indexOf("while") + 5,
							while_condition.length());
					pw.println("label L_" + counter_orginal);
					pw.println("if " + condition + "then goto L_" + (counter_orginal + 1));  
					
					
					

				} 
				
			 else if (line.contains("endfor")) {
					
				//	for_condition = stk.pop();
					for_condition = for_condition.trim();

					++endfor_count;
					HashMap<Integer,HashMap<String, Integer>> pair=hmap_outer.get("for");
					HashMap<String, Integer> pair1=pair.get(endfor_count);
					Map.Entry<String, Integer> pair2=pair1.entrySet().iterator().next();
					for_condition=pair2.getKey();
					counter_orginal=pair2.getValue();			
					
					
					String id = for_condition.substring(
							for_condition.indexOf("for") + 3,
							for_condition.indexOf("="));
					String factor2 = for_condition
							.substring(for_condition.indexOf("to") + 2,
									for_condition.length());

					pw.println("let " + id + "=" + id + "+" + 1);
					pw.println("if (" + id + " <=" + factor2 + ")"+ " then goto L_" + counter_orginal);
					
				
					
				}    
							
				else
					pw.println(line);

			}

			pw.flush();
			pw.close();
			br.close();
			
			 if_Expander exp=new if_Expander();
			 exp.Expand_if(file_Name);
	      			
		 }
	     
	    
}

	

