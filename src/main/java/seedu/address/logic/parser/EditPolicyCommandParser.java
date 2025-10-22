package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PID;

import seedu.address.logic.commands.EditPolicyCommand;
import seedu.address.logic.commands.EditPolicyCommand.EditPolicyDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.PolicyId;

/**
 * Parses input arguments and creates a new EditPolicyCommand object
 */
public class EditPolicyCommandParser implements Parser<EditPolicyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditPolicyCommand
     * and returns an EditPolicyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditPolicyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PID, PREFIX_NAME, PREFIX_DETAILS);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PID, PREFIX_NAME, PREFIX_DETAILS);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPolicyCommand.MESSAGE_USAGE));
        }


        if (argMultimap.getValue(PREFIX_PID).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPolicyCommand.MESSAGE_USAGE));
        }

        PolicyId policyId = ParserUtil.parsePolicyId(argMultimap.getValue(PREFIX_PID).get());
        EditPolicyDescriptor editPolicyDescriptor = new EditPolicyDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPolicyDescriptor.setName(ParserUtil.parsePolicyName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_DETAILS).isPresent()) {
            editPolicyDescriptor.setDetails(ParserUtil.parsePolicyDetails(argMultimap.getValue(PREFIX_DETAILS).get()));
        }

        if (!editPolicyDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditPolicyCommand.MESSAGE_NOT_EDITED);
        }

        return new EditPolicyCommand(policyId, editPolicyDescriptor);
    }

}
