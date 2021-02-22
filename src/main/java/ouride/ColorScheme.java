package main.java.ouride;

public class ColorScheme {
	private String background;
	private String font;
	
	public ColorScheme(String b, String f){
		background = b;
		font = f;
	}
	
	public String getBackgroundCSS(){
		return background;
	}
	
	public String getFontCSS(){
		return font;
	}
}
