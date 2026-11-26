import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.aston.hashmap.MyHashMap;

import static org.junit.jupiter.api.Assertions.*;

class MyHashMapTest {

    private MyHashMap<String, Integer> map;

    @BeforeEach
    void setUp() {
        map = new MyHashMap<>();
    }

    // --- Тесты для PUT ---
    @Test
    void testPutAndGet() {
        map.put("apple", 10);
        assertEquals(10, map.get("apple"));
    }

    @Test
    void testPutOverwritesValue() {
        map.put("apple", 10);
        Integer oldValue = map.put("apple", 20);

        assertEquals(10, oldValue, "Должно вернуться старое значение");
        assertEquals(20, map.get("apple"), "Значение должно обновиться");
    }

    // --- Тесты для GET ---
    @Test
    void testGetMissingKey() {
        assertNull(map.get("kiwi"), "Для отсутствующего ключа должен вернуться null");
    }

    @Test
    void testGetNullKey() {
        map.put(null, 100);
        assertEquals(100, map.get(null), "Ключ null должен корректно обрабатываться");
    }

    // --- Тесты для REMOVE ---
    @Test
    void testRemove() {
        map.put("apple", 10);
        Integer removed = map.remove("apple");

        assertEquals(10, removed);
        assertNull(map.get("apple"), "После удаления get должен вернуть null");
    }

    @Test
    void testRemoveMissingKey() {
        assertNull(map.remove("kiwi"), "Удаление несуществующего ключа возвращает null");
    }

    // --- Тесты для КОЛЛИЗИЙ ---
    @Test
    void testCollision() {
        // Внутренний класс с фиксированным hashCode для имитации коллизии
        class CollisionKey {
            final String name;
            CollisionKey(String name) { this.name = name; }

            @Override
            public int hashCode() { return 1; } // Форсируем коллизию

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                CollisionKey that = (CollisionKey) o;
                return name.equals(that.name);
            }
        }

        MyHashMap<CollisionKey, String> collisionMap = new MyHashMap<>();
        CollisionKey key1 = new CollisionKey("key1");
        CollisionKey key2 = new CollisionKey("key2");
        CollisionKey key3 = new CollisionKey("key3");

        collisionMap.put(key1, "Value1");
        collisionMap.put(key2, "Value2");
        collisionMap.put(key3, "Value3"); // Добавление в конец цепочки

        assertEquals("Value1", collisionMap.get(key1));
        assertEquals("Value2", collisionMap.get(key2));
        assertEquals("Value3", collisionMap.get(key3));

        // Удаляем из середины цепочки (key2)
        collisionMap.remove(key2);
        assertNull(collisionMap.get(key2), "Удаленный ключ должен исчезнуть");
        assertEquals("Value1", collisionMap.get(key1), "Первый элемент цепочки не должен сломаться");
        assertEquals("Value3", collisionMap.get(key3), "Последний элемент цепочки не должен сломаться");
    }
}