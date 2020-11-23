import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Problems {
    static List<String> words = Arrays.asList("hi", "hello", "this", "is", "me", "que", "abba", "test");

    void problem1(){
        /*P1. Loop down the words and print each on a separate line, with two spaces in front of each word.
        Don’t use map.*/
        System.out.println("Problem 1: ");
        words.forEach(element -> System.out.println("  " + element));
    }

    void problem2(){
        /*P2. Repeat the previous problem, but without the two spaces in front. This is trivial if you use the
         same approach as in P1, so the point is to use a method reference here, as opposed to an explicit
         lambda as in P1.*/
        System.out.println("Problem 2: ");
        words.forEach(System.out::println);
    }

    void problem3(){
        /*
        We assume that we have a method StringUtils.transformedList(List<String>, Function1<String>)
    where interface Function1<T> { T app(T);}
    and we produced transformed lists like this:
        • List<String> excitingWords = StringUtils.transformedList(words, s -> s + "!");
        • List<String> eyeWords = StringUtils.transformedList(words, s -> s.replace("i", "eye"));
        • List<String> upperCaseWords = StringUtils.transformedList(words, String::toUpperCase);
        Produce the same lists as above, but this time use streams and the builtin “map” method.
         */
        System.out.println("Problem3");
        List<String> excitingWords = words.stream().map(s -> s + '!').collect(Collectors.toList());
        System.out.println("excitingWords " + excitingWords);
        List<String> eyeWords = words.stream().map(s -> s.replace("i", "eye")).collect(Collectors.toList());
        System.out.println("eyeWords: " + eyeWords);
        List<String> uppercaseWords = words.stream().map(String::toUpperCase).collect(Collectors.toList());
        System.out.println("uppercaseWords: " + uppercaseWords);
    }


    void problem4(){
        /*
            We assume that we have the method StringUtils.allMatches(List<String>, Predicate1<String>) where interface Predicate1<T> { boolean check(T);}
            and we produced filtered lists like this:
            • List<String> shortWords = StringUtils.allMatches(words, s -> s.length() < 4);
            • List<String> wordsWithB = StringUtils.allMatches(words, s -> s.contains("b"));
            • List<String> evenLengthWords = StringUtils.allMatches(words, s -> (s.length() % 2) == 0);
            Produce the same lists as above, but this time use “filter”.
         */
        System.out.println("Problem4");
        List<String> shortWords = words.stream().filter(s -> s.length() < 4).collect(Collectors.toList());
        List<String> wordsWithB = words.stream().filter(s -> s.contains("b")).collect(Collectors.toList());
        List<String> evenLengthWords = words.stream().filter(s -> s.length() % 2 == 0).collect(Collectors.toList());
        System.out.println("shortWords: " +shortWords);
        System.out.println("wordsWithB " + wordsWithB);
        System.out.println("evenLengthWords " + evenLengthWords);
    }

    void problem5(){
        /*
        Turn the strings into uppercase, keep only the ones that are shorter than 4 characters,
        of what is remaining, keep only the ones that contain “E”, and print the first result.
         Repeat the process, except checking for a “Q” instead of an “E”. When checking for the “Q”,
         try to avoid repeating all the code from when you checked for an “E”.
         */
        System.out.println("Problem5");
        words.stream()
                .filter(s -> s.length() < 4)
                .map(String::toUpperCase)
                .filter(s -> s.contains("E"))
                .limit(1)
                .forEach(System.out::println);
    }

    void problem6(){
        /*
        P6. Produce a single String that is the result of concatenating the uppercase versions of all of the Strings.
         Use a single reduce operation, without using map.
         */
        System.out.println("Problem6: ");
        System.out.println(words.stream()
                .reduce("", (acc, s) -> acc = acc + s.toUpperCase() + ' '));
    }

    void problem7(){
        /*
        P7. Produce the same String as above, but this time via a map operation that turns the words into uppercase,
         followed by a reduce operation that concatenates them.
         */
        System.out.println("Problem7: ");
        String rez = words.stream()
                .map(String::toUpperCase)
                .reduce("", (acc, s) -> acc += s + ' ');
        System.out.println(rez);
    }

    void problem8(){
        /*
        P8. Produce a String that is all the words concatenated together, but with commas in between.
         E.g., the result should be "hi,hello,...". Note that there is no comma at the beginning, before “hi”,
          and also no comma at the end, after the last word. Major hint: there are two versions of reduce discussed
          in the notes.
         */
        System.out.println("Problem8: ");
        String s = words.stream().reduce((acc, item) -> acc + "," + item).get();
        System.out.println(s);
    }

    void problem9(){
        /*
        P9. Find the total number of characters (i.e., sum of the lengths) of the strings in the List.
         */
        System.out.println("Problem9: ");
        int nr = words.stream().reduce("", (acc, s) -> acc = acc + s).length();
        System.out.println(nr);
    }

    void problem10(){
        /*
        P10. Find the number of words that contain an “h”.
         */
        System.out.println("Problem 10: ");
        int nr = (int) words.stream().filter(s -> s.contains("h")).count();
        System.out.println(nr);
    }
}
