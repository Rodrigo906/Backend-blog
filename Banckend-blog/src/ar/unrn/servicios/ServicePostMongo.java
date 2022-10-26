package ar.unrn.servicios;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

import ar.unrn.api.ServicePost;

public class ServicePostMongo implements ServicePost{

	
	private String host;
	private int port;
	
	public ServicePostMongo(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	@Override
	public String obtenerUltimosCuatroPosts() {
		try (MongoClient mongoClient = new MongoClient(host , port)) {
			
			 MongoDatabase database = mongoClient.getDatabase("Blog");
			 MongoCollection<Document> collection = database.getCollection("Posts");

			 FindIterable<Document> i = collection.find().sort(Sorts.descending("date")).limit(4);

			 String result = StreamSupport.stream(i.spliterator(), false).map(Document::toJson)
					 .collect(Collectors.joining(", ", "[", "]"));		 
			  
			 return result; 
		}
	}
	
	@Override
	public String obtenerPost(String id) {
		try (MongoClient mongoClient = new MongoClient(host , port)) {
			
			 MongoDatabase database = mongoClient.getDatabase("Blog");
			 MongoCollection<Document> collection = database.getCollection("Posts");

			 FindIterable<Document> i = collection.find(Filters.eq("_id", new ObjectId(id)));
			 String result = StreamSupport.stream(i.spliterator(), false).map(Document::toJson)
					 .collect(Collectors.joining(", ", "[", "]"));		 
			  
			 return result; 
		}
	}

	@Override
	public String cantPostPorAutor() {
		try (MongoClient mongoClient = new MongoClient(host , port)) {
			
			 MongoDatabase database = mongoClient.getDatabase("Blog");
			 MongoCollection<Document> collection = database.getCollection("Posts");
			 
			 AggregateIterable<Document> document = collection.aggregate(
					    Arrays.asList(
					        Aggregates.group("$author", Accumulators.sum("count", 1))
					    ));
			 String result = StreamSupport.stream(document.spliterator(), false).map(Document::toJson)
					 .collect(Collectors.joining(", ", "[", "]"));	
			 
			 return result;
		}
	}
	
	@Override
	public String obtenerPostPorNombreAutor(String nombre) {
		try (MongoClient mongoClient = new MongoClient(host , port)) {
			
			 MongoDatabase database = mongoClient.getDatabase("Blog");
			 MongoCollection<Document> collection = database.getCollection("Posts");

			 FindIterable<Document> i = collection.find(Filters.eq("author", nombre));
			 
			 String result = StreamSupport.stream(i.spliterator(), false).map(Document::toJson)
					 .collect(Collectors.joining(", ", "[", "]")); 
			 
			 return result;
		}
	}

	@Override
	public String buscarPostPorContenido(String texto) {
		try (MongoClient mongoClient = new MongoClient(host , port)) {
			MongoDatabase database = mongoClient.getDatabase("Blog");
			MongoCollection<Document> collection = database.getCollection("Posts");
		
			FindIterable<Document> i = collection.find(Filters.text(texto));
			String result = StreamSupport.stream(i.spliterator(), false).map(Document::toJson)
					 .collect(Collectors.joining(", ", "[", "]")); 
			
			return result;
		}
	}

}
