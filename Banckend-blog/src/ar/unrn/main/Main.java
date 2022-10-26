package ar.unrn.main;

import ar.unrn.servicios.ServicePaginasMongo;
import ar.unrn.servicios.ServicePostMongo;
import ar.unrn.web.WebApi;

public class Main {

	public static void main(String[] args) {
		 String host = "localhost";
		 int port = 27017;
		 WebApi servicio = new WebApi(7004, new ServicePaginasMongo(host, port), new ServicePostMongo(host, port));
		 servicio.start();
	}
}
