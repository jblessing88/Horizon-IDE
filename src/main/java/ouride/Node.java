package main.java.ouride;

public class Node {
    
    private Node parent;
    private Node left;
    private Node right;
    private String value;
    private String[] operators;
    
    public Node(String[] a){
        parent = null;
        left = null;
        right = null;
        value = null;
        operators = a;
    }
    
    public void addLeft(Node child){
        left = child;
    }
    
    public void addRight(Node child){
        right = child;
    }
    
    public void setParent(Node parent){
        this.parent = parent;
    }
    
    public void setValue(String value){
        this.value = value;
    }
    
    public Node getParent(){
        return parent;
    }
    
    public Node getLeft(){
        return left;
    }
    
    public Node getRight(){
        return right;
    }
    
    public String getValue(){
        return value;
    }
    
    public String inOrder(){
        
        if(isOperator(value)){
            if(value.equals(operators[7])){
                return value + "(" + left.inOrder() + ", " + right.inOrder() + ")";
            }
            return value + "(" + right.inOrder() + ")";
        }
        else if(left != null && right != null)
            return "(" + left.inOrder() + value + right.inOrder() + ")";
        else{
            if(value.equals("&")){
                return "";
            }
            return "" + value;
        }
    }
    
    private boolean isOperator(String a){
        for(int i = 0; i < 8; i++){
            if(operators[i].equals(a))
                return true;
        }
        return false;
    }
}
