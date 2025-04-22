package server.commands;

import server.body.Worker;
import server.collection.CollectionWorker;
import server.network.CommandStatusResponse;
import server.network.WorkerDTO;
import server.utillity.Printer;

import java.util.Comparator;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AddIfMin extends Command {
    private final CollectionWorker collectionWorker;
    private static final Map<String, Comparator<Worker>> comparators = new HashMap<>();
    private CommandStatusResponse response;

    // Все внутренние классы компараторов остаются без изменений
    private static class IdComparator implements Comparator<Worker> {
        @Override
        public int compare(Worker w1, Worker w2) {
            return Long.compare(w1.getId(), w2.getId());
        }
    }

    private static class NameComparator implements Comparator<Worker> {
        @Override
        public int compare(Worker w1, Worker w2) {
            return String.CASE_INSENSITIVE_ORDER.compare(
                    w1.getName(),
                    w2.getName()
            );
        }
    }

    private static class SalaryComparator implements Comparator<Worker> {
        @Override
        public int compare(Worker w1, Worker w2) {
            return Integer.compare(w1.getSalary(), w2.getSalary());
        }
    }

    private static class CoordinatesComparator implements Comparator<Worker> {
        @Override
        public int compare(Worker w1, Worker w2) {
            int xCompare = Float.compare(
                    w1.getCoordinates().getX(),
                    w2.getCoordinates().getX()
            );
            return xCompare != 0 ? xCompare :
                    Float.compare(
                            w1.getCoordinates().getY(),
                            w2.getCoordinates().getY()
                    );
        }
    }

    private static class CreationDateComparator implements Comparator<Worker> {
        @Override
        public int compare(Worker w1, Worker w2) {
            return w1.getCreationDate().compareTo(w2.getCreationDate());
        }
    }

    private static class EndDateComparator implements Comparator<Worker> {
        @Override
        public int compare(Worker w1, Worker w2) {
            if (w1.getEndDate() == null) return -1;
            if (w2.getEndDate() == null) return 1;
            return w1.getEndDate().compareTo(w2.getEndDate());
        }
    }

    private static class PositionComparator implements Comparator<Worker> {
        @Override
        public int compare(Worker w1, Worker w2) {
            if (w1.getPosition() == null) return -1;
            if (w2.getPosition() == null) return 1;
            return Integer.compare(
                    w1.getPosition().ordinal(),
                    w2.getPosition().ordinal()
            );
        }
    }

    private static class StatusComparator implements Comparator<Worker> {
        @Override
        public int compare(Worker w1, Worker w2) {
            return w1.getStatus().compareTo(w2.getStatus());
        }
    }

    private static class OrganizationComparator implements Comparator<Worker> {
        @Override
        public int compare(Worker w1, Worker w2) {
            return String.CASE_INSENSITIVE_ORDER.compare(
                    w1.getOrganization().getFullName(),
                    w2.getOrganization().getFullName()
            );
        }
    }

    static {
        comparators.put("id", new IdComparator());
        comparators.put("name", new NameComparator());
        comparators.put("salary", new SalaryComparator());
        comparators.put("coordinates", new CoordinatesComparator());
        comparators.put("creationdate", new CreationDateComparator());
        comparators.put("enddate", new EndDateComparator());
        comparators.put("position", new PositionComparator());
        comparators.put("status", new StatusComparator());
        comparators.put("organization", new OrganizationComparator());
    }

    public AddIfMin(String description, boolean hasArgs, CollectionWorker collectionWorker) {
        super(description, hasArgs, collectionWorker);
        this.collectionWorker = collectionWorker;
    }

    @Override
    public void execute(Printer printer, Object data) {
        try {
            if (!checkArgument(printer, getArgs())) {
                response = CommandStatusResponse.ofString(
                        "Укажите корректный параметр сравнения! Доступные параметры:\n" +
                                String.join(", ", comparators.keySet()),
                        false
                );
                return;
            }

            String field = ((String) getArgs()).toLowerCase();
            Worker newWorker = createWorker(data);
            Comparator<Worker> comparator = comparators.get(field);

            // Использование Stream API для поиска минимального элемента
            Optional<Worker> minWorker = collectionWorker.getCollection()
                    .stream()
                    .min(comparator);

            if (minWorker.isEmpty()) {
                collectionWorker.addWorker(newWorker);
                response = CommandStatusResponse.ofString("Элемент добавлен (коллекция была пуста)", true);
                return;
            }

            if (comparator.compare(newWorker, minWorker.get()) < 0) {
                collectionWorker.addWorker(newWorker);
                response = CommandStatusResponse.ofString(
                        "Элемент добавлен. Новое минимальное значение в поле '" + field + "'",
                        true
                );
            } else {
                response = CommandStatusResponse.ofString(
                        "Элемент не добавлен. Текущий минимум в поле '" + field + "': " + getFieldValue(minWorker.get(), field),
                        false
                );
            }
        } catch (ClassCastException e) {
            response = CommandStatusResponse.ofString("Неверный формат данных для команды add_if_min", false);
        } catch (Exception e) {
            response = CommandStatusResponse.ofString("Ошибка выполнения команды: " + e.getMessage(), false);
        }
    }

    @Override
    public CommandStatusResponse getResponse() {
        return response;
    }

    // Остальные методы без изменений
    private Worker createWorker(Object data) {
        WorkerDTO dto = (WorkerDTO) data;
        return ConvertDTOtoWorker.toWorker(dto);
    }

    private Object getFieldValue(Worker worker, String field) {
        switch (field.toLowerCase()) {
            case "id": return worker.getId();
            case "name": return worker.getName();
            case "salary": return worker.getSalary();
            case "coordinates": return worker.getCoordinates();
            case "creationdate": return worker.getCreationDate();
            case "enddate": return worker.getEndDate();
            case "position": return worker.getPosition();
            case "status": return worker.getStatus();
            case "organization": return worker.getOrganization();
            default: return "N/A";
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs instanceof String && comparators.containsKey(((String) inputArgs).toLowerCase())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "add_if_min {field} : добавить новый элемент если он минимальный по указанному полю\n" +
                "Доступные поля:\n" +
                "  - id\n" +
                "  - name\n" +
                "  - salary\n" +
                "  - coordinates\n" +
                "  - creationDate\n" +
                "  - endDate\n" +
                "  - position\n" +
                "  - status\n" +
                "  - organization";
    }
}