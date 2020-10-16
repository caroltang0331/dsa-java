package edu.emory.cs.trie;


import org.junit.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;


/** @author Jinho D. Choi */
public class TrieQuizTest {
    @Test
    public void testGetEntities() {
        final List<String> L = List.of("United","United States", "South Korea", "States of America","England", "Costaa");
        TrieQuiz trie = new TrieQuiz();
        for (int i = 0; i < L.size(); i++)
            trie.put(L.get(i), i);

        String input = "Costa born in South Korea and France in the United States of America ho";
        List<Entity> entities = List.of(new Entity(44,50,0), new Entity(44, 57, 1), new Entity(51, 68, 3),new Entity(14, 25, 2));
        Set<String> expected = entities.stream().map(Entity::toString).collect(Collectors.toSet());
        Set<String> actual = trie.getEntities(input).stream().map(Entity::toString).collect(Collectors.toSet());
        assertEquals(expected, actual);
    }
}