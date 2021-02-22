package main.java.ouride;

import java.util.Stack;

public abstract class EquationEditor {
    
    protected String startEquation;
    protected String finalEquation;
    protected final String[] regularExpressions = new String[]{"cos", "sin", "exp", "tan", "sqrt", "log", "ln", "^", "+", "-", "*", "/", "%"};;
    
    public abstract String changeEquation(String start);
    
    /**
     *
     * @return
     */
    protected abstract Stack makeGeneralTree();
    
    protected abstract Stack convertToLanguageSpecificTree(Stack a);
    
    public void setStartEquation(String a){
        startEquation = a;
    }
    
    protected void reCreateEquation(Stack a){
        finalEquation = Generate.rebuildEquation(a);
    }
}
