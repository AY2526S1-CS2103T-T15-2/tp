package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.IntStream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NricContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.ui.ListPanelType;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class RemoveContactCommand extends Command {

    public static final String COMMAND_WORD = "remove_contact";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the NRIC used in the displayed person list.\n"
            + "Parameters: IC \n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    public static final String MESSAGE_DELETE_PERSON_FAILURE = "Could not delete person "
            + "since no such NRIC exists within current contact list.";
    public static final String MESSAGE_REMOVE_CONTACT_PENDING = "Contracts exists under this policy. "
            + "Please remove before proceeding: %1$s";

    private final NricContainsKeywordsPredicate predicate;

    public RemoveContactCommand(NricContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        int index = findIndex(lastShownList, predicate);
        if (index == -1) {
            throw new CommandException(MESSAGE_DELETE_PERSON_FAILURE);
        }
        Index personIndex = Index.fromZeroBased(index);
        Person person = lastShownList.get(personIndex.getZeroBased());

        //check for existing contracts
        if (!person.getContracts().isEmpty()) {
            String existingContractIds = person.getContractIdsAsString();
            throw new CommandException(String.format(MESSAGE_REMOVE_CONTACT_PENDING, existingContractIds));
        }

        model.deletePerson(person);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS,
                person.getName() + " " + person.getNric()), ListPanelType.CONTACT);
    }

    private static int findIndex(List<Person> list, NricContainsKeywordsPredicate predicate) {
        return IntStream.range(0, list.size())
                .filter(i -> predicate.test(list.get(i)))
                .findFirst()
                .orElse(-1);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveContactCommand)) {
            return false;
        }

        RemoveContactCommand otherRemoveContactCommand = (RemoveContactCommand) other;
        return predicate.equals(otherRemoveContactCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
