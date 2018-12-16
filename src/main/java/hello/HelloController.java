package hello;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class HelloController {
    
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
    
    
    @RequestMapping(
    		value = "/process", 
    	    method = RequestMethod.POST)
    public void process(@RequestBody Map<String, Object> payload) 
    		throws Exception {
    	 System.out.println(payload);
    }
    
    
    @RequestMapping(
    		value = "/print", 
    	    method = RequestMethod.POST)
    public void print(@RequestBody Map<String, Object> payload) 
    		throws Exception {
    	 System.out.println(payload);
    	 
    	 LoadPrintRequests loadRequests = new LoadPrintRequests();
         
         Printer printer = null;
         List<QueryDocumentSnapshot> documents = loadRequests.documents;
         if(documents.size() > 0) {
         	printer = new Printer();
         	
         	for (QueryDocumentSnapshot document : loadRequests.documents) {
       	      Map<String, Object> doc = document.getData();
       	      
       	      ReceiptToPrint receipt = new ReceiptToPrint();
       	      receipt.setOrderid(doc.get("orderId"));
       	      receipt.setProducts(doc.get("products"));
       	      receipt.setTotalSum(doc.get("totalPrice"));
       	      receipt.setTotalSum(doc.get("moneyPaid"));
       	      receipt.setTotalChange();
       	      
       	      receipt.print(printer);
       	    }
         	
         	printer = null;
         }  
    }
    
    
    
    // mvn package
    // java -jar target/pos-javaserver-0.1.0.jar
    
  //  curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"name":"value"}' http://localhost:8080/process
}
