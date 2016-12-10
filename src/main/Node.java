/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author Caio
 */
public class Node {
    ArrayList<Node> childList;
    public int name;
    public Node(int name){
                this.name=name;
                childList=new ArrayList();
    }
    
    public void addChild(Node node){
                if(!(this.name==node.name) ){
                        childList.add(node);
                }
    }
    
    
    public void getNextChild(int goal, ArrayList path, boolean firstTime){
                if(this.name==goal && !firstTime){
                        path.add(new Integer(this.name));
                       
                }               
                else{                     
                        if( childList.size()>0 ){
                                Node tmpNode=(Node)childList.remove(0);
                                tmpNode.removeChild(this); 
                                path.add(new Integer(this.name));
                                tmpNode.getNextChild(goal,path,false);
                        }
                }
                
    }
    public String toString(){
        return name+"";
    }
    public void removeChild(Node node){
                childList.remove(node);
    }
    
    public boolean hasMoreChilds(){
                return childList.size()>0;
    }
    
}
