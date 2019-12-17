/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntaxanalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author hp
 */

public class SyntaxAnalysis {
    
    public static void main(String[] args) {
        // TODO code application logic here
        try{
            File file = new File("C:\\Users\\hp\\Desktop\\Code\\code.txt");
            
            BufferedReader br = new BufferedReader(new FileReader(file));
            String expression = br.readLine();
            
            if (expression == null)
                return;
            
            List<LexicalUnit> lexicalUnits = lexicalAnalysis(expression);
            
            boolean verify = syntaxAnalysis(lexicalUnits);
            
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public static List<LexicalUnit> lexicalAnalysis(String expression){
        
        // Initialization of Variables 
        
        String validExpression = "";
        List<LexicalUnit> lexicalUnits = new LinkedList();
        int currentState = 0;
        StringBuilder strBuilder = new StringBuilder();
        boolean lexicalError = false;
        int cpt = 0;
        
        // Remove White Space
        
        for(int i = 0; i < expression.length(); i++){
            if(expression.charAt(i) != ' '){
                validExpression += expression.charAt(i);
            }
        }
        
        if(validExpression.charAt(validExpression.length() - 1) != '#'){
            validExpression += '#';
        }
        
        // Begin Lexical Analysis
        
        while(!lexicalError && cpt < validExpression.length()){
            char currentToken = validExpression.charAt(cpt);
            switch(currentState){
                case 0: if(currentToken == '#'){
                            System.out.println("Empty expression");
                            cpt++;
                        }else if(currentToken == '('){
                            currentState = 7;
                        }else if(currentToken == ')'){
                            currentState = 8;
                        }else if(currentToken == '+'){
                            currentState = 3;
                        }else if(currentToken == '-'){
                            currentState = 4;
                        }else if (currentToken == '*'){
                            currentState = 5;
                        }else if(currentToken == '/'){
                            currentState = 6;
                        }else if(Character.isDigit(currentToken)){
                            strBuilder.append(currentToken);
                            cpt++;
                            currentState = 2;
                        }else if(Character.isAlphabetic(currentToken)){
                            strBuilder.append(currentToken);
                            cpt++;
                            currentState = 1;
                        }else{
                            System.out.println("Error character not exist in alphabet postion : " + cpt);
                            lexicalError = true;
                        }
                        break;
                case 1: if(currentToken == '#'){
                            lexicalUnits.add(new LexicalUnit("Identifier", strBuilder.toString()));
                            strBuilder.delete(0, strBuilder.length());
                            cpt++;
                        }else if(currentToken == '('){
                            lexicalUnits.add(new LexicalUnit("Identifier", strBuilder.toString()));
                            strBuilder.delete(0, strBuilder.length());
                            currentState = 7;
                        }else if(currentToken == ')'){
                            lexicalUnits.add(new LexicalUnit("Identifier", strBuilder.toString()));
                            strBuilder.delete(0, strBuilder.length());
                            currentState = 8;
                        }else if(currentToken == '+'){
                            lexicalUnits.add(new LexicalUnit("Identifier", strBuilder.toString()));
                            strBuilder.delete(0, strBuilder.length());
                            currentState = 3;
                        }else if(currentToken == '-'){
                            lexicalUnits.add(new LexicalUnit("Identifier", strBuilder.toString()));
                            strBuilder.delete(0, strBuilder.length());
                            currentState = 4;                           
                        }else if(currentToken == '*'){
                            lexicalUnits.add(new LexicalUnit("Identifier", strBuilder.toString()));
                            strBuilder.delete(0, strBuilder.length());
                            currentState = 5;
                        }else if(currentToken == '/'){
                            lexicalUnits.add(new LexicalUnit("Identifier", strBuilder.toString()));
                            strBuilder.delete(0, strBuilder.length());
                            currentState = 6;                           
                        }else if(Character.isDigit(currentToken) || Character.isAlphabetic(currentToken)){
                            strBuilder.append(currentToken);
                            cpt++;
                        }else{
                            System.out.println("Error character not exist in alphabet postion : " + cpt);
                            lexicalError = true;
                        }
                        break;
                case 2: if(currentToken == '#'){
                            lexicalUnits.add(new LexicalUnit("Integer", strBuilder.toString()));
                            strBuilder.delete(0, strBuilder.length());
                            cpt++;
                        }else if(currentToken == '('){
                            lexicalUnits.add(new LexicalUnit("Integer", strBuilder.toString()));
                            strBuilder.delete(0, strBuilder.length());
                            currentState = 7;
                        }else if(currentToken == ')'){
                            lexicalUnits.add(new LexicalUnit("Integer", strBuilder.toString()));
                            strBuilder.delete(0, strBuilder.length());
                            currentState = 8;
                        }else if(currentToken == '+'){
                            lexicalUnits.add(new LexicalUnit("Integer", strBuilder.toString()));
                            strBuilder.delete(0, strBuilder.length());
                            currentState = 3;
                        }else if(currentToken == '-'){
                            lexicalUnits.add(new LexicalUnit("Integer", strBuilder.toString()));
                            strBuilder.delete(0, strBuilder.length());
                            currentState = 4;                            
                        }else if(currentToken == '*'){
                            lexicalUnits.add(new LexicalUnit("Integer", strBuilder.toString()));
                            strBuilder.delete(0, strBuilder.length());
                            currentState = 5;
                        }else if(currentToken == '/'){
                            lexicalUnits.add(new LexicalUnit("Integer", strBuilder.toString()));
                            strBuilder.delete(0, strBuilder.length());
                            currentState = 6;                            
                        }else if(Character.isDigit(currentToken)){
                            strBuilder.append(currentToken);
                            cpt++;
                        }else if(Character.isAlphabetic(currentToken)){
                            System.out.println("Lexical error integer must not contain letter postion : " + cpt);
                            lexicalError = true;
                        }else{
                            System.out.println("Error character not exist in alphabet postion : " + cpt);
                            lexicalError = true;
                        }
                        break;
                case 3: if(currentToken == '#'){
                            cpt++;
                        }else if(currentToken == '('){
                            currentState = 7;
                        }else if(currentToken == ')'){
                            currentState = 8;
                        }else if(currentToken == '+'){
                            strBuilder.append(currentToken);
                            lexicalUnits.add(new LexicalUnit("+", strBuilder.toString()));
                            strBuilder.delete(0, strBuilder.length());
                            cpt++;
                        }else if(currentToken == '-'){
                            currentState = 4;
                        }else if(currentToken == '*'){
                            currentState = 5;
                        }else if(currentToken == '/'){
                            currentState = 6;
                        }else if(Character.isDigit(currentToken)){
                            currentState = 2;
                        }else if(Character.isAlphabetic(currentToken)){
                            currentState = 1;
                        }else{
                            System.out.println("Error character not exist in alphabet postion : " + cpt);
                            lexicalError = true;
                        }
                        break;
                case 4: if(currentToken == '#'){
                            cpt++;
                        }else if(currentToken == '('){
                            currentState = 7;
                        }else if(currentToken == ')'){
                            currentState = 8;
                        }else if(currentToken == '+'){
                            currentState = 3;
                        }else if(currentToken == '-'){
                            strBuilder.append(currentToken);
                            lexicalUnits.add(new LexicalUnit("-", strBuilder.toString()));
                            strBuilder.delete(0, strBuilder.length());
                            cpt++;
                        }else if(currentToken == '*'){
                            currentState = 5;
                        }else if(currentToken == '/'){
                            currentState = 6;
                        }else if(Character.isDigit(currentToken)){
                            currentState = 2;
                        }else if(Character.isAlphabetic(currentToken)){
                            currentState = 1;
                        }else{
                            System.out.println("Error character not exist in alphabet postion : " + cpt);
                            lexicalError = true;
                        }
                        break;
                case 5: if(currentToken == '#'){
                            cpt++;
                        }else if(currentToken == '('){
                            currentState = 7;
                        }else if(currentToken == ')'){
                            currentState = 8;
                        }else if(currentToken == '+'){
                            currentState = 3;
                        }else if(currentToken == '-'){
                            currentState = 4;
                        }else if(currentToken == '*'){
                            strBuilder.append(currentToken);
                            lexicalUnits.add(new LexicalUnit("*", strBuilder.toString()));
                            strBuilder.delete(0, strBuilder.length());
                            cpt++;
                        }else if(currentToken == '/'){
                            currentState = 6;
                        }else if(Character.isDigit(currentToken)){
                            currentState = 2;
                        }else if(Character.isAlphabetic(currentToken)){
                            currentState = 1;
                        }else{
                            System.out.println("Error character not exist in alphabet postion : " + cpt);
                            lexicalError = true;
                        }
                        break;
                case 6: if(currentToken == '#'){
                            cpt++;
                        }else if(currentToken == '('){
                            currentState = 7;
                        }else if(currentToken == ')'){
                            currentState = 8;
                        }else if(currentToken == '+'){
                            currentState = 3;
                        }else if(currentToken == '-'){
                            currentState = 4;
                        }else if(currentToken == '*'){
                            currentState = 5;
                        }else if(currentToken == '/'){
                            strBuilder.append(currentToken);
                            lexicalUnits.add(new LexicalUnit("/", strBuilder.toString()));
                            strBuilder.delete(0, strBuilder.length());
                            cpt++;
                        }else if(Character.isDigit(currentToken)){
                            currentState = 2;
                        }else if(Character.isAlphabetic(currentToken)){
                            currentState = 1;
                        }else{
                            System.out.println("Error character not exist in alphabet postion : " + cpt);
                            lexicalError = true;
                        }
                        break;
                case 7: if(currentToken == '#'){
                            cpt++;
                        }else if(currentToken == '('){
                            strBuilder.append(currentToken);
                            lexicalUnits.add(new LexicalUnit("(", strBuilder.toString()));
                            strBuilder.delete(0, strBuilder.length());
                            cpt++;
                        }else if(currentToken == ')'){
                            currentState = 8;
                        }else if(currentToken == '+'){
                            currentState = 3;
                        }else if(currentToken == '-'){
                            currentState = 4;
                        }else if(currentToken == '*'){
                            currentState = 5;
                        }else if(currentToken == '/'){
                            currentState = 6;
                        }else if(Character.isDigit(currentToken)){
                            currentState = 2;
                        }else if(Character.isAlphabetic(currentToken)){
                            currentState = 1;
                        }else{
                            System.out.println("Error character not exist in alphabet postion : " + cpt);
                            lexicalError = true;
                        }  
                        break;
                case 8: if(currentToken == '#'){
                            cpt++;
                        }else if(currentToken == '('){
                            currentState = 7;
                        }else if(currentToken == ')'){
                            strBuilder.append(currentToken);
                            lexicalUnits.add(new LexicalUnit(")", strBuilder.toString()));
                            strBuilder.delete(0, strBuilder.length());
                            cpt++;
                        }else if(currentToken == '+'){
                            currentState = 3;
                        }else if(currentToken == '-'){
                            currentState = 4;
                        }else if(currentToken == '*'){
                            currentState = 5;
                        }else if(currentToken == '/'){
                            currentState = 6;
                        }else if(Character.isDigit(currentToken)){
                            currentState = 2;
                        }else if(Character.isAlphabetic(currentToken)){
                            currentState = 1;
                        }else{
                            System.out.println("Error character not exist in alphabet postion : " + cpt);
                            lexicalError = true;
                        }  
                        break;
            }
        }
        lexicalUnits.add(new LexicalUnit("#", "#"));
        
        return lexicalUnits;
    }
    
    
    public static boolean syntaxAnalysis(List<LexicalUnit> lexicalUnits){
        SyntaxUnit syntaxUnit = new SyntaxUnit();
        syntaxUnit.createPredecitveTable();
        
        Stack stack = new Stack();
        stack.push("#");
        stack.push("Expression");
        
        boolean endAnalysis = false;
        boolean syntaxCorrect = false;
        int cpt = 0;
        int ct = syntaxUnit.terminal.get(lexicalUnits.get(cpt).type);
        
        while(!endAnalysis){
            if(syntaxUnit.terminal.containsKey(stack.lastElement().toString()) && !stack.lastElement().toString().equals("#")){
                if(lexicalUnits.get(cpt).type.equals(stack.lastElement())){
                    cpt++;
                    ct = syntaxUnit.terminal.get(lexicalUnits.get(cpt).type);
                    stack.pop();
                }
                else{
                    System.out.println("\nError Syntax");
                    endAnalysis = true;
                }
            }
            else{
                if(syntaxUnit.notTerminal.containsKey(stack.lastElement().toString())){
                    int row = syntaxUnit.notTerminal.get(stack.lastElement().toString());
                    if(syntaxUnit.matrix[row][ct] == null){
                        System.out.println("\nError Syntax");
                        endAnalysis = true;
                    }
                    else{
                        stack.pop();
                        for(int i = syntaxUnit.matrix[row][ct].size() - 1; i >= 0; i--){
                            if(syntaxUnit.matrix[row][ct].get(i).equals("ε"))
                                continue;
                            stack.push(syntaxUnit.matrix[row][ct].get(i));
                        }
                    }
                }
                else{
                    if(lexicalUnits.get(cpt).type.equals("#")){
                        System.out.println("\nSyntax Correct");
                        syntaxCorrect = true;
                        endAnalysis = true;
                    }
                    else{
                        System.out.println("\nError Syntax");
                        endAnalysis = true;
                    }
                }
            }
        }
        return syntaxCorrect;
    } 
          
}
