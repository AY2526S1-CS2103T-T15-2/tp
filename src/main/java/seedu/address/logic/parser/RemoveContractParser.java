package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveContract;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoveContractCommand object
 */
public class RemoveContractParser implements Parser<RemoveContract> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveContract
     * and returns a RemoveContract object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveContract parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new RemoveContract(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveContract.MESSAGE_USAGE), pe);
        }
    }

}
