package edu.leedom.ashley;

import java.util.ArrayList;
import java.util.List;

public class UniqueIdentifier {
	// list to store the unique ids
	private static List<Integer> ids = new ArrayList<Integer>();
	private static final int RANGE = 10000; // int to store the range
	private static int index = 0; // int to store the index
	
	// adds values to the list
	static {
		for( int i = 0; i < RANGE; i++ )
		{
			ids.add(i);
		}
	} // end static
	
	// Method Name: UniqueIdentifier 
	// Parameters: None
	// Return Value(s): None
	// Description:Creates the class that allows the static method to run and add ids to a list
	private UniqueIdentifier() {
		
	} // end UniqueIdentifier()
	
	// Method Name: getIdentifier
	// Parameters: None
	// Return Value(s): int - the unique id
	// Description: Returns a new unique id end called
	public static int getIdentifier() {
		if( index > ids.size() - 1 )
		{
			index = 0;
		}
		
		return ids.get(index++);
	} // end getIdentifier()
} // end class UniqueIdentifier
