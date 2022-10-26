package ar.unrn.web;

import io.javalin.Javalin;
import java.util.Map;
import ar.unrn.api.ServicePaginas;
import ar.unrn.api.ServicePost;
import io.javalin.http.Handler;

public class WebApi {

	private int webPort;
	private ServicePaginas sp;
	private ServicePost spost;
	    
	public WebApi(int webPort, ServicePaginas sp, ServicePost spost) {
		 this.webPort = webPort;
		 this.sp = sp;
		 this.spost = spost;
	}

	 public void start() {
		 Javalin app = Javalin.create(config -> 
		 	config.plugins.enableCors(cors -> {
		        cors.add(it -> {
		            it.anyHost();
		        });
		 })).start(this.webPort);
		  
		 app.get("/pages/{id}", encontrarPagina());
		 app.get("/posts/latest", ultimosPosts());
		 app.get("/posts/{postId}", encontrarPost());
		 app.get("/posts/author/{name}", obtenerPostPorAutor());
		 app.get("/posts/author/{text}", obtenerPostPorContenido());
		 app.get("/byauthor", obtenerCantPostPorAutor());
		 app.get("/search/{text}", buscarTexto());
		 
		 app.exception(Exception.class, (e, ctx) -> {
		   ctx.json(Map.of("result", "error", "message", "Ups... algo se rompió.: " + e.getMessage()));
		  });
	 }

	 private Handler encontrarPagina() {
		return ctx -> {
	      ctx.result(sp.obtenerPagina(ctx.pathParam("id")));
		};
	}
	 
	 private Handler ultimosPosts() {
		return ctx -> {
		  ctx.result(spost.obtenerUltimosCuatroPosts());
		};
	}
	 
	 private Handler encontrarPost() {
		return ctx -> {
	      ctx.result(spost.obtenerPost(ctx.pathParam("postId")));
		};
	 }
	 
	 private Handler obtenerPostPorAutor() {
		return ctx -> {
		  ctx.result(spost.obtenerPostPorNombreAutor(ctx.pathParam("name")));
		};
	 }
	 
	 private Handler obtenerPostPorContenido() {
		return ctx -> {
		  ctx.result(spost.buscarPostPorContenido(ctx.pathParam("text")));
		};
	 }
	 
	 private Handler obtenerCantPostPorAutor() {
		return ctx -> {
	      ctx.result(spost.cantPostPorAutor());
		};
	 }
	 
	 private Handler buscarTexto() {
		return ctx -> {
		  ctx.result(spost.buscarPostPorContenido(ctx.pathParam("text")));
		};
	 }
	 
	 
	 
	 
}
