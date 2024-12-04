package ru.clevertec.service;

import ru.clevertec.entity.Car;
import ru.clevertec.entity.Client;
import ru.clevertec.repository.impl.CarRepository;
import ru.clevertec.repository.impl.ClientRepository;

import java.util.List;

public class ClientService {

    private final ClientRepository clientRepository = new ClientRepository();
    private final CarRepository carRepository = new CarRepository();

    public void addClient(Client client) {
        clientRepository.save(client);
    }

    public void updateClient(Client client) {
        clientRepository.update(client);
    }

    public void deleteClient(Client client) {
        clientRepository.delete(client);
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public void buyCar(Long clientId, Long carId) {
        Client client = clientRepository.findById(clientId);
        Car car = carRepository.findById(carId);

        if (client == null || car == null) {
            throw new IllegalArgumentException("Client or Car not found");
        }

        client.getCars().add(car);
        clientRepository.update(client);
    }
}


