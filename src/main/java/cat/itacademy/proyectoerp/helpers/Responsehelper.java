package cat.itacademy.proyectoerp.helpers;

import java.util.HashMap;

import org.springframework.stereotype.Component;
@Component
public class Responsehelper {
	
	public  HashMap<String, Object> responseWasOkWithEntity(String succes, String message, String nameEntityReturned, Object entityObject){
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		 map.put("success", succes);
	      map.put("message", message);
	      map.put(nameEntityReturned, entityObject);
		return map;
	}
	
	public  HashMap<String, Object> responsewaswrong(String succes, String message){
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		 map.put("success", succes);
	      map.put("message", message);
	     
		return map;
	}
	
	public  HashMap<String, Object> responseSimpleWasOk(String succes, String message){
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		 map.put("success", succes);
	      map.put("message", message);
	      
		return map;
	}
}
