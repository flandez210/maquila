package ws.maquila.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import ws.maquila.repository.ManufacturingOrderFabricRepository;
import ws.maquila.model.ManufacturingOrderFabric;

@SuppressWarnings("unused")
@Service
@Transactional
public class ManufacturingOrderFabricService {
	@Autowired
    private ManufacturingOrderFabricRepository repository;

    public List<ManufacturingOrderFabric> getAll() {
		return repository.findAll();
	}

	public ManufacturingOrderFabric save(ManufacturingOrderFabric manufacturingOrderFabric) {
		return repository.save(manufacturingOrderFabric);
	}

    public ManufacturingOrderFabric createManufacturingOrderFabric(ManufacturingOrderFabric fabric) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createManufacturingOrderFabric'");
    }

    public ManufacturingOrderFabric updateManufacturingOrderFabric(Long id, ManufacturingOrderFabric fabric) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateManufacturingOrderFabric'");
    }

    public void createManufacturingOrderFabric(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createManufacturingOrderFabric'");
    }

    public ManufacturingOrderFabric create(ManufacturingOrderFabric fabric) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    public ManufacturingOrderFabric update(Long id, ManufacturingOrderFabric fabric) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public void delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }  
}
