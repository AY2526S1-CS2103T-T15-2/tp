package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PID;

import java.time.LocalDate;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddContractCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contract.Contract;
import seedu.address.model.person.Nric;
import seedu.address.model.policy.PolicyId;

/**
 * Parses input arguments and creates a new AddContractCommand object
 */
public class AddContractCommandParser implements Parser<AddContractCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddContract
     * and returns an AddContract object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddContractCommand parse(String args) throws ParseException {
        ArgumentMultimap arguMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PID, PREFIX_NRIC, PREFIX_DATE);

        if (!arePrefixesPresent(arguMultimap, PREFIX_PID, PREFIX_NRIC, PREFIX_DATE)
                || !arguMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContractCommand.MESSAGE_USAGE));
        }

        arguMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PID, PREFIX_NRIC, PREFIX_DATE);
        PolicyId pid = ParserUtil.parsePolicyId(arguMultimap.getValue(PREFIX_PID).get());
        Nric nric = ParserUtil.parseNric(arguMultimap.getValue(PREFIX_NRIC).get());
        LocalDate date = ParserUtil.parseDate(arguMultimap.getValue(PREFIX_DATE).get());

        Contract contract = new Contract(nric, pid, date);

        return new AddContractCommand(contract);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
