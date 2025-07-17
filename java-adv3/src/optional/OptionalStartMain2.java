package optional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OptionalStartMain2 {

    private static final Map<Long, String> map = new HashMap<>();

    static {
        map.put(1L, "Kim");
        map.put(2L, "Seo");
    }

    public static void main(String[] args) {
        findAndPrint(1L);
        findAndPrint(3L);
    }

    static void findAndPrint(Long id) {
        Optional<String> optName = findNameById(id);
        String nmae = optName.orElse("UNKNOWN"); // null 이라면 인자 안의 값을 리턴하라는 의미의 orElse()
        System.out.println(id + ": " + nmae.toUpperCase());
    }

    static Optional<String> findNameById(Long id) {
        String findName = map.get(id);
        Optional<String> optName = Optional.ofNullable(findName);
        return optName;
    }
}
