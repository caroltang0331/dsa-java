package edu.emory.cs.trie.autocomplete;

import org.junit.Test;

import java.util.List;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class AutocompleteTest {
    class Eval {
        int correct = 0;
        int total = 0;
    }

    @Test
    public void test() {
        final String dict_file = "src/main/resources/dict.txt";
        final int max = 5;//20

        Autocomplete<?> ac = new AutocompleteHWExtra(dict_file, max);
        Eval eval = new Eval();
        testAutocomplete(ac, eval);
    }

    private void testAutocomplete(Autocomplete<?> ac, Eval eval) {
        String prefix;
        List<String> expected;

        prefix = "a";
        expected = List.of("five including a");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "ab";
        expected = List.of("two including ab");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "a";
        expected = List.of("abeeee", "hoho", "aaf", "atyuib", "only four");
        ac.pickCandidate(prefix, "aaf");
        ac.pickCandidate(prefix, "abeeee");
        ac.pickCandidate(prefix, "ahhhhhh");
        ac.pickCandidate(prefix, "zlovecs");
        ac.pickCandidate(prefix, "ab");
        ac.pickCandidate(prefix, "hoho");
        ac.pickCandidate(prefix, "abeeee");
        ac.pickCandidate(prefix, "hoho");
        ac.pickCandidate(prefix, "abeeee");
        ac.pickCandidate(prefix, "aaf");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "abdominal";
        expected = List.of("wah", "b ba", "bb", "two more should bot hv b ba");
        ac.pickCandidate(prefix, "bba");
        testGetCandidates(ac, eval, prefix, expected);

        System.out.printf("Score: %d/%d\n", eval.correct, eval.total);
    }

    private void testGetCandidates(Autocomplete<?> ac, Eval eval, String prefix, List<String> expected) {
        String log = String.format("%2d: ", eval.total);
        eval.total++;

        try {
            List<String> candidates = ac.getCandidates(prefix);

            if (expected.equals(candidates)) {
                eval.correct++;
                log += "PASS";
            }
            else
                log += String.format("FAIL -> expected = %s, returned = %s", expected, candidates);
        }
        catch (Exception e) {
            log += "ERROR";
        }

        System.out.println(log);
    }
}