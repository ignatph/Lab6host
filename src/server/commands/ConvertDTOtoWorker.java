package server.commands;

import server.body.Address;
import server.body.Coordinates;
import server.body.Organization;
import server.body.Worker;
import server.collection.CollectionWorker;
import server.network.WorkerDTO;
import server.utillity.IDGenerator;

import java.time.LocalDate;

public class ConvertDTOtoWorker {
    public WorkerDTO dto;
    public ConvertDTOtoWorker(WorkerDTO dto) {
        this.dto=dto;
    }
    public static  Worker toWorker(WorkerDTO dto) {
        Worker worker = new Worker();

        // Автоматическая генерация системных полей
        worker.setId(IDGenerator.generateUniqueId());
        worker.setCreationDate(LocalDate.now());

        // Установка основных полей
        worker.setName(dto.getName());
        worker.setSalary(dto.getSalary());
        worker.setEndDate(dto.getEndDate());
        worker.setPosition(dto.getPosition());
        worker.setStatus(dto.getStatus());

        // Установка координат
        Coordinates coordinates = new Coordinates();
        coordinates.setX(dto.getCoordinates().getX());
        coordinates.setY(dto.getCoordinates().getY());
        worker.setCoordinates(coordinates);

        // Установка организации
        Organization organization = new Organization();
        organization.setName(dto.getOrganization().getFullName());
        organization.setType(dto.getOrganization().getType());

        // Установка адреса организации
        Address address = new Address();
        address.setStreet(dto.getOrganization().getOfficialAddress().getStreet());
        address.setZipCode(dto.getOrganization().getOfficialAddress().getZipCode());
        organization.setAddress(address);

        worker.setOrganization(organization);

        return worker;
    }
}