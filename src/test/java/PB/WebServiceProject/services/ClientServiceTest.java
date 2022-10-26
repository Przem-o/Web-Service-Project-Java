package PB.WebServiceProject.services;

import PB.WebServiceProject.entities.AddressEntity;
import PB.WebServiceProject.entities.ClientEntity;
import PB.WebServiceProject.repository.AddressRepository;
import PB.WebServiceProject.repository.ClientRepository;
import PB.WebServiceProject.rest.dto.AddressDTO;
import PB.WebServiceProject.rest.dto.ClientDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {
    @Mock
    private ClientRepository clientRepositoryMock;
    @Mock
    private AddressRepository addressRepositoryMock;
    @InjectMocks
    private ClientService clientService;

    // testujemy tylko servisy !
    @Before
    public void init() {

        Mockito.when(clientRepositoryMock.findAll()).thenReturn(preparedClientList());
//        Mockito.when(addressRepositoryMock.findAll()).thenReturn(prepareClientList());

//        Mockito.when(clientRepositoryMock.findById(2L)).thenReturn(Optional.of(clientEntity1));
//        można tutaj storzyc nowe obiekty zamiast metody prepareClientList i zastosować powyższe zamiast tworzyc nowy obiekt *.build w tescie should_return_Client_By_Id_From_Repository()
    }

    @Test
    public void shouldReturnClientByIdFromRepository() {
        //given - > w tej czesci jest actual
        Mockito.when(clientRepositoryMock.findById(2L)).thenReturn(Optional.of(ClientEntity.builder()
                .id(2L)
                .name("Tom")
                .addressEntity(AddressEntity.builder().build())
                .build()));
        // when
        ClientDTO clientDTO = clientService.getClientById(2L);
        Assertions.assertNotNull(clientDTO);
        //Assertions.assertTrue(clientDTO.getId() == 2L); to samo co poniżej
        Assertions.assertEquals(2L, clientDTO.getId());
        Assertions.assertEquals("Tom", clientDTO.getName());
    }

    @Test
    public void shouldReturnListOfClientsConvertedToDTOByParameters() {
        List<ClientDTO> clientsDTOList = clientService.getClients(null, null, null);
        //List<ClientDTO> clientsDTOList = clientService.getClients(2L, "Mati", null);
        Assertions.assertNotNull(clientsDTOList);
        Assertions.assertFalse(clientsDTOList.isEmpty()); // Jeśli wartość jest obecna to true, wykonuje daną akcję z wartością, w przeciwnym razie nic nie robi
        Assertions.assertEquals(3, clientsDTOList.size());
//        Assertions.assertEquals("Mati", clientsDTOList.get(0).getName());
    }

    @Test
    public void shouldAddClientToRepository() {
        //// sekcja given
        // AddressEntity addressEntity = new AddressEntity();
        ClientEntity clientEntity = ClientEntity.builder() // w tej czesci jest to co w bazie danych czyli actual
                .id(10L)
                .name("John")
                .addressEntity(AddressEntity.builder().build())
                .build();

        Mockito.when(clientRepositoryMock.save(Mockito.any())).thenReturn(clientEntity);
        // symulacja dodania w/w klienta do bazy danych
        // czyli w momencie dadania jakiegoś klienta do repozytorium metoda Mockito.save -> (clientRepository.save(Mockito.any))
        // powinno mi zwrócić konkretnego klienta -> thenReturn(clientEntity)
        // (clientEntity) to mój  10L, "John" którego najpierw na starcie utworzyłem w tym tescie

        //   Mockito.when(addressRepositoryMock.save(Mockito.any())).thenReturn(addressEntity);

        //// sekcja when
        // czyli wywołanie zamokowanej metody aaClient

        ClientDTO clientDTO = ClientDTO.builder()
                .id(10L)
                .name("John")
                .addressDTO(AddressDTO.builder().build())
                .build();
        ClientDTO clientDTO1 = clientService.addClient(clientDTO);

        ////sekcja then czyli dokonanie sprawdzenia
        Assertions.assertNotNull(clientDTO1);
        Assertions.assertEquals("John", clientDTO1.getName()); // spodziewam się "John" i mam w repo też mam "John"
        Assertions.assertEquals(10L, clientDTO1.getId());
    }

    @Test
    public void shouldEditClient() {
        // given actual
        //   AddressEntity addressEntity = new AddressEntity();
        ClientEntity clientEntity = ClientEntity.builder()
                .id(1L)
                .name("Pati")
                .addressEntity(AddressEntity.builder().build())
                .build();
        Mockito.when(clientRepositoryMock.save(Mockito.any())).thenReturn(clientEntity); // symulacja dodania w/w klienta do bazy danych
        //    Mockito.when(addressRepositoryMock.save(Mockito.any())).thenReturn(addressEntity);
        Mockito.when(clientRepositoryMock.findById(1L)).thenReturn(Optional.of(clientEntity));

        ClientDTO clientDTO = ClientDTO.builder()
                .id(1L)
                .name("Pati")
                .addressDTO(AddressDTO.builder().build())
                .build();
        ClientDTO clientDTO1 = clientService.editClient(1L, clientDTO);
        Assertions.assertNotNull(clientDTO1);
        Assertions.assertEquals("Pati", clientDTO1.getName());
        Assertions.assertEquals(1L, clientDTO1.getId());

    }

    private List<ClientEntity> preparedClientList() {
        List<ClientEntity> clientsEntityList = new ArrayList<>();
        clientsEntityList.add(ClientEntity.builder()
                .id(1L)
                .name("Jaro")
                .addressEntity(AddressEntity.builder().build())
                .build());
        clientsEntityList.add(ClientEntity.builder()
                .id(2L)
                .name("Mati")
                .addressEntity(AddressEntity.builder().build())
                .build());
        clientsEntityList.add(ClientEntity.builder()
                .id(3L)
                .name("Seba")
                .addressEntity(AddressEntity.builder().build())
                .build());
        return clientsEntityList;
    }
}


