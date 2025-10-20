package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.PolicyCommandTestUtil.POLICY_PATH_A;
import static seedu.address.logic.commands.PolicyCommandTestUtil.POLICY_PATH_A_DESC;
import static seedu.address.logic.parser.CliSyntax.FLAG_ALPHABETICAL_ORDER;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.PolicyUtil.unassign;
import static seedu.address.testutil.TypicalData.LIFE;
import static seedu.address.testutil.TypicalId.VALID_POLICY_ID_3;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalNricPredicates.PREDICATE_FIRST;
import static seedu.address.testutil.TypicalNrics.NRIC_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddContactCommand;
import seedu.address.logic.commands.AddContractCommand;
import seedu.address.logic.commands.AddPolicyCommand;
import seedu.address.logic.commands.AddPolicyFileCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RemoveContactCommand;
import seedu.address.logic.commands.RemoveContractCommand;
import seedu.address.logic.commands.RemovePolicyCommand;
import seedu.address.logic.commands.SortContactCommand;
import seedu.address.logic.commands.ViewContactCommand;
import seedu.address.logic.commands.ViewContractCommand;
import seedu.address.logic.commands.ViewPolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NricContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonComparatorType;
import seedu.address.model.policy.IdContainsKeywordsPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.PolicyUtil;


public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_addContact() throws Exception {
        Person person = new PersonBuilder().build();
        AddContactCommand command = (AddContactCommand) parser.parseCommand(PersonUtil.getAddContactCommand(person));
        assertEquals(new AddContactCommand(person), command);
    }

    @Test
    public void parseCommand_editContact() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_removeContact() throws Exception {
        RemoveContactCommand command = (RemoveContactCommand) parser.parseCommand(
                RemoveContactCommand.COMMAND_WORD + " " + NRIC_FIRST_PERSON);
        assertEquals(new RemoveContactCommand(PREDICATE_FIRST), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_viewContact() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        ViewContactCommand command = (ViewContactCommand) parser.parseCommand(
                ViewContactCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new ViewContactCommand(new NricContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_sortContact() throws Exception {
        SortContactCommand command = (SortContactCommand) parser.parseCommand(
                SortContactCommand.COMMAND_WORD + " " + FLAG_ALPHABETICAL_ORDER);
        assertEquals(new SortContactCommand(PersonComparatorType.ALPHABETICAL), command);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_addPolicy() throws Exception {
        AddPolicyCommand command = (AddPolicyCommand) parser.parseCommand(PolicyUtil.getAddPolicyCommand(LIFE));
        assertEquals(new AddPolicyCommand(unassign(LIFE)), command);
    }

    @Test
    public void parseCommand_addPolicyFile() throws Exception {
        AddPolicyFileCommand command = (AddPolicyFileCommand) parser.parseCommand(
                AddPolicyFileCommand.COMMAND_WORD + POLICY_PATH_A_DESC);
        assertEquals(new AddPolicyFileCommand(POLICY_PATH_A), command);
    }

    @Test
    public void parseCommand_removePolicy() throws Exception {
        RemovePolicyCommand command = (RemovePolicyCommand) parser.parseCommand(
                RemovePolicyCommand.COMMAND_WORD + " " + "p:" + VALID_POLICY_ID_3);
        assertEquals(new RemovePolicyCommand(VALID_POLICY_ID_3), command);
    }

    @Test
    public void parseCommand_viewPolicy() throws Exception {
        List<String> keywords = Arrays.asList("abcdef", "123456", "abc123");
        ViewPolicyCommand command = (ViewPolicyCommand) parser.parseCommand(
                ViewPolicyCommand.COMMAND_WORD
                        + " p: " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new ViewPolicyCommand(new IdContainsKeywordsPredicate(keywords)), command);
        assertTrue(parser.parseCommand(ViewPolicyCommand.COMMAND_WORD + " -a") instanceof ViewPolicyCommand);
    }

    @Test
    public void parseCommand_addContract() throws Exception {
        assertTrue(parser.parseCommand(
                AddContractCommand.COMMAND_WORD + " p:P1234A ic:S1234567A dt:2024-12-12 e: 2025-12-12")
                instanceof AddContractCommand);
    }

    @Test
    public void parseCommand_removeContract() throws Exception {
        assertTrue(parser.parseCommand(RemoveContractCommand.COMMAND_WORD + " C1234A")
                instanceof RemoveContractCommand);
    }

    @Test
    public void parseCommand_viewContract() throws Exception {
        assertTrue(parser.parseCommand(ViewContractCommand.COMMAND_WORD) instanceof ViewContractCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
