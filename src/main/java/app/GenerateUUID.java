package app;

import java.util.UUID;

public class GenerateUUID {

	public String generateID(){
		
		String value;
		
		UUID idvalue = UUID.randomUUID();
		
		value = idvalue.toString();
		
		return value;
		
	}
	
}
