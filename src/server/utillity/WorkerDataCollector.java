// WorkerDataCollector.java
package server.utillity;


import server.body.*;
import client.manager.WorkerDTO;

public class WorkerDataCollector {

    public static void fillWorkerFromDTO(Worker worker, WorkerDTO dto) {
        // Установка обязательных полей
        worker.setName(dto.getName());
        worker.setCoordinates(convertCoordinates(dto.getCoordinates()));
        worker.setSalary(dto.getSalary());
        worker.setStatus(dto.getStatus());
        worker.setOrganization(convertOrganization(dto.getOrganization()));

        // Установка необязательных полей
        if (dto.getEndDate() != null) {
            worker.setEndDate(dto.getEndDate());
        }
        if (dto.getPosition() != null) {
            worker.setPosition(dto.getPosition());
        }
    }

    private static Coordinates convertCoordinates(CoordinatesDTO dto) {
        Coordinates coord = new Coordinates();
        coord.setX(dto.getX());
        coord.setY(dto.getY());
        return coord;
    }

    private static Organization convertOrganization(OrganizationDTO dto) {
        Organization org = new Organization();
        org.setFullName(dto.getFullName());
        org.setType(dto.getType());
        org.setOfficialAddress(convertAddress(dto.getOfficialAddress()));
        return org;
    }

    private static Address convertAddress(AddressDTO dto) {
        Address addr = new Address();
        addr.setStreet(dto.getStreet());
        addr.setZipCode(dto.getZipCode());
        return addr;
    }
}