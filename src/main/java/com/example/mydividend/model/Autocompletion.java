package com.example.mydividend.model;

import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;

import java.util.List;
import java.util.stream.Collectors;

public class Autocompletion {
    private Trie<String, String> trie = new PatriciaTrie<>();
    private static final int LIMIT_COUNT = 10;

    public void addKeyword(String keyword) {
        trie.put(keyword, null);
    }

    public List<String> autocomplete(String keyword) {
        return trie.prefixMap(keyword).keySet()
                .stream()
                .limit(LIMIT_COUNT)
                .collect(Collectors.toList());
    }

    public void deleteKeyword(String keyword) {
        trie.remove(keyword);
    }
}
