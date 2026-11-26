package ru.aston.hashmap;

public class MyHashMap<K, V> {

    private static class Node<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private final Node<K, V>[] table;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        this.table = new Node[16];
    }

    private static int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    private int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    // 1. Считаем хэш.
    // 2. Определяем бакет.
    // 3. Если пустой - добавляем.
    // 4. Если нет - идем по списку: сравниваем hash, затем equals.
    // 5. Совпало - перезаписываем value. Не совпало - добавляем в конец списка.
    public V put(K key, V value) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);

        Node<K, V> head = table[index];

        if (head == null) {
            table[index] = new Node<>(hash, key, value, null);
            size++;
            return null;
        }

        Node<K, V> current = head;
        Node<K, V> prev = null;

        while (current != null) {
            if (current.hash == hash && (current.key == key || (key != null && key.equals(current.key)))) {
                V oldValue = current.value;
                current.value = value;
                return oldValue;
            }
            prev = current;
            current = current.next;
        }

        prev.next = new Node<>(hash, key, value, null);
        size++;
        return null;
    }

    public V get(Object key) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);

        Node<K, V> current = table[index];
        while (current != null) {
            if (current.hash == hash && (current.key == key || (key != null && key.equals(current.key)))) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public V remove(Object key) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);

        Node<K, V> current = table[index];
        Node<K, V> prev = null;

        while (current != null) {
            if (current.hash == hash && (current.key == key || (key != null && key.equals(current.key)))) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }
}
