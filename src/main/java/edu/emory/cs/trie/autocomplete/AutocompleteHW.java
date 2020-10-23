package edu.emory.cs.trie.autocomplete;
import edu.emory.cs.trie.TrieNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class AutocompleteHW extends Autocomplete<List<String>> {
    public AutocompleteHW(String dict_file, int max) {
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
            List<String> result = new ArrayList<String>();
            int i = 0;
            while (i < getMax() && i < lastNode.getValue().size()) {
                if(unrefinedResult.contains(lastNode.getValue().get(i)))
                    unrefinedResult.remove(lastNode.getValue().get(i));
                result.add(lastNode.getValue().get(i++));
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
        else if (lastNode != null) {
            if (lastNode.getValue().contains(candidate)) lastNode.getValue().remove(candidate);
            this.find(prefix).getValue().add(0, candidate);
        }
        else {
            List<String> value = new ArrayList<String>();
            value.add(candidate);
            this.put(prefix, value);
        }
    }
}