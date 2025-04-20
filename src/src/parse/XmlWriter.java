package client.main.server.src.parse;
import server.collection.CollectionWorker;
import server.interfaces.BaseWriter;
import utillity.Printer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

import java.io.File;

/**
 * Class for Writing Collections to XML files
 */

public class XmlWriter implements BaseWriter {
    private final Printer printer;
    private final CollectionWorker collection;

    public XmlWriter(Printer printer, CollectionWorker collection) {
        this.printer = printer;
        this.collection = collection;
    }
@Override
    public void write(String path) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // Корневой элемент
            Element rootElement = doc.createElement("Workers");
            doc.appendChild(rootElement);

            for (Worker worker : collection.getCollection()) {
                Element workerElement = doc.createElement("Worker");
                rootElement.appendChild(workerElement);

                // id
                createElementWithText(doc, workerElement, "id", String.valueOf(worker.getId()));
                // name
                createElementWithText(doc, workerElement, "name", worker.getName());
                // coordinates
                Element coordElement = doc.createElement("coordinates");
                workerElement.appendChild(coordElement);
                createElementWithText(doc, coordElement, "x", String.valueOf(worker.getCoordinates().getX()));
                createElementWithText(doc, coordElement, "y", String.valueOf(worker.getCoordinates().getY()));
                // creationDate
                createElementWithText(doc, workerElement, "creationDate", worker.getCreationDate().toString());
                // salary
                createElementWithText(doc, workerElement, "salary", String.valueOf(worker.getSalary()));
                // endDate (опционально)
                createElementWithText(doc, workerElement, "endDate", worker.getEndDate() != null ? worker.getEndDate().toString() : "");
                // position (опционально)
                createElementWithText(doc, workerElement, "position", worker.getPosition() != null ? worker.getPosition().toString() : "");
                // status
                createElementWithText(doc, workerElement, "status", worker.getStatus().toString());
                // organization
                Element orgElement = doc.createElement("organization");
                workerElement.appendChild(orgElement);
                createElementWithText(doc, orgElement, "fullName", worker.getOrganization().getFullName() != null ? worker.getOrganization().getFullName() : "");
                createElementWithText(doc, orgElement, "type", worker.getOrganization().getType() != null ? worker.getOrganization().getType().toString() : "");
                // officialAddress (опционально)
                if (worker.getOrganization().getOfficialAddress() != null) {
                    Element addrElement = doc.createElement("officialAddress");
                    orgElement.appendChild(addrElement);
                    createElementWithText(doc, addrElement, "street", worker.getOrganization().getOfficialAddress().getStreet());
                    createElementWithText(doc, addrElement, "zipCode", worker.getOrganization().getOfficialAddress().getZipCode());
                }
            }

            // Запись XML в файл
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);

            printer.print("Коллекция успешно сохранена в XML файле.");
        } catch (Exception e) {
            printer.print("Ошибка при записи в XML файл: " + e.getMessage());
        }
    }

    // Вспомогательный метод для создания элемента с текстовым содержимым
    private void createElementWithText(Document doc, Element parent, String tagName, String text) {
        Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(text));
        parent.appendChild(element);
    }
}
