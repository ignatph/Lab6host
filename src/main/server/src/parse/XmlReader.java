package parse;

import collection.CollectionWorker;
import interfaces.BaseReader;
import utillity.Printer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.File;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class XmlReader implements BaseReader {
    private final Printer printer;
    private final CollectionWorker collection;

    public XmlReader(Printer printer, CollectionWorker collection) {
        this.printer = printer;
        this.collection = collection;
    }

    @Override
    public Worker[] read(String path) {
        try {
            File xmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList workerList = doc.getElementsByTagName("Worker");
            Set<Long> uniqueIds = new HashSet<>(); // Для проверки уникальности ID

            for (int i = 0; i < workerList.getLength(); i++) {
                Node node = workerList.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE) continue;

                Element workerElement = (Element) node;
                Worker worker = new Worker();


                long id;
                try {
                    id = Long.parseLong(getTagValue("id", workerElement));
                    if (uniqueIds.contains(id)) {
                        printer.print("Ошибка: Дубликат ID " + id + ". Элемент не добавлен.");
                        continue;
                    }
                    uniqueIds.add(id);
                } catch (NumberFormatException e) {
                    printer.print("Ошибка: Некорректный формат ID в элементе " + i);
                    continue;
                }

                worker.setId(id);


                worker.setName(getTagValue("name", workerElement));


                Element coordElement = (Element) workerElement.getElementsByTagName("coordinates").item(0);
                Coordinates coordinates = new Coordinates();
                coordinates.setX(Float.parseFloat(getTagValue("x", coordElement)));
                coordinates.setY(Float.parseFloat(getTagValue("y", coordElement)));
                worker.setCoordinates(coordinates);

                worker.setCreationDate(LocalDate.parse(getTagValue("creationDate", workerElement)));
                worker.setSalary(Integer.parseInt(getTagValue("salary", workerElement)));

                String endDateStr = getTagValue("endDate", workerElement);
                worker.setEndDate(endDateStr.isEmpty() ? null : LocalDate.parse(endDateStr));


                String positionStr = getTagValue("position", workerElement);
                worker.setPosition(positionStr.isEmpty() ? null : Position.valueOf(positionStr));

                worker.setStatus(Status.valueOf(getTagValue("status", workerElement)));


                Element orgElement = (Element) workerElement.getElementsByTagName("organization").item(0);
                Organization organization = new Organization();
                String fullName = getTagValue("fullName", orgElement);
                organization.setName(fullName.isEmpty() ? null : fullName);

                String typeStr = getTagValue("type", orgElement);
                organization.setType(typeStr.isEmpty() ? null : OrganizationType.valueOf(typeStr));


                NodeList addrList = orgElement.getElementsByTagName("officialAddress");
                if (addrList.getLength() > 0) {
                    Element addrElement = (Element) addrList.item(0);
                    Address address = new Address();
                    address.setStreet(getTagValue("street", addrElement));
                    address.setZipCode(getTagValue("zipCode", addrElement));
                    organization.setAddress(address);
                }

                worker.setOrganization(organization);
                collection.addWorker(worker);
            }
        } catch (Exception e) {
            printer.print("Ошибка при чтении XML: " + e.getMessage());
        }
        return collection.getCollection().toArray(new Worker[0]);
    }

    private String getTagValue(String tag, Element element) {
        NodeList nlList = element.getElementsByTagName(tag);
        if (nlList.getLength() == 0) return "";
        Node node = nlList.item(0);
        return node.getTextContent().trim();
    }
}