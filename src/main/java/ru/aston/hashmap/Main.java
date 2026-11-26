package ru.aston.hashmap;

public class Main {
    public static void main(String[] args) {
        MyHashMap<String, Integer> map = new MyHashMap<>();

        System.out.println("=== 1. Adding elements (put) ===");
        map.put("apple", 10);
        map.put("banana", 20);
        map.put("cherry", 30);
        System.out.println("Added: apple=10, banana=20, cherry=30");

        System.out.println("\n=== 2. Retrieving elements (get) ===");
        System.out.println("get(\"apple\")  = " + map.get("apple"));
        System.out.println("get(\"banana\") = " + map.get("banana"));
        System.out.println("get(\"kiwi\")   = " + map.get("kiwi") + " (key not found, expected null)");

        System.out.println("\n=== 3. Updating an existing key (put) ===");
        Integer oldValue = map.put("apple", 100);
        System.out.println("put(\"apple\", 100) returned previous value: " + oldValue);
        System.out.println("Now get(\"apple\") = " + map.get("apple"));

        System.out.println("\n=== 4. Removing elements (remove) ===");
        Integer removed = map.remove("banana");
        System.out.println("remove(\"banana\") returned: " + removed);
        System.out.println("get(\"banana\") after removal: " + map.get("banana") + " (expected null)");

        Integer removedMissing = map.remove("kiwi");
        System.out.println("remove(\"kiwi\") for a missing key: " + removedMissing + " (expected null)");

        System.out.println("\n=== 5. Collision demonstration ===");
        MyKey key1 = new MyKey("First", 1);
        MyKey key2 = new MyKey("Second", 1); // Same hashCode (1), but different equals

        MyHashMap<MyKey, String> collisionMap = new MyHashMap<>();
        collisionMap.put(key1, "Value 1");
        collisionMap.put(key2, "Value 2");

        System.out.println("Added two different keys with the same hashCode.");
        System.out.println("get(key1) = " + collisionMap.get(key1));
        System.out.println("get(key2) = " + collisionMap.get(key2));

        collisionMap.remove(key1);
        System.out.println("After remove(key1):");
        System.out.println("get(key1) = " + collisionMap.get(key1) + " (expected null)");
        System.out.println("get(key2) = " + collisionMap.get(key2) + " (chain is not broken)");
    }
}
