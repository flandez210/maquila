package ws.maquila.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ws.maquila.repository.ProviderRepository;
import ws.maquila.model.Provider;

@Service
@Transactional
public class ProviderService {
	@Autowired
    private ProviderRepository repository;

    public List<Provider> getAll() {
		return repository.findAll();
	}

	public Provider save(Provider provider) {
		return repository.save(provider);
	}

	public Provider getById(Integer idProvider) {
		return repository.findById(idProvider).get();
	}

	public void delete(Integer idProvider) {
		repository.deleteById(idProvider);
	}

    public Provider findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    public Provider update(Long id, Provider convertToEntity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public void delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }    
}
