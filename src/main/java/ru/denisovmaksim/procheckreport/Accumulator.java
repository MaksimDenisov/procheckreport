package ru.denisovmaksim.procheckreport;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.denisovmaksim.procheckreport.model.Check;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Accumulator {
    private static final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static List<Check> getReport(String jsonPath) throws IOException {
        List<Check> list = mapper.readValue(Files.readString(Paths.get(jsonPath).normalize()), new TypeReference<>() {
        });
        Map<String, List<Check>> map = list.stream()
                .collect(Collectors.groupingBy(Check::getName));
        List<Check> result = new ArrayList<>();
        for (Map.Entry<String, List<Check>> entry : map.entrySet()) {
            int quantity = entry.getValue().stream().mapToInt(Check::getQuantity).sum();
            int sum = entry.getValue().stream().mapToInt(Check::getSum).sum();
            result.add(new Check(entry.getKey(), quantity, sum));
        }
        return result.stream()
                .sorted(Comparator.comparing(Check::getName))
                .collect(Collectors.toList());
    }
}
