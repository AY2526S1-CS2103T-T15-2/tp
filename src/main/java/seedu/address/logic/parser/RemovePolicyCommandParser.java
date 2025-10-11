package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.RemovePolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.PolicyId;

/**
 * Parses input arguments and creates a new RemovePolicyCommand object
 */
public class RemovePolicyCommandParser implements Parser<RemovePolicyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemovePolicyCommand
     * and returns a RemovePolicyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemovePolicyCommand parse(String args) throws ParseException {
        try {
            PolicyId id = ParserUtil.parsePolicyId(args);
            return new RemovePolicyCommand(id);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemovePolicyCommand.MESSAGE_USAGE), pe);
        }
    }

}

