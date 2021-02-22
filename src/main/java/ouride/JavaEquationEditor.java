package main.java.ouride;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class JavaEquationEditor extends EquationEditor{

    protected final String[] javaExpressions = new String[]{"Math.cos", "Math.sin", "Math.exp", "Math.tan", "Math.sqrt", "Math.log", "Math.log10", "Math.pow", "+", "-", "*", "/", "%"};;
    
    @Override
    protected Stack makeGeneralTree(){
        Stack prefixNotation = null;
        try{
            prefixNotation = (Stack) Generate.generate(startEquation, javaExpressions);
        } catch(ClassCastException cce){
            System.out.println("You forgot the Equation!");
        }
        if(prefixNotation == null)
            System.out.println("Check your parenthesis!");
        
        return prefixNotation;
    }
    
    @Override
    protected Stack convertToLanguageSpecificTree(Stack a) {
        Queue temp = new LinkedList();
        boolean match = false;
        for(int j = a.size(); j > 0; j--){
            String strMatch = "";
            for (int i = 0; i < regularExpressions.length; i++) {
                if (regularExpressions[i].equals(a.peek().toString())) {
                    a.pop();
                    strMatch = javaExpressions[i];
                    match = true;
                    break;
                }
            }
            if(!match)
                strMatch = a.pop().toString();
            temp.add(strMatch); 
            match = false;
        }
        
        for(int i = temp.size(); i > 0; i--){
            a.push(temp.poll());
        }
        return a;
    }

    @Override
    public String changeEquation(String start) {
        setStartEquation(start);
        Stack stack = makeGeneralTree();
        stack = convertToLanguageSpecificTree(stack);
        reCreateEquation(stack);
        return finalEquation;
    }
}
