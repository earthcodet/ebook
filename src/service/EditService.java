/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.ArrayList;

/**
 *
 * @author LORD OF GAME
 */
public class EditService {
    /*
    +hasEmptyFilelds(ArrayList<String> book):boolean
    +hasEmptyBookCover(int check);boolean
    */
   public static boolean hasEmptyFilelds(ArrayList<String> book){
       for(int i = 0;i<book.size();i++){
           if(book.get(i).equals("")){
                 return true;
           }
       }
       return true;
   }
   public static boolean hasEmptyBookCover(int check){
       if(check==0){
       return true;
       }else
           return false;
   }
}
