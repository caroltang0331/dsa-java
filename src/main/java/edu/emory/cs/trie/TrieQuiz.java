package edu.emory.cs.trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrieQuiz extends Trie<Integer> {
    /**
     * PRE: this trie contains all country names as keys and their unique IDs as values
     * (e.g., this.get("United States") -> 0, this.get("South Korea") -> 1).
     * @param input the input string in plain text
     *              (e.g., "I was born in South Korea and raised in the United States").
     * @return the list of entities (e.g., [Entity(14, 25, 1), Entity(44, 57, 0)]).
     */
    List<Entity> getEntities(String input) {
        List<Entity> result = new ArrayList<Entity>();
        String[] splitSpace = input.split(" ");
        int[] indexCount = new int[splitSpace.length];
        indexCount[0] = 0;
        for (int i = 1; i < splitSpace.length; i++) {
            indexCount[i] = splitSpace[i - 1].length() + indexCount[i - 1] + 1;
        }

        for (int i = 0; i < splitSpace.length; i++) {
            if (this.find(splitSpace[i]) != null) {
                int beginIndex = indexCount[i];
                if (this.contains(splitSpace[i])) result.add(new Entity(indexCount[i], indexCount[i + 1] - 1, this.get(splitSpace[i])));
                String temp = splitSpace[i];
                int j = i;
                while (j + 1 < splitSpace.length && this.find(temp + " " + splitSpace[j + 1]) != null) {
                    temp = temp + " " + splitSpace[j + 1];
                    if (this.contains(temp))
                        result.add(new Entity(beginIndex, beginIndex + temp.length(), this.get(temp)));
                    j++;
                }
            }
        }

        return result;
    }
}

