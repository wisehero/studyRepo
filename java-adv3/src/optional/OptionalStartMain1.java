package optional;

import java.util.HashMap;
import java.util.Map;

public class OptionalStartMain1 {

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
        String name = findNameById(id);

        // 여기서는 Optional을 사용하지 않고 if로 null 체크
        if (name != null) {
            System.out.println(id + ": " + name.toUpperCase()); // 만약 null 검사를 하지 않을때 name <- 이 null 이면 여기서 NPE가 발생한다.
        } else {
            System.out.println(id + ": " + "UNKNOWN");
        }
    }

    static String findNameById(Long id) {
        return map.get(id);
    }
}
