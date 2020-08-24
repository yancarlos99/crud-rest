package br.com.devmedia.webservice.model.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.devmedia.webservice.exceptions.DAOException;
import br.com.devmedia.webservice.exceptions.ErrorCode;
import br.com.devmedia.webservice.model.domain.Marca;

public class MarcaDAO {

	public List<Marca> getAll() {
		EntityManager em = JPAUtil.getEntityManager();
		List<Marca> marcas = null;

		try {
			marcas = em.createQuery("select m from Marca m", Marca.class).getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException("Erro ao recuperar todas as marcas do banco: " + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getCode());
		} finally {
			em.close();
		}

		return marcas;
	}

	public Marca getById(long id) {
		EntityManager em = JPAUtil.getEntityManager();
		Marca marca = null;

		if (id <= 0) {
			throw new DAOException("O id precisa ser maior do que 0.", ErrorCode.BAD_REQUEST.getCode());
		}

		try {
			marca = em.find(Marca.class, id);
		} catch (RuntimeException ex) {
			throw new DAOException("Erro ao buscar marca por id no banco de dados: " + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getCode());
		} finally {
			em.close();
		}

		if (marca == null) {
			throw new DAOException("Marca de id " + id + " não existe.", ErrorCode.NOT_FOUND.getCode());
		}

		return marca;
	}

	public Marca save(Marca marca) {
		EntityManager em = JPAUtil.getEntityManager();

		if (!marcaIsValid(marca)) {
			throw new DAOException("Marca com dados incompletos.", ErrorCode.BAD_REQUEST.getCode());
		}

		try {
			em.getTransaction().begin();
			em.persist(marca);
			em.getTransaction().commit();
		} catch (RuntimeException ex) {
			em.getTransaction().rollback();
			throw new DAOException("Erro ao salvar marca no banco de dados: " + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getCode());
		} finally {
			em.close();
		}
		return marca;
	}

	public Marca update(Marca marca) {
	        EntityManager em = JPAUtil.getEntityManager();
	        Marca marcaManaged = null;

	        if (marca.getId() <= 0) {
	            throw new DAOException("O id precisa ser maior do que 0.",
	                ErrorCode.BAD_REQUEST.getCode());
	        }
	        if (!marcaIsValid(marca)) {
	            throw new DAOException("Marca com dados incompletos.",
	                ErrorCode.BAD_REQUEST.getCode());
	        }

	        try {
	            em.getTransaction().begin();
	            marcaManaged = em.find(Marca.class, marca.getId());
	            marcaManaged.setNome(marca.getNome());
	            marcaManaged.setCategoria(marca.getCategoria());
	            em.getTransaction().commit();
	        } catch (NullPointerException ex) {
	            em.getTransaction().rollback();
	            throw new DAOException("Marca informada para atualização não existe: " + ex.getMessage(),
	                ErrorCode.NOT_FOUND.getCode());
	        } catch (RuntimeException ex) {
	            em.getTransaction().rollback();
	            throw new DAOException("Erro ao atualizar marcano banco de dados: " + ex.getMessage(),
	                ErrorCode.SERVER_ERROR.getCode());
	        } finally {
	            em.close();
	        }
	        return marcaManaged;
	    }

	public Marca delete(Long id) {
	        EntityManager em = JPAUtil.getEntityManager();
	        Marca marca = null;

	        if (id <= 0) {
	            throw new DAOException("O id precisa ser maior do que 0.", ErrorCode.BAD_REQUEST.getCode());
	        }

	        try {
	            em.getTransaction().begin();
	            marca = em.find(Marca.class, id);
	            em.remove(marca);
	            em.getTransaction().commit();
	        } catch (IllegalArgumentException ex) {
	            em.getTransaction().rollback();
	            throw new DAOException("Marca informada para remoção não existe: " + ex.getMessage(),
	                ErrorCode.NOT_FOUND.getCode());
	        } catch (RuntimeException ex) {
	            em.getTransaction().rollback();
	            throw new DAOException("Erro ao remover marca do banco de dados: " + ex.getMessage(),
	                ErrorCode.SERVER_ERROR.getCode());
	        } finally {
	            em.close();
	        }

	        return marca;
	    }

	public boolean marcaIsValid(Marca marca) {
		try {
			if ((marca.getNome().isEmpty()) || (marca.getCategoria().isEmpty()))
				return false;
		} catch (NullPointerException ex) {
			throw new DAOException("Marca com dados incompletos.", ErrorCode.BAD_REQUEST.getCode());
		}

		return true;
	}

	public List<Marca> getByPagination(int firstResult, int maxResults) {
	        List<Marca> marcas;
	        EntityManager em = JPAUtil.getEntityManager();

	        try {
	            marcas = em.createQuery("select m from Marca m", Marca.class)
	                    .setFirstResult(firstResult - 1)
	                    .setMaxResults(maxResults)
	                    .getResultList();
	        } catch (RuntimeException ex) {
	            throw new DAOException("Erro ao buscar marcas no banco de dados: " + ex.getMessage(),
	                ErrorCode.SERVER_ERROR.getCode());
	        } finally {
	            em.close();
	        }

	        if (marcas.isEmpty()) {
	            throw new DAOException("Página com marcas vazia.",
	                ErrorCode.NOT_FOUND.getCode());
	        }

	        return marcas;
	    }

	public List<Marca> getByName(String name) {
	        EntityManager em = JPAUtil.getEntityManager();
	        List<Marca> marcas = null;

	        try {
	            marcas = em.createQuery("select m from Marca m where m.nome like :name", Marca.class)
	                    .setParameter("name", "%" + name + "%")
	                    .getResultList();
	        } catch (RuntimeException ex) {
	            throw new DAOException("Erro ao buscar marcas por nome no banco de dados: " + ex.getMessage(),
	                ErrorCode.SERVER_ERROR.getCode());
	        } finally {
	            em.close();
	        }

	        if (marcas.isEmpty()) {
	            throw new DAOException("A consulta não encontrou marcas.", ErrorCode.NOT_FOUND.getCode());
	        }

	        return marcas;
	    }

}
