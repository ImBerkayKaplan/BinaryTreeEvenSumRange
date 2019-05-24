package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class evenSumRange {
    public class Node {

        long data;
        Node right;
        Node left;
        boolean isEven;
        
        public Node(long data) {
            this.data = data;
            this.isEven = (data % 2 == 0);
        }

        public void setIsEven(boolean even) {
            this.isEven = even;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public void Insert(Node node) {
        		this.setIsEven(!(this.isEven ^ node.isEven));
            if (this.data > node.data) {
                if (this.left != null) {
                    this.left.Insert(node);
                } else {
                    this.left = node;
                }
            } else {
                if (this.right != null) {
                    this.right.Insert(node);
                } else {
                    this.right = node;
                }
            }
        }

        //Find the sum of the values between low and high and determine if it is even
        public boolean evenSumRange(long low, long high) {
            boolean result = true;
            result = !(this.GE(high)^this.LE(low));
            result = !(result ^ this.isEven);
            return result;
        }

      //Find the values greater or equal to high and determine if it is even
        public boolean GE(long high) {
        	boolean result = true, temp1 = true, first = true;
        	Node temp2 = this;
        	while(temp2!=null) {
        		if(temp2.data>high) {
        			if(temp2.left!=null) {
        				if(first){
        					result = !(temp2.isEven^temp2.left.isEven);
        					first = false;
        				}else{
        				    temp1 = !(temp2.isEven^temp2.left.isEven);
        				    result = !(result^temp1);
        				}
        			}else{
        				result = !(result^temp2.isEven);
        			}
        			temp2 = temp2.left;
        		}else {
        			temp2 = temp2.right;
        		}
        	}
        	return result;
        }
        
        //Find the values less or equal to low and determine if it is even
        public boolean LE(long low) {
        	boolean result = true, temp1 = true, first = true;
        	Node temp2 = this;
        	while(temp2!=null) {
        		if(temp2.data<low) {
        			if(temp2.right!=null) {
        				if(first){
        					result = !(temp2.isEven^temp2.right.isEven);
        					first = false;
        				}else{
        				    temp1 = !(temp2.isEven^temp2.right.isEven);
        				    result = !(result^temp1);
        				}
        			}else{
        				result = !(result^temp2.isEven);
        			}
        			temp2 = temp2.right;
        		}else {
        			temp2 = temp2.left;
        		}
        	}
        	return result;
        }
    }
    
    
    public static void main(String[] args) throws IOException {

	//Error checking to make sure the correct number of arguments are entered
	if(args.length != 2){
	    System.err.println("Please enter 2 arguments, which are the data file as the first argument to build the binary tree, and the range file as the second argument to add all variables that are within the range in the binary tree.");
	    System.exit(1);
	}

    	//Open the files and initialize the root
        evenSumRange bt = new evenSumRange();
        BufferedReader bufferedReader1 = new BufferedReader(new FileReader(args[0]));
        BufferedReader bufferedReader2 = new BufferedReader(new FileReader(args[1]));
        String line = bufferedReader1.readLine();
        evenSumRange.Node root = bt.new Node(Long.parseLong(line));
        
        //Insert the data from the file to a binary tree
        while ((line = bufferedReader1.readLine()) != null) {
            evenSumRange.Node node = bt.new Node(Long.parseLong(line));
            root.Insert(node);
        }

        //Get the ranges and find the sum of the ranges and determine if it is even
        while ((line = bufferedReader2.readLine()) != null) {
        	long low=0, high=0;
        	int startingIndex = 0;
        	boolean blow = true;
        	for(int counter1 = 0; counter1<line.length();counter1++){
        		if(line.charAt(counter1)==' '){
        			if(blow){
        				blow = false;
        				low = Long.parseLong(line.substring(0,counter1));
        			}
        			startingIndex = counter1;
        		}
        	}
        	high = Long.parseLong(line.substring(startingIndex+1));
        	if(root.evenSumRange(low, high)){
        		System.out.println("Range ["+low+","+high+"]: even sum");
        	}else{
        		System.out.println("Range ["+low+","+high+"]: odd sum");
        	}
        }

        bufferedReader1.close();
        bufferedReader2.close();
    }


}

