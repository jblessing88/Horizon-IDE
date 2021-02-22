package main.java.ouride;

public abstract class Compiler {
	private OurIDE ide;
	
	public Compiler(OurIDE ide){
		this.ide = ide;
	}
	public OurIDE getIDE(){
		return ide;
	}
	public abstract void compile(String path);
	public abstract void run(String path);
}
