package br.com.devmedia.webservice.service;

import java.util.List;

import br.com.devmedia.webservice.model.dao.MarcaDAO;
import br.com.devmedia.webservice.model.domain.Marca;

public class MarcaService {
	
	private MarcaDAO dao = new MarcaDAO();
	
	  public List<Marca> getMarcas() {
	        return dao.getAll();
	    }

	    public Marca getMarca(Long id) {
	        return dao.getById(id);
	    }

	    public Marca saveMarca(Marca marca) {
	        return dao.save(marca);
	    }

	    public Marca updateMarca(Marca marca) {
	        return dao.update(marca);
	    }

	    public Marca deleteMarca(Long id) {
	        return dao.delete(id);
	    }

	    public List<Marca> getMarcasByPagination(int firstResult, int maxResults) {
	        return dao.getByPagination(firstResult, maxResults);
	    }

	    public List<Marca> getMarcaByName(String name) {
	        return dao.getByName(name);
	    }


}
