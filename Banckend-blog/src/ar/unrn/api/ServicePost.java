package ar.unrn.api;

import java.util.List;

public interface ServicePost {

	String obtenerUltimosCuatroPosts();
	
	String obtenerPost(String id);
	
	String cantPostPorAutor();
	
	String obtenerPostPorNombreAutor(String nombre);
	
	String buscarPostPorContenido(String texto);
}
