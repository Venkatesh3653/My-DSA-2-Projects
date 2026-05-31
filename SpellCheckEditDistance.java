import java.util.*;

public class SpellCheckEditDistance {

    static int[][] buildDP(String a, String b) {

        int m = a.length();
        int n = b.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++)
            dp[i][0] = i;

        for (int j = 0; j <= n; j++)
            dp[0][j] = j;

        for (int i = 1; i <= m; i++) {

            for (int j = 1; j <= n; j++) {

                if (a.charAt(i - 1) == b.charAt(j - 1)) {

                    dp[i][j] = dp[i - 1][j - 1];

                } else {

                    dp[i][j] = 1 + Math.min(
                            dp[i - 1][j],
                            Math.min(
                                    dp[i][j - 1],
                                    dp[i - 1][j - 1]
                            )
                    );
                }
            }
        }

        return dp;
    }

    static void printDPTable(String a, String b, int[][] dp) {

        System.out.println("Edit Distance DP Table:");

        System.out.printf("%5s", "Ø");

        for (int j = 0; j < b.length(); j++) {
            System.out.printf("%5c", b.charAt(j));
        }

        System.out.println();

        for (int i = 0; i <= a.length(); i++) {

            if (i == 0)
                System.out.printf("%5s", "Ø");
            else
                System.out.printf("%5c", a.charAt(i - 1));

            for (int j = 0; j <= b.length(); j++) {
                System.out.printf("%5d", dp[i][j]);
            }

            System.out.println();
        }
    }

    static void backtrace(String a, String b, int[][] dp) {

        int i = a.length();
        int j = b.length();

        List<String> operations = new ArrayList<>();

        while (i > 0 || j > 0) {

            if (i > 0 && j > 0 &&
                    a.charAt(i - 1) == b.charAt(j - 1)) {

                i--;
                j--;

            } else if (i > 0 && j > 0 &&
                    dp[i][j] == dp[i - 1][j - 1] + 1) {

                operations.add(
                        "Substitute "
                                + a.charAt(i - 1)
                                + " -> "
                                + b.charAt(j - 1)
                );

                i--;
                j--;

            } else if (i > 0 &&
                    dp[i][j] == dp[i - 1][j] + 1) {

                operations.add(
                        "Delete "
                                + a.charAt(i - 1)
                );

                i--;

            } else {

                operations.add(
                        "Insert "
                                + b.charAt(j - 1)
                );

                j--;
            }
        }

        Collections.reverse(operations);

        System.out.println("\nEdit Operations:");

        for (String op : operations) {
            System.out.println(op);
        }
    }

    static int editDistance(String a, String b) {

        int[][] dp = buildDP(a, b);

        printDPTable(a, b, dp);

        System.out.println("\ndp[" +
                a.length() +
                "][" +
                b.length() +
                "] = " +
                dp[a.length()][b.length()]);

        backtrace(a, b, dp);

        return dp[a.length()][b.length()];
    }

    static int editDistance1D(String a, String b) {

        int m = a.length();
        int n = b.length();

        int[] prev = new int[n + 1];
        int[] curr = new int[n + 1];

        for (int j = 0; j <= n; j++)
            prev[j] = j;

        for (int i = 1; i <= m; i++) {

            curr[0] = i;

            for (int j = 1; j <= n; j++) {

                if (a.charAt(i - 1) == b.charAt(j - 1)) {

                    curr[j] = prev[j - 1];

                } else {

                    curr[j] = 1 + Math.min(
                            prev[j],
                            Math.min(
                                    curr[j - 1],
                                    prev[j - 1]
                            )
                    );
                }
            }

            int[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[n];
    }

    static String closestWord(String input,
                              List<String> dictionary) {

        String bestWord = "";
        int minDistance = Integer.MAX_VALUE;

        for (String word : dictionary) {

            int distance =
                    editDistance1D(input, word);

            if (distance < minDistance) {

                minDistance = distance;
                bestWord = word;
            }
        }

        System.out.println(
                "\nClosest Dictionary Word: "
                        + bestWord
        );

        System.out.println(
                "Minimum Edit Distance: "
                        + minDistance
        );

        return bestWord;
    }

    public static void main(String[] args) {

        String word1 = "kitten";
        String word2 = "sitting";

        int distance =
                editDistance(word1, word2);

        System.out.println(
                "\nFinal Edit Distance = "
                        + distance
        );

        System.out.println(
                "\nTime Complexity: O(m*n)"
        );

        System.out.println(
                "Space Complexity: O(m*n)"
        );

        System.out.println(
                "Optimized Space Complexity: O(n)"
        );

        List<String> dictionary =
                Arrays.asList(
                        "sitting",
                        "written",
                        "bitten",
                        "kitten",
                        "fitting",
                        "smitten"
                );

        closestWord("kitten", dictionary);

        System.out.println(
                "\nSpell-Check Optimizations:"
        );

        System.out.println(
                "1. BK-Tree uses triangle inequality to prune dictionary search."
        );

        System.out.println(
                "2. Levenshtein Automaton efficiently finds words within a fixed edit distance."
        );

        System.out.println(
                "\nAdvanced Variants:"
        );

        System.out.println(
                "1. Damerau-Levenshtein Distance supports transposition."
        );

        System.out.println(
                "2. Weighted Edit Distance assigns different costs to edits."
        );
    }
}