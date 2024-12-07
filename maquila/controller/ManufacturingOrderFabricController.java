package ws.maquila.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import ws.maquila.model.ManufacturingOrderFabric;
import ws.maquila.service.ManufacturingOrderFabricService;

import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("fabricacionOrdenTelas")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@Tag(name = "Detalles de telas de la orden de fabricación", description = "Proporciona métodos para gestionar los detalles de las órdenes de fabricación de telas")
public class ManufacturingOrderFabricController {

    @Autowired
    private ManufacturingOrderFabricService service;

    @Operation(summary = "Obtener todas las telas de la orden de fabricación")
    @ApiResponse(responseCode = "200", description = "Se encontraron telas para la orden de fabricación", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ManufacturingOrderFabric.class)))
    })
    @GetMapping
    public List<ManufacturingOrderFabric> obtenerTodo() {
        return service.getAll();
    }

    @Operation(summary = "Crear una nueva tela para la orden de fabricación")
    @ApiResponse(responseCode = "201", description = "Tela creada exitosamente", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ManufacturingOrderFabric.class))
    })
    @PostMapping
    public ManufacturingOrderFabric crear(@RequestBody ManufacturingOrderFabric fabric) {
        return service.create(fabric);
    }

    @Operation(summary = "Actualizar una tela existente para la orden de fabricación")
    @ApiResponse(responseCode = "200", description = "Tela actualizada exitosamente", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ManufacturingOrderFabric.class))
    })
    @PutMapping("/{id}")
    public ManufacturingOrderFabric actualizar(@PathVariable Long id, @RequestBody ManufacturingOrderFabric fabric) {
        return service.update(id, fabric);
    }

    @Operation(summary = "Eliminar una tela de la orden de fabricación")
    @ApiResponse(responseCode = "204", description = "Tela eliminada exitosamente")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.delete(id);
    }
}