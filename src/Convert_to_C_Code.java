import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;


public class Convert_to_C_Code {

	public void Convert_CCode(String file_to_compiler) throws IOException
	{
	
	String line;
	String C_file = file_to_compiler.substring(0,
			file_to_compiler.indexOf("."));
	String path = System.getProperty("user.dir");
	
	PrintWriter pw = new PrintWriter(path+"\\"+C_file + ".C");
	
	BufferedReader br = new BufferedReader(new FileReader(path+"\\"+file_to_compiler));
	while ((line = br.readLine()) != null) {
		line = line.trim();

		if (line.startsWith("title")) {
			pw.println("//" + " " + line.substring(0, line.length()));
			pw.println("#include <stdio.h>");
			pw.println("int main(void)\r\n {");

		} else if (line.contains("if") && line.contains("then")
				&& line.contains("goto")) {

			line = line.replace("then", "");
			pw.println(line + ";");

		} else if (line.startsWith("begin")) {
			//br.readLine();

		} else if (line.startsWith("end")) {
			pw.println(" return 0;\r\n}");
		}

		else if (line.contains("goto")) {
			pw.println(line + ";");
		}

		else if (line.startsWith("input")) {
			String id = line.substring(line.indexOf("input") + 6,
					line.length());
			pw.println("scanf" + "(" + "\"%d\"" + "," + id + ")" + ";");
		} else if (line.startsWith("label")) {
			String lbl_id = line.substring(line.indexOf("label") + 6,
					line.length());
			pw.println(lbl_id + ":;");
		} else if (line.startsWith("let")) {
			String let_id = line.substring(line.indexOf("let") + 3,
					line.length());
			pw.println(let_id + ";");
		} else if (line.startsWith("var")) {
			//br.readLine();
		} else if (line.startsWith("rem")) {
			String comment_line = line.substring(line.indexOf("rem") + 3,
					line.length());
			pw.println("//" + comment_line);
		} else if (line.startsWith("print")) {
			String print_id = line.substring(line.indexOf("print") + 6,
					line.length());
			pw.println("printf" + "(" + "\"%d\"" + "," + print_id + ")"
					+ ";");
		} else if (line.startsWith("prompt")) {
			String prompt_txt = line.substring(line.indexOf("prompt") + 6,
					line.length());
			pw.println("printf" + "(" + prompt_txt + ")" + ";");
		}
		else if (line.startsWith("int")) {
			pw.println(line + ";");
		} else if (line.contains("list")) {
			String words[] = line.split(" ");
			words[0] = words[0].replace("list", "");
			pw.println("int " + words[1] + "" + words[0] + ";");
		} else {
			pw.println(line);
		}
	}
	pw.flush();
	pw.close();
	System.out.println("the files have been saved in "+System.getProperty("user.dir"));
	
}
	
}

