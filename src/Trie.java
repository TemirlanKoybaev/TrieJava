import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie {

    // Узел префиксного дерева
    private static class Node {
        Map<Character, Node> children = new HashMap<>(); // переходы по символам
        boolean isWordEnd = false;                       // пометка конца слова
    }

    private final Node root;

    public Trie() {
        root = new Node();
    }

    // Добавление слова в дерево
    public void addWord(String word) {
        if (word == null || word.isEmpty()) return;

        Node cur = root;

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            // Переходим в следующего ребенка, если его нет, создаем
            Node next = cur.children.get(c);
            if (next == null) {
                next = new Node();
                cur.children.put(c, next);
            }

            cur = next;
        }

        // Отмечаем конец слова
        cur.isWordEnd = true;
    }

    // Проверка существования слова
    public boolean containsWord(String word) {
        if (word == null || word.isEmpty()) return false;

        Node node = findNode(word);
        return node != null && node.isWordEnd;
    }

    // Проверка существования префикса
    public boolean hasPrefix(String prefix) {
        if (prefix == null || prefix.isEmpty()) return false;

        return findNode(prefix) != null;
    }

    // Получение всех слов по заданному префиксу
    public List<String> getWordsByPrefix(String prefix) {
        List<String> result = new ArrayList<>();
        if (prefix == null) return result;

        Node node = findNode(prefix);
        if (node == null) return result;

        StringBuilder sb = new StringBuilder(prefix);
        dfs(node, sb, result);

        return result;
    }

    // Поиск узла, соответствующего строке
    private Node findNode(String str) {
        Node cur = root;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            Node next = cur.children.get(c);

            // Если пути нет, то нужного узла нет
            if (next == null) return null;

            cur = next;
        }

        return cur;
    }

    // Глубинный обход для получения всех слов
    private void dfs(Node node, StringBuilder sb, List<String> out) {

        // Если дошли до конца слова, добавляем его
        if (node.isWordEnd) {
            out.add(sb.toString());
        }

        // Рекурсивно обходим всех детей
        for (Map.Entry<Character, Node> entry : node.children.entrySet()) {
            sb.append(entry.getKey());
            dfs(entry.getValue(), sb, out);
            sb.deleteCharAt(sb.length() - 1); // откат символа после рекурсии
        }
    }
}
