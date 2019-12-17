/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntaxanalysis;

import java.util.List;

/**
 *
 * @author hp
 */
public class Rules {
    List<String> RP;
    List<String> First;
    List<String> Follow;
    boolean hasFirst;
    boolean hasFollow;
    
    public Rules(List<String> RP){
        this.RP = RP;
        hasFirst = false;
        hasFollow = false;
    }
}
