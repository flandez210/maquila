package ws.maquila.controller;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import ws.maquila.dto.ManufacturingOrderFabricDTO;
import ws.maquila.dto.ManufacturingOrderRequestDTO;
import ws.maquila.model.Fabric;
import ws.maquila.model.ManufacturingOrder;
import ws.maquila.model.ManufacturingOrderFabric;
import ws.maquila.service.FabricService;
import ws.maquila.service.ManufacturingOrderService;

import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("manufacturingOrders")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })
@Tag(name = "Manufacturing orders", description = "Provides methods for managing manufacturing orders")
public class ManufacturingOrderController {
	@Autowired
	private ManufacturingOrderService service;

	@Autowired
	private FabricService fabricService;

	@Autowired
	private ModelMapper modelMapper;

	@Operation(summary = "Get all manufacturing orders")
	@ApiResponse(responseCode = "200", description = "Found manufacturing order", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ManufacturingOrder.class))) })
	@GetMapping
	public List<ManufacturingOrder> getAll() {
		return service.getAll();
	}

	@Operation(summary = "Register a manufacturing order")
	@ApiResponse(responseCode = "201", description = "Registered manufacturing order", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ManufacturingOrder.class)) })
	@PostMapping
	public ResponseEntity<ManufacturingOrder> add(
			@RequestBody ManufacturingOrderRequestDTO manufacturingOrderRequestDTO) {
		ManufacturingOrder savedManufacturingOrder = service.save(convertToEntity(manufacturingOrderRequestDTO));
		return new ResponseEntity<>(savedManufacturingOrder, HttpStatus.CREATED);
	}

	@Operation(summary = "Update a manufacturing order")
	@ApiResponse(responseCode = "200", description = "Updated manufacturing order", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ManufacturingOrder.class)) })
	@PutMapping("/{id}")
	public ResponseEntity<ManufacturingOrder> update(@PathVariable Long id, @RequestBody ManufacturingOrderRequestDTO manufacturingOrderRequestDTO) {
		ManufacturingOrder existingOrder = service.findById(id);
		if (existingOrder != null) {
			ManufacturingOrder updatedOrder = service.update(id, convertToEntity(manufacturingOrderRequestDTO));
			return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Delete a manufacturing order")
	@ApiResponse(responseCode = "204", description = "Deleted manufacturing order successfully")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		ManufacturingOrder order = service.findById(id);
		if (order != null) {
			service.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Add a fabric to an existing manufacturing order")
	@ApiResponse(responseCode = "200", description = "Added fabric to manufacturing order", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ManufacturingOrder.class)) })
	@PostMapping("/{id}/fabrics")
	public ResponseEntity<ManufacturingOrder> addFabric(@PathVariable Long id, @RequestBody ManufacturingOrderFabricDTO fabricDTO) {
		ManufacturingOrder order = service.findById(id);
		if (order != null) {
			Fabric fabric = fabricService.getById(fabricDTO.getFabricId());
			ManufacturingOrderFabric fabricEntity = convertToDTO(fabricDTO);
			fabricEntity.setIdFabric(fabric);
			fabricEntity.setManufacturingOrder(order);
			order.getManufacturingOrderFabrics().add(fabricEntity);
			service.save(order);
			return new ResponseEntity<>(order, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	public ManufacturingOrder convertToEntity(ManufacturingOrderRequestDTO manufacturingOrderRequestDTO) {
		List<ManufacturingOrderFabricDTO> manufacturingOrderFabricsDTO = manufacturingOrderRequestDTO
				.getManufacturingOrderFabricsDTO();
		List<ManufacturingOrderFabric> manufacturingOrderFabrics = manufacturingOrderFabricsDTO.stream()
				.map(this::convertToDTO)
				.collect(Collectors.toList());

		ManufacturingOrder manufacturingOrder = modelMapper.map(manufacturingOrderRequestDTO, ManufacturingOrder.class);
		manufacturingOrder.setManufacturingOrderFabrics(manufacturingOrderFabrics);

		Iterator<ManufacturingOrderFabricDTO> manufacturingOrderFabricDTOIterator = manufacturingOrderFabricsDTO.iterator();
		Iterator<ManufacturingOrderFabric> manufacturingOrderFabricIterator = manufacturingOrderFabrics.iterator();
		while (manufacturingOrderFabricDTOIterator.hasNext()) {
			ManufacturingOrderFabricDTO manufacturingOrderFabricDTO = manufacturingOrderFabricDTOIterator.next();
			ManufacturingOrderFabric manufacturingOrderFabric = manufacturingOrderFabricIterator.next();
			Fabric managedFabric = fabricService.getById(manufacturingOrderFabricDTO.getFabricId());
			manufacturingOrderFabric.setIdFabric(managedFabric);
			manufacturingOrderFabric.setManufacturingOrder(manufacturingOrder);
		}		
		return manufacturingOrder;
	}

	private ManufacturingOrderFabric convertToDTO(ManufacturingOrderFabricDTO manufacturingOrderFabricDTO) {
		return modelMapper.map(manufacturingOrderFabricDTO, ManufacturingOrderFabric.class);
	}
}