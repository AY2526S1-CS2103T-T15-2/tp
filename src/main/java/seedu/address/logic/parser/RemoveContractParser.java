package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.RemoveContractCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contract.ContractId;

/**
 * Parses input arguments and creates a new RemoveContractCommand object
 */
public class RemoveContractParser implements Parser<RemoveContractCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveContract
     * and returns a RemoveContract object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveContractCommand parse(String args) throws ParseException {
        try {
            ContractId cId = ParserUtil.parseContractId(args);
            return new RemoveContractCommand(cId);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveContractCommand.MESSAGE_USAGE), pe);
        }
    }

}
