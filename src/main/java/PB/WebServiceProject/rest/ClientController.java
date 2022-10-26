package PB.WebServiceProject.rest;

import PB.WebServiceProject.rest.dto.ClientDTO;
import PB.WebServiceProject.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @Operation(description = "Add new client")
    @PostMapping("/client")
    public ClientDTO addClient(@Parameter(description = "add new client")
                               @Valid @RequestBody ClientDTO clientDTO) {
        return clientService.addClient(clientDTO);
    }

    @Operation(description = "Delete client")
    @DeleteMapping("/client/{id}")
    public ResponseEntity deleteClient(@Parameter(description = "delete client by id")
                                       @PathVariable(name = "id") Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Edit client")
    @PutMapping("/client/{id}")
    public ClientDTO editClient(@Parameter(description = "edit client by id")
                                @PathVariable(name = "id") Long id,
                                @Valid @RequestBody ClientDTO clientDTO) {
        return clientService.editClient(id, clientDTO);
    }

    @Operation(description = "Get clients")
    @GetMapping("/clients")
    public List<ClientDTO> getClients(@Parameter(description = "get client by id")
                                      @RequestParam(name = "id", required = false) Long id,
                                      @Parameter(description = "get client by name")
                                      @RequestParam(name = "name", required = false) String name,
                                      @Parameter(description = "get client by address type city")
                                      @RequestParam(name = "address", required = false) String address) {
        return clientService.getClients(id, name, address);

    }
}
