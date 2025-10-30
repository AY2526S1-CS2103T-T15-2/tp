package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.PolicyCommandTestUtil.POLICY_ID_DESC_HOME;
import static seedu.address.logic.commands.PolicyCommandTestUtil.POLICY_PATH_A;
import static seedu.address.logic.commands.PolicyCommandTestUtil.POLICY_PATH_A_DESC;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_POLICY_ID_HOME;
import static seedu.address.logic.parser.CliSyntax.FLAG_ALPHABETICAL_ORDER;
import static seedu.address.logic.parser.CliSyntax.FLAG_LIST_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PID;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.PolicyUtil.unassign;
import static seedu.address.testutil.TypicalData.getAlice;
import static seedu.address.testutil.TypicalData.getLife;
import static seedu.address.testutil.TypicalId.VALID_POLICY_ID_3;
import static seedu.address.testutil.TypicalNricPredicates.PREDICATE_FIRST;
import static seedu.address.testutil.TypicalNrics.NRIC_FIRST_CONTACT;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.commands.AddContactCommand;
import seedu.address.logic.commands.AddContractCommand;
import seedu.address.logic.commands.AddPolicyCommand;
import seedu.address.logic.commands.AddPolicyFileCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.EditContactCommand;
import seedu.address.logic.commands.EditPolicyCommand;
import seedu.address.logic.commands.EditPolicyCommand.EditPolicyDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.RemoveAppointmentCommand;
import seedu.address.logic.commands.RemoveContactCommand;
import seedu.address.logic.commands.RemoveContractCommand;
import seedu.address.logic.commands.RemovePolicyCommand;
import seedu.address.logic.commands.SortContactCommand;
import seedu.address.logic.commands.ViewAppointmentCommand;
import seedu.address.logic.commands.ViewContactCommand;
import seedu.address.logic.commands.ViewContractCommand;
import seedu.address.logic.commands.ViewPolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.AppointmentIdContainsKeywordsPredicate;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactComparatorType;
import seedu.address.model.contact.NricContainsKeywordsPredicate;
import seedu.address.model.contract.ContractIdContainsKeywordsPredicate;
import seedu.address.model.policy.IdContainsKeywordsPredicate;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyId;
import seedu.address.testutil.ContactBuilder;
import seedu.address.testutil.ContactUtil;
import seedu.address.testutil.EditContactDescriptorBuilder;
import seedu.address.testutil.EditPolicyDescriptorBuilder;
import seedu.address.testutil.PolicyBuilder;
import seedu.address.testutil.PolicyUtil;


public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_addContact() throws Exception {
        Contact contact = new ContactBuilder().build();
        AddContactCommand command = (AddContactCommand) parser.parseCommand(ContactUtil.getAddContactCommand(contact));
        assertEquals(new AddContactCommand(contact), command);
    }

    @Test
    public void parseCommand_editContact() throws Exception {
        Contact contact = new ContactBuilder().build();
        EditContactCommand.EditContactDescriptor descriptor = new EditContactDescriptorBuilder(contact).build();
        EditContactCommand command = (EditContactCommand) parser.parseCommand(EditContactCommand.COMMAND_WORD + " "
                + PREFIX_NRIC + getAlice().getNric().toString() + " "
                + ContactUtil.getEditContactDescriptorDetails(descriptor));
        assertEquals(new EditContactCommand(getAlice().getNric(), descriptor), command);
    }

    @Test
    public void parseCommand_removeContact() throws Exception {
        RemoveContactCommand command = (RemoveContactCommand) parser.parseCommand(
                RemoveContactCommand.COMMAND_WORD + " " + PREFIX_NRIC + NRIC_FIRST_CONTACT);
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
                ViewContactCommand.COMMAND_WORD
                        + " ic: " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new ViewContactCommand(new NricContainsKeywordsPredicate(keywords)), command);
        assertTrue(parser.parseCommand(ViewContactCommand.COMMAND_WORD + " " + FLAG_LIST_ALL)
                instanceof ViewContactCommand);
    }

    @Test
    public void parseCommand_sortContact() throws Exception {
        SortContactCommand command = (SortContactCommand) parser.parseCommand(
                SortContactCommand.COMMAND_WORD + " " + FLAG_ALPHABETICAL_ORDER);
        assertEquals(new SortContactCommand(ContactComparatorType.ALPHABETICAL), command);
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
    public void parseCommand_addAppointment() throws Exception {
        assertTrue(parser.parseCommand(
                AddAppointmentCommand.COMMAND_WORD + " ic:S1234567A dt:2024-12-12 d:Details")
                instanceof AddAppointmentCommand);
    }

    @Test
    public void parseCommand_viewAppointment() throws Exception {
        List<String> keywords = Arrays.asList("abcdef", "123456", "abc123");
        ViewAppointmentCommand command = (ViewAppointmentCommand) parser.parseCommand(
                ViewAppointmentCommand.COMMAND_WORD
                        + " a: " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new ViewAppointmentCommand(new AppointmentIdContainsKeywordsPredicate(keywords)), command);
        assertTrue(parser.parseCommand(ViewAppointmentCommand.COMMAND_WORD + " " + FLAG_LIST_ALL)
                instanceof ViewAppointmentCommand);
    }

    @Test
    public void parseCommand_removeAppointment() throws Exception {
        assertTrue(parser.parseCommand(RemoveAppointmentCommand.COMMAND_WORD + " "
                + PREFIX_AID + " A1234A")
                instanceof RemoveAppointmentCommand);
    }

    @Test
    public void parseCommand_addPolicy() throws Exception {
        AddPolicyCommand command = (AddPolicyCommand) parser.parseCommand(PolicyUtil.getAddPolicyCommand(getLife()));
        assertEquals(new AddPolicyCommand(unassign(getLife())), command);
    }

    @Test
    public void parseCommand_addPolicyFile() throws Exception {
        AddPolicyFileCommand command = (AddPolicyFileCommand) parser.parseCommand(
                AddPolicyFileCommand.COMMAND_WORD + POLICY_PATH_A_DESC);
        assertEquals(new AddPolicyFileCommand(POLICY_PATH_A), command);
    }

    @Test
    public void parseCommand_editPolicy() throws Exception {
        Policy policy = new PolicyBuilder().build();
        PolicyId policyId = new PolicyId(VALID_POLICY_ID_HOME);
        EditPolicyDescriptor descriptor = new EditPolicyDescriptorBuilder(policy).build();
        EditPolicyCommand command = (EditPolicyCommand) parser.parseCommand(EditPolicyCommand.COMMAND_WORD
                + POLICY_ID_DESC_HOME + " " + PolicyUtil.getEditPolicyDescriptorDetails(descriptor));
        assertEquals(new EditPolicyCommand(policyId, descriptor), command);
    }

    @Test
    public void parseCommand_removePolicy() throws Exception {
        RemovePolicyCommand command = (RemovePolicyCommand) parser.parseCommand(
                RemovePolicyCommand.COMMAND_WORD + " " + PREFIX_PID + VALID_POLICY_ID_3);
        assertEquals(new RemovePolicyCommand(VALID_POLICY_ID_3), command);
    }

    @Test
    public void parseCommand_viewPolicy() throws Exception {
        List<String> keywords = Arrays.asList("abcdef", "123456", "abc123");
        ViewPolicyCommand command = (ViewPolicyCommand) parser.parseCommand(
                ViewPolicyCommand.COMMAND_WORD + " "
                        + PREFIX_PID + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new ViewPolicyCommand(new IdContainsKeywordsPredicate(keywords)), command);
        assertTrue(parser.parseCommand(ViewPolicyCommand.COMMAND_WORD + " -a") instanceof ViewPolicyCommand);
    }

    @Test
    public void parseCommand_addContract() throws Exception {
        assertTrue(parser.parseCommand(
                AddContractCommand.COMMAND_WORD + " p:P1234A ic:S1234567A dt:2024-12-12 e: 2025-12-12 pr:1000.00")
                instanceof AddContractCommand);
    }

    @Test
    public void parseCommand_removeContract() throws Exception {
        assertTrue(parser.parseCommand(RemoveContractCommand.COMMAND_WORD + " " + PREFIX_CID + " C1234A")
                instanceof RemoveContractCommand);
    }

    @Test
    public void parseCommand_viewContract() throws Exception {
        List<String> keywords = Arrays.asList("C1234A", "C1234B", "C1234C");
        ViewContractCommand command = (ViewContractCommand) parser.parseCommand(
                ViewContractCommand.COMMAND_WORD + " " + PREFIX_CID
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new ViewContractCommand(new ContractIdContainsKeywordsPredicate(keywords)), command);
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
