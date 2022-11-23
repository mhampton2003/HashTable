package project7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Hash {
	
	//Node class
	static class Node {
		String key;
		Node next;
		
		//Node constructor
		public Node (String data) {
			key = data;
			next = null;
		}
	}
	
	int HASH = 1189;
	int counter;
	Node[] bucketArray;
	
	//Hash Table constructor
	public Hash () {
		bucketArray = new Node[HASH];
		for (int i = 0; i < HASH; i++) {
			bucketArray[i] = null;
		}
	}
	
	private static String cleanUp(String str) {
    	
    	String s = "";
    	for (int i = 0; i < str.length(); i++) {
    		if ((str.charAt(i) >= 97 && str.charAt(i) <= 122) || (str.charAt(i) >= 65 && str.charAt(i) <= 90) || (str.charAt(i) == 39)) {
    			s = s + str.charAt(i);
    		}
    	}
    	
        return s.toLowerCase();
    }
	
	//converts string to numerical value - Hash Function
	//O(n)
	private int convert(String input) {
		int sum = 0;
		
		//adds together the ascii value of each char 
		for (int i = 0; i < input.length(); i++) {
			sum += (int)input.charAt(i);
		}
	
		return sum;
	}
	
	//inserts data into hash table - does not allow duplicates
	//O(n)
	public void insert(String input) {
		int newInput = convert(cleanUp(input));
		Node newNode = new Node(cleanUp(input));
		
		//if the data is already present - don't do anything with it
		if (search(cleanUp(input))) {
			//do nothing so there are no duplicates
		}
		//if data is not already in the table - put it in
		else {
			newNode.next = bucketArray[newInput % HASH];
			bucketArray[newInput % HASH] = newNode;
		}
	}
	
	//displays the data in the table
	public void display() {
		Node temp;
		//loops through each bucket
		for (int i = 0; i < HASH; i++) {
			temp = bucketArray[i];
			System.out.print("Index " + i + ": ");
			
			//loops through and checks each element in the list
			while (temp != null) {
				System.out.print(temp.key + " ");
				temp = temp.next;
			}
			
			System.out.println("");
		}
	}
	
	//searches for a word in the table
	public boolean search(String input) {
		Node temp;
		int x = convert(input) % HASH;
		temp = bucketArray[x];
		counter = 0;
		
		//if the first element in the list is null then return false
		if (bucketArray[x] == null) {
			counter++;
			return false;
		}
		
		//if first element in the list in the correct bucket is a match then return true
		if (bucketArray[x].key.equals(input)) {
			counter++;
			return true;
		}
		
		//if the bucket has an element but does not match then it searches through the list
		//if there is a match then return true
		if (bucketArray[x].key != null && !bucketArray[x].key.equals(input)) {
			while(temp != null) {
				counter++;
				if (temp.key.equals(input)) {
					return true;
				}
				temp = temp.next;
			}
		}
		//if there are no matches then return false
		return false;
	}
	
	public static void main(String args[]) throws FileNotFoundException {
		Hash hash = new Hash();
		
		Scanner fin = new Scanner(new File("input.in"));
		Scanner scnr = new Scanner(System.in);
		
		//enters text from file into table
		while (fin.hasNext()) {
			hash.insert(fin.next());
		}
		
		//displays table
		hash.display();
		
		String input = "0";
		
		//user can keep searching for words
		while (!input.equals("-1")) {
			System.out.println("Enter a word to search for or -1 to quit");
			input = scnr.next();
			
			//exits out of while loop
			if (input.equals("-1")) {
				System.out.println("Quitting");
				break;
			}
			else {
				//if user input was found in table
				if (hash.search(input)) {
					System.out.println(hash.counter + " elements were inspected");
					System.out.println(input + " was found!");
				}
				//if user input was not found in table
				else {
					System.out.println(hash.counter + " elements were inspected");
					System.out.println(input + " was not found");
				}
			}
			
		}
		
	}
}


