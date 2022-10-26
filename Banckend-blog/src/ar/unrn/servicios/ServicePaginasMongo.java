package ar.unrn.servicios;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
//import com.mongodb.client.model.Updates.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import ar.unrn.api.ServicePaginas;

public class ServicePaginasMongo implements ServicePaginas{

	private String host;
	private int port;
	
	public ServicePaginasMongo(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	@Override
	public String obtenerPagina(String id) {
		try (MongoClient mongoClient = new MongoClient(host , port)) {
			
			 MongoDatabase database = mongoClient.getDatabase("Blog");
			 MongoCollection<Document> collection = database.getCollection("Paginas");
			 			 
			 FindIterable<Document> i = collection.find(Filters.eq("_id", new ObjectId(id)));
			 String result = StreamSupport.stream(i.spliterator(), false).map(Document::toJson).collect(Collectors.joining(", ", "[", "]"));		 
			
		return result;
		}
	}
}
