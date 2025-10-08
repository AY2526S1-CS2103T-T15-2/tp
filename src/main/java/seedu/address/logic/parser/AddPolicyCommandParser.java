package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddPolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.PolicyId;
import seedu.address.model.policy.PolicyName;
import seedu.address.model.policy.PolicyDetails;
import seedu.address.model.policy.Policy;

/**
 * Parses input arguments and creates a new AddPolicyCommand object
 */
public class AddPolicyCommandParser implements Parser<AddPolicyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPolicyCommand
     * and returns an AddPolicyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPolicyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DETAILS);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DETAILS)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_DETAILS);
        PolicyName policyName = ParserUtil.parsePolicyName(argMultimap.getValue(PREFIX_NAME).get());
        PolicyDetails policyDetails = ParserUtil.parsePolicyDetails(argMultimap.getValue(PREFIX_DETAILS).get());
        PolicyId policyId = PolicyId.generate();

        Policy policy = new Policy(policyName, policyDetails, policyId);

        return new AddPolicyCommand(policy);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
