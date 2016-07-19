package org.sufficientlysecure.htmltextview;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by secretrobotron on 7/10/16.
 * Class for converting :-) emoji text or github style :smile: into emojis
 */
public class EmojiUtils {
    public static final Map<String, Integer> emojiMap;
    private static final String EMOTICON_DELIMITER = "";
    // Normal text smiles pattern :-) etc
    public static final Pattern SMILEY_REGEX_PATTERN = Pattern.compile(":[)DdpP]|:[ -]\\)|<3");
    // github style pattern :happy: etc
    public static final Pattern GITHUB_REGEX_PATTERN = Pattern.compile(":[^:]+:");
    // frowny pattern :( etc
    public static final Pattern FROWNY_REGEX_PATTERN = Pattern.compile(":[(<]|:[ -]\\(");
//    public static final Pattern EMOTICON_REGEX_PATTERN =
//            Pattern.compile("(?<=^|" + EMOTICON_DELIMITER + ")("
//                    + SMILEY_REGEX_PATTERN.pattern() + "|" + FROWNY_REGEX_PATTERN.pattern() + "|" + GITHUB_REGEX_PATTERN.pattern()
//                    + ")+(?=$|" + EMOTICON_DELIMITER + ")");
    public static final Pattern EMOTICON_REGEX_PATTERN = Pattern.compile("(:[^:]+:)");

    //TODO: add missing icons/smilies
    static {
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        map.put(":sparkles:", 0x2728);
        map.put(":party-popper:", 0x1F389);
        map.put(":balloon:", 0x1F388);
        map.put(":question:", 0x2753);
        map.put(":dancer:", 0x1F483);
        map.put(":calendar:", 0x1F4C5);
        map.put(":happy:", 0x1F601);
        map.put(":happy2:", 0x1F602);
        map.put(":happy3:", 0x1F603);
        map.put(":happy4:", 0x1F604);
        map.put(":happy5:", 0x1F605);
        map.put(":happy6:", 0x1F606);
        map.put(":wink:", 0x1F609);
        map.put(":$", 0x1F60A);
        map.put(":delicious:", 0x1F60B);
        map.put(":relief:", 0x1F60C);
        map.put(":hearts:", 0x1F60D);
        map.put(":sunglasses:", 0x1F60E);
        map.put(":smirk:", 0x1F60F);
        map.put(":unamused:", 0x1F612);
        map.put("(:|", 0x1F613);
        map.put(":pensive:", 0x1F614);
        map.put(":confounded:", 0x1F616);
        map.put(":kiss:", 0x1F618);
        map.put(":kiss2:", 0x1F61A);
        map.put(":tongue:", 0x1F61C);
        map.put(":tongue2:", 0x1F61D);
        map.put(":disappointed:", 0x1F61E);
        map.put(":angry:", 0x1F620);
        map.put(":pouting:", 0x1F621);
        map.put(":crying:", 0x1F622);
        map.put(":persevering:", 0x1F623);
        map.put(":triumph:", 0x1F624);
        map.put(":relief2:", 0x1F625);
        map.put(":fearful:", 0x1F628);
        map.put(":weary:", 0x1F629);
        map.put(":sleepy:", 0x1F62A);
        map.put(":tired:", 0x1F62B);
        map.put(":crying2:", 0x1F62D);
        map.put(":screaming:", 0x1F631);
        map.put(":astonished:", 0x1F632);
        map.put(":flushed:", 0x1F633);
        map.put(":dizzy:", 0x1F635);
        map.put(":mask:", 0x1F637);
        map.put(":grinning-cat:", 0x1F638);
        map.put(":joy-cat:", 0x1F639);
        map.put(":smiling-cat:", 0x1F63A);
        map.put(":smiling-heart-cat:", 0x1F63B);
        map.put(":wry-cat:", 0x1F63C);
        map.put(":kissing-cat:", 0x1F63D);
        map.put(":pouting-cat:", 0x1F63E);
        map.put(":crying-cat:", 0x1F63F);
        map.put(":weary-cat:", 0x1F640);
        map.put(":no-good:", 0x1F645);
        map.put(":ok:", 0x1F646);
        map.put(":bowing:", 0x1F647);
        map.put(":see-no-evil:", 0x1F648);
        map.put(":hear-no-evil:", 0x1F649);
        map.put(":speak-no-evil:", 0x1F64A);
        map.put(":hand-person:", 0x1F64B);
        map.put(":both-hands-person:", 0x1F64C);
        map.put(":frowning-person:", 0x1F64D);
        map.put(":pouting-person:", 0x1F64E);
        map.put(":folded-hands-person:", 0x1F64F);
        map.put(":alien:", 0x1F47E);
        map.put(":alien-monster:", 0x1F47E);
        map.put(":horse-face:", 0x1F434);
        map.put(":horse:", 0x1F40E);
        map.put(":monkey-face:", 0x1F435);
        map.put(":monkey:", 0x1F412);
        map.put(":dog-face:", 0x1F436);
        map.put(":dog:", 0x1F415);
        map.put(":poodle:", 0x1F429);
        map.put(":wolf-face:", 0x1F43A);
        map.put(":cat-face:", 0x1F431);
        map.put(":cat:", 0x1F408);
        map.put(":tiger-face:", 0x1F42F);
        map.put(":tiger:", 0x1F405);
        map.put(":leopard:", 0x1F406);
        map.put(":horse-face:", 0x1F434);
        map.put(":horse:", 0x1F40E);
        map.put(":cow-face:", 0x1F42E);
        map.put(":ox:", 0x1F402);
        map.put(":water-buffalo:", 0x1F403);
        map.put(":cow:", 0x1F404);
        map.put(":pig-face:", 0x1F437);
        map.put(":pig:", 0x1F416);
        map.put(":boar:", 0x1F417);
        map.put(":pig-nose:", 0x1F43D);
        map.put(":ram:", 0x1F40F);
        map.put(":sheep:", 0x1F411);
        map.put(":goat:", 0x1F410);
        map.put(":dromedary-camel:", 0x1F42A);
        map.put(":bactrian-camel:", 0x1F42B);
        map.put(":elephant:", 0x1F418);
        map.put(":mouse-face:", 0x1F42D);
        map.put(":mouse:", 0x1F401);
        map.put(":rat:", 0x1F400);
        map.put(":hamster-face:", 0x1F439);
        map.put(":rabbit-face:", 0x1F430);
        map.put(":rabbit:", 0x1F407);
        map.put(":bear-face:", 0x1F43B);
        map.put(":koala:", 0x1F428);
        map.put(":panda-face:", 0x1F43C);
        map.put(":paw-prints:", 0x1F43E);
        map.put(":chicken:", 0x1F414);
        map.put(":rooster:", 0x1F413);
        map.put(":hatching-chick:", 0x1F423);
        map.put(":baby-chick:", 0x1F424);
        map.put(":front-facing-baby-chick:", 0x1F425);
        map.put(":bird:", 0x1F426);
        map.put(":penguin:", 0x1F427);
        map.put(":frog-face:", 0x1F438);
        map.put(":crocodile:", 0x1F40A);
        map.put(":turtle:", 0x1F422);
        map.put(":snake:", 0x1F40D);
        map.put(":dragon-face:", 0x1F432);
        map.put(":dragon:", 0x1F409);
        map.put(":spouting-whale:", 0x1F433);
        map.put(":whale:", 0x1F40B);
        map.put(":dolphin:", 0x1F42C);
        map.put(":fish:", 0x1F41F);
        map.put(":tropical-fish:", 0x1F420);
        map.put(":blowfish:", 0x1F421);
        map.put(":octopus:", 0x1F419);
        map.put(":spiral-shell:", 0x1F41A);
        map.put(":snail:", 0x1F40C);
        map.put(":bug:", 0x1F41B);
        map.put(":ant:", 0x1F41C);
        map.put(":honeybee:", 0x1F41D);
        map.put(":lady-beetle:", 0x1F41E);
        map.put(":thumbs-up:", 0x1F44D);
        map.put(":lock:", 0x1F512);
        map.put(":rocket:", 0x1F680);
        map.put(":thought:", 0x1F4AD);
        map.put(":magnifying-glass:", 0x1F50E);
        map.put(":silhouette:", 0x1F464);
        map.put(":silhouettes:", 0x1F465);
        map.put(":happy-raising-hand:", 0x1F64B);
        map.put(":information-desk-person:", 0x1F481);

        emojiMap = Collections.unmodifiableMap(map);

    }

    /**
     * Does the actual replacing for emojis. see {@link Pattern} {@link Pattern}
     * @param input test with emoticons
     * @return converted string with emojis
     */
    public static String parse(String input) {
        Matcher matcher = EMOTICON_REGEX_PATTERN.matcher(input);
        String output = "";
        int start = 0;
        int end = 0;
        while (matcher.find()) {
            String match = matcher.group();
            start = matcher.start();

            if (emojiMap.containsKey(match)) {
                output += input.substring(end, start);
                output += new String(Character.toChars(emojiMap.get(match)));
            }

            end = matcher.end();
        }

        if (end < input.length()) {
            output += input.substring(end);
        }

        return output;
    }
}

