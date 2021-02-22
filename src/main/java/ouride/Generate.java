package main.java.ouride;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Generate {
    
    private static String[] operators;
    private static Stack output = new Stack();
    private static Stack operator = new Stack();
    private static Node root;

    public static Object generate(String equation, String[] a){
        output = new Stack();
        operator = new Stack();
        root = null;
        
        operators = a;
        root = new Node(operators);
        
        if(equation.length() == 0)
            return "No";
        equation = equation.replaceAll("\\s", "");
        
        boolean ok = buildPrefixNotation(equation);
        ArrayList<String> tokens = new ArrayList<>();

        if(ok){
            for(int i = output.size(); i > 0; i--){
                tokens.add(output.pop().toString());
            }

            if(!isOperator(tokens.get(tokens.size() - 3)) && (isOperator(tokens.get(tokens.size() - 2)) || isOperator(tokens.get(tokens.size() - 1)))){
                //Tell user the equation is wrong
                //Stop all execution of equation
            }
            
            for (String token : tokens) {
                output.push(token);
            }
        }
        else if(!ok){
            return null;
        }
        return output;
    }
    
    private static boolean buildPrefixNotation(String a){

        String equation = new StringBuilder(a).reverse().toString();
        char[] temp = equation.toCharArray();
        for(int i = 0; i < temp.length; i++){
            if(temp[i] == '(')
                temp[i] = ')';
            else if(temp[i] == ')')
                temp[i] = '(';
        }
        
        equation = Arrays.toString(temp);
        equation = equation.replaceAll(",", "");
        equation = equation.replaceAll("\\s", "");
        equation = equation.replaceAll("\\[", "");
        equation = equation.replaceAll("\\]", "");
        
        for(int i = 0; i < equation.length(); i++){
            if(Character.isDigit(equation.charAt(i)) || equation.charAt(i) == '.'){
                String subDigit = "";
                do{
                    subDigit += equation.charAt(i);
                    if(i != equation.length())
                        i++;
                    if(i == equation.length())
                        break;
                } while((Character.isDigit(equation.charAt(i)) || equation.charAt(i) == '.'));
                i--;
                subDigit = new StringBuilder(subDigit).reverse().toString();
                if(Double.parseDouble(subDigit) % 1 == 0){
                    int num = Integer.parseInt(subDigit);
                    output.push(num);
                }
                else if(Double.parseDouble(subDigit) % 1 != 0){
                    Double num1 = Double.parseDouble(subDigit);
                    output.push(num1);
                }
            }
            else if(Character.isLetter(equation.charAt(i))){
                String subEquation = "";
                do
                {
                    subEquation += equation.charAt(i);
                    if(i != equation.length())
                        i++;
                    if(i == equation.length())
                        break;
                } while(Character.isLetter(equation.charAt(i)));
                subEquation = new StringBuilder(subEquation).reverse().toString();
                output.push(subEquation);
                i--;
            }
            else if(equation.charAt(i) == '+' || equation.charAt(i) == '-'){
                if(operator.size() != 0){
                    if(operator.peek().equals('*') || operator.peek().equals('/') || operator.peek().equals('%') || operator.peek().equals('^')){
                        output.push(operator.pop());
                    }
                }
                operator.push(equation.charAt(i));
            }
            else if(equation.charAt(i) == '*' || equation.charAt(i) == '/' || equation.charAt(i) == '%'){
                if(operator.size() != 0){
                    if(operator.peek().equals('^')){
                        output.push(operator.pop());
                    }
                }
                operator.push(equation.charAt(i));
            }
            else if(equation.charAt(i) == '^'){
                operator.push(equation.charAt(i));
            }
            else if(equation.charAt(i) == '('){
                operator.push(equation.charAt(i));
            }
            else if(equation.charAt(i) == ')'){
                do{
                    if(operator.size() == 0){
                        return false;
                    }
                    if(operator.peek().equals('('))
                        output.push("&");
                    else if(!operator.peek().equals('('))
                        output.push(operator.pop());
                    if(i != equation.length())
                        i++;
                    if(i == equation.length() || operator.size() == 0)
                        break;
                } while(!operator.peek().equals('('));
                i--;
                if(i == 0){
                    //Tell user they forgot some parenthesis
                    //also stop execution of prefix notation
                    return false;
                }
                else{
                    operator.pop();
                }
            }
        }
        while(operator.size() != 0){
            if(operator.peek().equals("(") || operator.peek().equals(")")){
                //Tell user they forgot some parenthesis
                //also stop execution of prefix notation
                return false;
            }
            else{
                output.push(operator.pop());
            }
        }
        return true;
    }
 
    private static void createTree(Node a, Stack b){
        a.setValue(b.pop().toString());
        for(int i = 0; i < 2; i++){
            Node newNode = new Node(operators);
            if(isOperator(b.peek().toString())){
                if(i == 0){
                    a.addLeft(newNode);
                }
                else{
                    a.addRight(newNode);
                }
                newNode.setParent(a);
                createTree(newNode, b);
            }
            else if(!isOperator(b.peek().toString())){
                newNode.setValue(b.pop().toString());
                if(i == 0){
                    a.addLeft(newNode);
                }
                else{
                    a.addRight(newNode);
                }
                newNode.setParent(a);
                
            }
        }
    }
    
    public static String rebuildEquation(Stack a){
        String newEquation = null;
        createTree(root, a);
        newEquation = root.inOrder();
        return newEquation;
    }
    
    private static boolean isOperator(String a){
        for (String operator1 : operators) {
            if (operator1.equals(a)) {
                return true;
            }
        }
        return false;
    }
}