package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddPolicyCommand;
import seedu.address.logic.commands.AddPolicyCommandType;
import seedu.address.logic.commands.AddPolicyFileCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.PolicyDetails;
import seedu.address.model.policy.PolicyName;
import seedu.address.model.policy.UnassignedPolicy;

/**
 * Parses input arguments and creates a new AddPolicyCommandType object
 */
public class AddPolicyCommandParser implements Parser<AddPolicyCommandType> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPolicyCommandType
     * and returns an AddPolicyCommandType object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPolicyCommandType parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DETAILS, PREFIX_FILE);

        if (argMultimap.getPreamble().isEmpty()) {
            if (arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DETAILS)
                    && arePrefixesAbsent(argMultimap, PREFIX_FILE)) {
                argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_DETAILS);
                PolicyName policyName = ParserUtil.parsePolicyName(argMultimap.getValue(PREFIX_NAME).get());
                PolicyDetails policyDetails = ParserUtil.parsePolicyDetails(argMultimap.getValue(PREFIX_DETAILS).get());

                UnassignedPolicy unassignedPolicy = new UnassignedPolicy(policyName, policyDetails);

                return new AddPolicyCommand(unassignedPolicy);
            } else if (arePrefixesPresent(argMultimap, PREFIX_FILE)
                    && arePrefixesAbsent(argMultimap, PREFIX_NAME, PREFIX_DETAILS)) {
                argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_FILE);

                try {
                    Path filePath = ParserUtil.parsePath(argMultimap.getValue(PREFIX_FILE).get());
                    return new AddPolicyFileCommand(filePath);
                } catch (InvalidPathException ipe) {
                    throw new ParseException(MESSAGE_INVALID_PATH, ipe);
                }
            }
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommandType.MESSAGE_USAGE));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if all the prefixes contain empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesAbsent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isEmpty());
    }

}
