package edu.emory.cs.trie.autocomplete;

import edu.emory.cs.trie.TrieNode;

import java.util.*;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class AutocompleteHWExtra extends Autocomplete<List<String>> {
    public AutocompleteHWExtra(String dict_file, int max) {
        super(dict_file, max);
    }
    public String toString(TrieNode<List<String>> node) {
        if (node.getParent() != null) return toString(node.getParent()) + node.getKey();
        else return "";

    }
    public List<String> getAllWords(TrieNode<List<String>> node) {
        List<String> result = new ArrayList<String>();
        if (node.isEndState()) result.add(toString(node));
        if (node.hasChildren()) {
            for (int i = 0; i < 26; i++) {
                if (node.getChild((char) ('a' + i)) != null) result.addAll(getAllWords(node.getChild((char) ('a' + i))));
            }
        }
        return result;
    }

    @Override
    public List<String> getCandidates(String prefix) {
        prefix = prefix.replace(" ", "");
        if (this.find(prefix) == null) return null;
        List<String> unrefinedResult = getAllWords(this.find(prefix));
        unrefinedResult.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(o1.length(), o2.length());
            }
        });

        if (this.find(prefix) != null && this.find(prefix).getValue() == null) {
            List<String> result = new ArrayList<String>();
            int i = 0;
            while (i < getMax() && i < unrefinedResult.size()) result.add(unrefinedResult.get(i++));
            return result;
        }
        else {
            TrieNode<List<String>> lastNode = this.find(prefix);
            Map<String, Integer> map = new LinkedHashMap<>();
            for (int i = 0; i < lastNode.getValue().size(); i++) {
                map.put(lastNode.getValue().get(i), 1 + map.getOrDefault(lastNode.getValue().get(i), 0)); }
            List<Entry<String, Integer>> frequencyList = new ArrayList<Entry<String,Integer>>(map.entrySet());
            Collections.sort(frequencyList, new Comparator<Entry<String, Integer>>() {
                @Override
                public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                    return - Integer.compare(o1.getValue(), o2.getValue());
                }
            });

            List<String> result = new ArrayList<String>();
            int i = 0;
            while (i < getMax() && i < frequencyList.size()) {
                if(unrefinedResult.contains(frequencyList.get(i).getKey()))
                    unrefinedResult.remove(frequencyList.get(i).getKey());
                result.add(frequencyList.get(i++).getKey());
            }
            i = 0;
            while (result.size() < getMax() && i < unrefinedResult.size()) result.add(unrefinedResult.get(i++));
            return result;

        }
    }

    @Override
    public void pickCandidate(String prefix, String candidate) {
        prefix = prefix.replace(" ", "");
        candidate = candidate.replace(" ", "");
        if (this.find(candidate) == null) this.put(candidate, null);
        else this.find(candidate).setEndState(true);
        TrieNode<List<String>> lastNode = this.find(prefix);
        if (lastNode != null && lastNode.getValue() == null) {
            List<String> value = new ArrayList<String>();
            value.add(candidate);
            lastNode.setValue(value);;
        }
        else if (lastNode != null) this.find(prefix).getValue().add(0, candidate);

        else {
            List<String> value = new ArrayList<String>();
            value.add(candidate);
            this.put(prefix, value);
            this.find(prefix).setEndState(false);
        }
    }

}