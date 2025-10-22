package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CID;

import java.util.stream.Stream;

import seedu.address.logic.commands.RemoveContractCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contract.ContractId;


/**
 * Parses input arguments and creates a new RemoveContractCommand object
 */
public class RemoveContractCommandParser implements Parser<RemoveContractCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveContractCommand
     * and returns a RemoveContractCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveContractCommand parse(String args) throws ParseException {
        ArgumentMultimap arguMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CID);
        if (!arePrefixesPresent(arguMultimap, PREFIX_CID) || !arguMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemoveContractCommand.MESSAGE_USAGE));
        }
        arguMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CID);
        ContractId id = ParserUtil.parseContractId(arguMultimap.getValue(PREFIX_CID).get());
        return new RemoveContractCommand(id);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
