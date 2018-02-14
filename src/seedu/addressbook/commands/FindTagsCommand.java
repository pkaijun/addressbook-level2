package seedu.addressbook.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.data.tag.UniqueTagList;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindTagsCommand extends Command {

    public static final String COMMAND_WORD = "findtags";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose tag contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " owesMoney friends classmate";

    private final Set<String> keywords;

    public FindTagsCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Returns a copy of keywords in this command.
     */
    public Set<String> getKeywords() {
        return new HashSet<>(keywords);
    }

    @Override
    public CommandResult execute() {
        final List<ReadOnlyPerson> personsFound = getPersonsWithTagsContainingAnyKeyword(keywords);
        return new CommandResult(getMessageForPersonListShownSummary(personsFound), personsFound);
    }

    /**
     * Retrieves all persons in the address book whose names contain some of the specified keywords.
     *
     * @param keywords for searching
     * @return list of persons found
     */
    private List<ReadOnlyPerson> getPersonsWithTagsContainingAnyKeyword(Set<String> keywords) {
        final List<ReadOnlyPerson> matchedPersons = new ArrayList<>();
        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            final UniqueTagList personTags = person.getTags();
            final Set<String> wordsInNameRaw = getRawTagName(personTags.toSet());
            if (!Collections.disjoint(wordsInNameRaw, keywords)) {
                matchedPersons.add(person);
            }
        }
        return matchedPersons;
    }

    // Returns the raw tag name in a Set
    private Set<String> getRawTagName(Set<Tag> set) {
        Set<String> rawTags = new HashSet<>();
        for(Tag s : set)
            rawTags.add(s.getRawString());

        return rawTags;
    }

}

