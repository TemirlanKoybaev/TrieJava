import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie {

    private static class Uzel {
        Map<Character, Uzel> deti = new HashMap<>();
        boolean konecSlova = false;
    }

    private final Uzel koren;

    public Trie() {
        koren = new Uzel();
    }

    public void insert(String word) {
        if (word == null || word.isEmpty()) return;
        Uzel tek = koren;
        for (int i = 0; i < word.length(); i++) {
            char buk = word.charAt(i);
            Uzel sled = tek.deti.get(buk);
            if (sled == null) {
                sled = new Uzel();
                tek.deti.put(buk, sled);
            }
            tek = sled;
        }
        tek.konecSlova = true;
    }

    public boolean contains(String word) {
        if (word == null || word.isEmpty()) return false;
        Uzel uz = naytiUzel(word);
        return uz != null && uz.konecSlova;
    }

    public boolean startsWith(String prefix) {
        if (prefix == null || prefix.isEmpty()) return false;
        Uzel uz = naytiUzel(prefix);
        return uz != null;
    }

    public List<String> getByPrefix(String prefix) {
        List<String> sp = new ArrayList<>();
        if (prefix == null) return sp;
        Uzel uz = naytiUzel(prefix);
        if (uz == null) return sp;
        StringBuilder sb = new StringBuilder(prefix);
        dfs(uz, sb, sp);
        return sp;
    }

    private Uzel naytiUzel(String stroka) {
        Uzel tek = koren;
        for (int i = 0; i < stroka.length(); i++) {
            char buk = stroka.charAt(i);
            Uzel sled = tek.deti.get(buk);
            if (sled == null) return null;
            tek = sled;
        }
        return tek;
    }

    private void dfs(Uzel uz, StringBuilder sb, List<String> sp) {
        if (uz.konecSlova) {
            sp.add(sb.toString());
        }
        for (Map.Entry<Character, Uzel> v : uz.deti.entrySet()) {
            sb.append(v.getKey());
            dfs(v.getValue(), sb, sp);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}