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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hp
 */
public class SyntaxUnit {
    Map<String, Rules> leftPart;
    Map<String, Integer> terminal;
    Map<String, Integer> notTerminal;
    List<String> [] [] matrix;
    
    public void createPredecitveTable(){
                
        try{
            File file = new File("C:\\Users\\hp\\Desktop\\Code\\regle.txt");
            
            String line;
            
            leftPart = new HashMap<>();
            
            try(BufferedReader br = new BufferedReader(new FileReader(file))){
                while((line = br.readLine()) != null){
                    
                    String key = line.split(">")[0].trim();
                    List<String> value = Arrays.asList(line.split(">")[1].trim().split(" "));
                    
                    Rules rule = new Rules(value);
                    
                    leftPart.put(key, rule);
                } 
            }
            
            First("Expression");
            First("AS");
            First("Term");
            First("MS");
            First("Factor");
            
            System.out.println("\n----------------- Firsts ---------------");
            
            System.out.println(leftPart.get("Expression").First);
            System.out.println(leftPart.get("AS").First);
            System.out.println(leftPart.get("Term").First);
            System.out.println(leftPart.get("MS").First);
            System.out.println(leftPart.get("Factor").First);
              
            System.out.println("\n----------------- Follows ---------------");
            
            Follow("Expression");
            Follow("AS");
            Follow("Term");
            Follow("MS");
            Follow("Factor");

            System.out.println(leftPart.get("Expression").Follow);
            System.out.println(leftPart.get("AS").Follow);
            System.out.println(leftPart.get("Term").Follow);
            System.out.println(leftPart.get("MS").Follow);
            System.out.println(leftPart.get("Factor").Follow);
            
            System.out.println("\n----------------- Predicitve Tabale ---------------");
            
            terminal = new HashMap<>();
            notTerminal = new HashMap<>();
            
            notTerminal.put("Expression", 0);
            notTerminal.put("AS", 1);
            notTerminal.put("Term", 2);
            notTerminal.put("MS", 3);
            notTerminal.put("Factor", 4);
            
            // Terminals 
            
            terminal.put("+", 0);
            terminal.put("-", 1);
            terminal.put("*", 2);
            terminal.put("/", 3);
            terminal.put("(", 4);
            terminal.put(")", 5);
            terminal.put("Integer", 6);
            terminal.put("Identifier", 7);
            terminal.put("#", 8);
            
            // Declaration of Matrix
            
            matrix = new LinkedList[notTerminal.size()][terminal.size()];
            
            predectiveTable();
            
            print2D(matrix);
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        } 
    }
    
    public List<String> First(String key){
        int size = leftPart.get(key).RP.size();
        List<String> tmp = new LinkedList<>();
        List<String> RP = leftPart.get(key).RP;
        boolean test = false;
        
        if(leftPart.get(key).hasFirst)
            return leftPart.get(key).First;
        
        if(RP.contains("&")){
            for(int i = 0; i < size; i++){
                if(!RP.get(i).equals("&")){
                    if(test)
                        continue;
                    
                    if(leftPart.containsKey(RP.get(i))){
                        for(String str:First(RP.get(i)))
                            if(!tmp.contains(str))
                                tmp.add(str);
                    }
                    else{
                        if(!tmp.contains(RP.get(i)))
                            tmp.add(RP.get(i));
                    }
                    
                    if(tmp.contains("ε") && i+1 != size && !RP.get(i+1).equals("&"))
                        tmp.remove("ε");
                    else
                        test = true;
                }
                else{
                    test = false;
                }
            }
        }
        else{
            for(int i = 0; i < size; i++){
                if(leftPart.containsKey(RP.get(i))){
                    for(String str:First(RP.get(i)))
                        if(!tmp.contains(str))
                            tmp.add(str);
                }
                else{
                    if(!tmp.contains(RP.get(i)))
                        tmp.add(RP.get(i));
                }
                
                if(!tmp.contains("ε")){
                    break;
                }
                else{
                    if(i+1 != size)
                        tmp.remove("ε");
                }
            }
        }
        leftPart.get(key).First = tmp;
        leftPart.get(key).hasFirst = true;
        return tmp;
    }
    
    public List<String> Follow(String key){
        List<String> tmp = new LinkedList<>();
        
        if("Expression".equals(key)){
            tmp.add("#");
        }
        
        if(leftPart.get(key).hasFollow)
            return leftPart.get(key).Follow;
        
        for(Map.Entry<String, Rules> map:leftPart.entrySet()){
            List<String> RP = map.getValue().RP;
            List<Integer> indexs = new LinkedList<>();
            List<String> str;
            int size = RP.size();
            
            if(RP.contains(key)){
               for(int i = 0; i < size; i++){
                   if(RP.get(i).equals(key))
                       indexs.add(i);
               }
               
               for(int index:indexs){
                   if(index + 1 == size){
                       if(!RP.get(index).equals(map.getKey())){
                           str = Follow(map.getKey());
                           for(String s:str){
                               if(!tmp.contains(s))
                                   tmp.add(s);
                           }
                       }
                   }
                   else{
                       if(RP.get(index + 1).equals("&")){
                           if(!RP.get(index).equals(map.getKey())){
                               str = Follow(map.getKey());
                               for(String s:str){
                                   if(!tmp.contains(s))
                                       tmp.add(s);
                               }
                           }
                       }
                       else{
                           for(int i = index+1; i < size; i++){
                                if(leftPart.containsKey(RP.get(i))){
                                    str = new LinkedList<>();
                                    str.addAll(leftPart.get(RP.get(i)).First);
                                    if(str.contains("ε")){
                                       if(i+1 == size || (RP.get(i+1).equals("&") && !RP.get(i).equals(map.getKey())))
                                           str.addAll(Follow(map.getKey()));
                                       
                                       
                                       str.remove("ε");
                                       
                                       for(String s:str){
                                           if(!tmp.contains(s))
                                               tmp.add(s);
                                       }
                                    }
                                    else{
                                        for(String s:str){
                                           if(!tmp.contains(s))
                                               tmp.add(s);
                                        }
                                       break;
                                    }
                                   
                                }
                               else{
                                   if(!RP.get(i).equals("&")){
                                       if(!tmp.contains(RP.get(i)))
                                           tmp.add(RP.get(i));
                                   }
                                   break;
                               }
                           }
                       }
                       
                   }
               }
            }
        }
        leftPart.get(key).Follow = tmp;
        leftPart.get(key).hasFollow = true;
        return tmp;
    }
    
    public void predectiveTable(){
        
        for(Map.Entry<String, Rules> map:leftPart.entrySet()){
            Rules rule = map.getValue();
            int x = notTerminal.get(map.getKey());
            
            if(rule.RP.contains("&")){
                String [] array = String.join(" ", rule.RP).split("&");
                for(String str:array){
                    List<String> list = Arrays.asList(str.trim().split(" "));
                    
                    if(list.contains("ε")){
                        for(String tmp:rule.Follow){
                            int y = terminal.get(tmp);
                            if(matrix[x][y] == null){
                                matrix[x][y] = new LinkedList<>();
                                matrix[x][y].add("ε");
                            }
                        }
                    }
                    else{
                        for(String tmp:rule.First){
                            if(tmp.equals(list.get(0))){
                                if(tmp.equals("ε"))
                                    continue;
                                int y = terminal.get(tmp);
                                if(matrix[x][y] == null){
                                    matrix[x][y] = new LinkedList<>();
                                    matrix[x][y].addAll(list);
                                }
                            }
                        }
                    }
                }   
            }
            else{
                if(rule.RP.contains("ε")){
                    for(String str:rule.Follow){
                        int y = terminal.get(str);
                        if(matrix[x][y] == null){
                            matrix[x][y] = new LinkedList<>();
                            matrix[x][y].add("ε");
                        }
                    }
                }
                else{
                    for(String str:rule.First){
                        if(str.equals("ε"))
                            continue;
                        int y = terminal.get(str);
                        if(matrix[x][y] == null){
                            matrix[x][y] = new LinkedList<>();
                            matrix[x][y].addAll(rule.RP);
                        }
                    }
                }
            }
        }
    }
    
    public void print2D(List<String> mat[][]){ 
        for (List<String>[] row : mat)
            System.out.println(Arrays.toString(row)); 
    } 
 
}
