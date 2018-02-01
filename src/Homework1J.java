/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework1j;

/**
 *
 * @author User
 */
public class Homework1J {
     static Node root= null;
      public static String input ;
     public static int inputlength;
 
     public static void main(String[] args) {
       if (args.length > 0) {
             input = args[0];
             inputlength = input.length() - 1;       }
  
 
         Inorder(new Node(input.charAt(inputlength)));
  
          inflix(root);
  
          System.out.println("=" + calculate(root));
  
      }
     public static void Inorder(Node a) {
         if (root == null){
             root = a;
             inputlength--;
          }

        if (Operator(a)) {
             root.right = new Node(input.charAt(inputlength));
             inputlength--;
              Inorder(a.right);

             root.left = new Node(input.charAt(inputlength));
             inputlength--;
             Inorder(a.left);
             
         } 
      }
  
      public static boolean Operator(Node a) {
         switch (a.key) {
             case '+':
                 return true;
             case '-':
                 return true;
             case '*':
                 return true;
             case '/':
                 return true;
             default:
                 return false;
         }
     }
 
     public static int calculate(Node a) {
         if (Operator(a)) {
             switch (a.key) {
                 case '+':
                     return calculate(a.left) + calculate(a.right);
                 case '-':
                     return calculate(a.left) - calculate(a.right);
                 case '*':
                     return calculate(a.left) * calculate(a.right);
                 case '/':
                     return calculate(a.left) / calculate(a.right);
                 default:;
                     return 0;
             }
 
         } else {
             return Character.getNumericValue(a.key);
         }
     }
 
     public static void inflix(Node a) {
         if (a == root) {
              inflix(a.left);
             System.out.print(a.key);
             inflix(a.right);
         } else if (Operator(a)) {
             System.out.print('(');
             inflix(a.left);
             System.out.print(a.key);
             inflix(a.right);
              System.out.print(')');
          } else {
              System.out.print(a.key);
          }
     }
 
     public static class Node{
 
         Node left;
         Node right;
         char key;
 
         public Node(char key) {
             this.key = key;
         }
     }
 }