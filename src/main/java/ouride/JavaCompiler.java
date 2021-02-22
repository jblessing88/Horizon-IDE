package main.java.ouride;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;


public class JavaCompiler extends Compiler {
	
	public JavaCompiler(OurIDE ide){
		super(ide);
	}
        
        @Override
    public void compile(String path){
		try {
			//ProcessBuilder p = new ProcessBuilder("javac","*.java");// = new ProcessBuilder("javac",path + System.getProperty("file.separator") + "*.java");
			//p.directory(new File(path));
			//Runtime.getRuntime().exec("javac *.java",null,new File(path));
			File[] files = new File(path).listFiles(new FilenameFilter(){
				public boolean accept(File dir, String name){
					return name.endsWith(".java");
				}
			});
			StringBuilder command = new StringBuilder("javac");
			for(File file: files){
				command.append(" ").append(file.getAbsolutePath());
			}
			super.getIDE().getOutputArea().setProcess(Runtime.getRuntime().exec(command.toString()));//new String[]{"javac","*.java"},null,new File(path)));//p.start());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
        
        @Override
	public void run(String path){
		try {
			//String temp[] = {path.substring(0,path.lastIndexOf(System.getProperty("file.separator"))),path.substring(path.lastIndexOf(System.getProperty("file.separator"))+1)};
			//ProcessBuilder p = new ProcessBuilder("java","-cp",temp[0],temp[1]);
			String dir = path.substring(0,path.lastIndexOf(System.getProperty("file.separator")));
			path = path.substring(path.lastIndexOf(System.getProperty("file.separator"))+1);
			//p.redirectError(Redirect.appendTo(new File("C:\\Users\\own\\Desktop\\error.txt")));
			//p.redirectOutput(Redirect.appendTo(new File("C:\\Users\\own\\Desktop\\out.txt")));
			
			super.getIDE().getOutputArea().setProcess(Runtime.getRuntime().exec(new String[]{"java",path},null,new File(dir)));//p.start());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
