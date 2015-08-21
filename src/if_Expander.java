import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;


public class if_Expander {

	
	public void Expand_if(String file2) throws IOException
	{
		 String file_Name = file2.substring(0, file2.indexOf("."))
				+ "_Task_2.txt";
		 String line=null;
		 String path = System.getProperty("user.dir");
		 String inner_folder=file2.substring(0,file2.indexOf("_"));
		
		 String folder_name = path + "\\" + "Outputs" + "\\"+inner_folder;
		 System.setProperty("user.dir",folder_name);
		 String path1 = System.getProperty("user.dir");
		PrintWriter pw = new PrintWriter(folder_name+"\\"+file_Name);
		int counter = 0,counter_orginal=0; 
		BufferedReader br = new BufferedReader(new FileReader(folder_name+"\\"+file2));

		while ((line = br.readLine()) != null) {
			
			line=line.trim();
			
	     if (line.startsWith("if") && line.contains("then")
				&& !line.contains("goto"))
	     {
			
			if (line.contains("<") && !line.contains("<=")) {
				line = line.replace("<", ">=");
			} else if (line.contains(">") && !line.contains(">=")) {
				line = line.replace(">", "<=");
			} else if (line.contains("==") && !line.contains("!=")) {
				line = line.replace("==", "!=");
			} else if (line.contains("!=") && !line.contains("==")) {
				line = line.replace("!=", "==");
			} else if (line.contains("<=") && !line.contains("<")) {
				line = line.replace("<=", ">");
			} else if (line.contains(">=") && !line.contains(">")) {
				line = line.replace(">=", "<");
			}

			line = line.trim();
			String factor1 = line.substring(0, line.indexOf("then") - 1);
			String factor2 = line.substring(line.indexOf("then") + 4,
					line.length());
			// int appendText=line.indexOf(line.indexOf("then")+4);
			pw.println(factor1 + "then  goto L_" + counter+"\r\n"+factor2);
			//pw.println(br.readLine());				
			pw.println("label L_" + counter);	
			
			counter=counter+1;
	     }
	     else
	    	 pw.println(line);
			
			
		}
		pw.flush();
		pw.close();
		br.close();
		Convert_to_C_Code ccode=new Convert_to_C_Code();
		ccode.Convert_CCode(file_Name);
	}
	
}
