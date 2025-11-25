import java.util.ArrayList;
import java.util.List;

public class Trie {

    // Узел дерева
    private static class Node {
        Node[] children = new Node[26]; // ссылки на следующие буквы
        boolean isEnd;                  // флаг конца слова
    }

    private final Node root;

    public Trie() {
        root = new Node(); // изначально пустой корень
    }

    // переводим букву в индекс массива 0..25
    private int toIndex(char c) {
        return c - 'a';
    }

    // вставка нового слова
    public void insert(String word) {
        if (word == null || word.isEmpty()) return;

        word = word.toLowerCase();
        Node cur = root;

        for (int i = 0; i < word.length(); i++) {
            int id = toIndex(word.charAt(i));

            // пропускаем символы вне диапазона a-z
            if (id < 0 || id >= 26) continue;

            // создаём новый узел, если его не было
            if (cur.children[id] == null) {
                cur.children[id] = new Node();
            }
            cur = cur.children[id];
        }

        // помечаем, что слово заканчивается здесь
        cur.isEnd = true;
    }

    // проверка полного совпадения слова
    public boolean contains(String word) {
        if (word == null || word.isEmpty()) return false;

        word = word.toLowerCase();
        Node cur = root;

        for (int i = 0; i < word.length(); i++) {
            int id = toIndex(word.charAt(i));
            if (id < 0 || id >= 26) return false;
            if (cur.children[id] == null) return false;
            cur = cur.children[id];
        }

        return cur.isEnd; // важно, иначе это только префикс
    }

    // проверяет, существует ли хоть одно слово с таким префиксом
    public boolean startsWith(String prefix) {
        if (prefix == null || prefix.isEmpty()) return false;

        prefix = prefix.toLowerCase();
        Node cur = root;

        for (int i = 0; i < prefix.length(); i++) {
            int id = toIndex(prefix.charAt(i));
            if (id < 0 || id >= 26) return false;
            if (cur.children[id] == null) return false;
            cur = cur.children[id];
        }

        return true;
    }

    // возвращает список всех слов, начинающихся с prefix
    public List<String> getByPrefix(String prefix) {
        List<String> result = new ArrayList<>();
        if (prefix == null || prefix.isEmpty()) return result;

        prefix = prefix.toLowerCase();
        Node cur = root;

        // доходим до узла, соответствующего последней букве префикса
        for (int i = 0; i < prefix.length(); i++) {
            int id = toIndex(prefix.charAt(i));
            if (id < 0 || id >= 26) return result;
            if (cur.children[id] == null) return result;
            cur = cur.children[id];
        }

        // собираем слова снизу
        StringBuilder sb = new StringBuilder(prefix);
        collectWords(cur, sb, result);

        return result;
    }

    // DFS по поддереву, собираем строки
    private void collectWords(Node node, StringBuilder sb, List<String> list) {
        // если здесь конец слова — добавляем
        if (node.isEnd) {
            list.add(sb.toString());
        }

        // обходим всех детей
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                sb.append((char) ('a' + i));                // добавляем букву
                collectWords(node.children[i], sb, list);   // углубляемся
                sb.deleteCharAt(sb.length() - 1);           // откат назад
            }
        }
    }
}
