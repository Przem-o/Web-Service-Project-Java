package PB.WebServiceProject.services;

import PB.WebServiceProject.entities.AddressEntity;
import PB.WebServiceProject.entities.ClientEntity;
import PB.WebServiceProject.repository.ClientRepository;
import PB.WebServiceProject.rest.dto.ClientDTO;
import PB.WebServiceProject.util.EntityDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientDTO addClient(ClientDTO clientDTO) {
        ClientEntity clientEntity = EntityDtoMapper.mapClientToEntity(clientDTO);
        AddressEntity addressEntity = EntityDtoMapper.mapAddressToEntity(clientDTO.getAddressDTO());
        clientEntity.setAddressEntity(addressEntity);
        addressEntity.setClientEntity(clientEntity);
        ClientEntity saveClient = clientRepository.save(clientEntity);

        return EntityDtoMapper.mapClientToDto(saveClient);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    public ClientDTO editClient(Long id, ClientDTO clientDTO) {
        Optional<ClientEntity> findClientById = clientRepository.findById(id);
        if (findClientById.isPresent()) {
            ClientEntity clientEntity = findClientById.get();
            clientEntity.setId(clientDTO.getId());
            clientEntity.setName(clientDTO.getName());
            AddressEntity addressEntity = clientEntity.getAddressEntity();
            if (addressEntity != null) {
                addressEntity.setCity(clientDTO.getAddressDTO().getCity());
                addressEntity.setCountry(clientDTO.getAddressDTO().getCountry());
            }
            ClientEntity saveClient = clientRepository.save(clientEntity);
            return EntityDtoMapper.mapClientToDto(saveClient);
        } else {
            ClientEntity clientEntity1 = EntityDtoMapper.mapClientToEntity(clientDTO);
            clientEntity1.setAddressEntity(EntityDtoMapper.mapAddressToEntity(clientDTO.getAddressDTO()));
            ClientDTO clientDTO1 = EntityDtoMapper.mapClientToDto(clientEntity1);
            return clientDTO1;
        }
    }

    public ClientDTO getClientById(Long id) {
        Optional<ClientEntity> clientEntityOptional = clientRepository.findById(id);
        ClientDTO clientDTO = clientEntityOptional.map(EntityDtoMapper::mapClientToDto).orElse(null);
        return clientDTO;

    }

    public List<ClientDTO> getClients(Long id, String name, String address) {
        return clientRepository.findAll().stream()
                .filter(clientEntity -> id == null || clientEntity.getId().equals(id))
                .filter(clientEntity -> name == null || clientEntity.getName().equalsIgnoreCase(name))
                .filter(clientEntity -> address == null || clientEntity.getAddressEntity().getCity().equalsIgnoreCase(address))
                .map(EntityDtoMapper::mapClientToDto)
                .collect(Collectors.toList());
    }
}


