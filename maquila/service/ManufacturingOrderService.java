package ws.maquila.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ws.maquila.repository.ManufacturingOrderRepository;
import ws.maquila.model.ManufacturingOrder;

@Service
public class ManufacturingOrderService {
	@Autowired
    private ManufacturingOrderRepository repository;

    public List<ManufacturingOrder> getAll() {
		return repository.findAll();
	}

	@Transactional
	public ManufacturingOrder save(ManufacturingOrder manufacturingOrder) {
		return repository.save(manufacturingOrder);
	}

	public ManufacturingOrder getById(Integer idManufacturingOrder) {
		return repository.findById(idManufacturingOrder).get();
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

    public ManufacturingOrder update(Long id, ManufacturingOrder convertToEntity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public ManufacturingOrder findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }    
}
