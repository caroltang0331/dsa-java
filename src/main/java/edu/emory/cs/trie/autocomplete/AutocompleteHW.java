package edu.emory.cs.trie.autocomplete;
import edu.emory.cs.trie.TrieNode;

import javax.print.CancelablePrintJob;
import java.util.*;
import java.util.Map;
import java.util.Map.Entry;

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
    public List<String> getMaxWords(int max, TrieNode<List<String>> node) {
        List<String> result = new ArrayList<>();
        int count = 0;
        if (node.isEndState()) result.add(toString(node));

        List<TrieNode<List<String>>> moreThanMaxNodes = new LinkedList<>();
        if (node.hasChildren()) {
            List<Entry<Character, TrieNode<List<String>>>> alphabetLists = new ArrayList<Entry<Character, TrieNode<List<String>>>>();
            alphabetLists.addAll(node.getChildrenMap().entrySet());
            Collections.sort(alphabetLists, (o1, o2) -> Character.compare(o1.getKey(), o2.getKey()));
            for (Entry<Character, TrieNode<List<String>>> alphabetList : alphabetLists) {
                moreThanMaxNodes.add(alphabetList.getValue());
            } //list of children(level1) to be search that are alphbaticaly sorted
        }
        while (moreThanMaxNodes.size() > 0 && count < max) {
            TrieNode currentNode = moreThanMaxNodes.remove(0);
            if (currentNode.isEndState()) {
                result.add(toString(currentNode));
                count++;
            }
            if (currentNode.hasChildren()) {
                List<Entry<Character, TrieNode<List<String>>>> ChildrenLists = new ArrayList<Entry<Character, TrieNode<List<String>>>>();
                ChildrenLists.addAll(currentNode.getChildrenMap().entrySet());
                Collections.sort(ChildrenLists, (o1, o2) -> Character.compare(o1.getKey(), o2.getKey()));
                for (Entry<Character, TrieNode<List<String>>> ChildrenList : ChildrenLists)
                    moreThanMaxNodes.add(ChildrenList.getValue());
            }//first child L2
        }
        return result;
    }

    @Override
    public List<String> getCandidates(String prefix) {
        prefix = prefix.replace(" ", "");
        if (this.find(prefix) == null) return null;
        List<String> unrefinedResult = getMaxWords(getMax(), this.find(prefix));

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
            this.find(prefix).setEndState(false);
        }
    }
}