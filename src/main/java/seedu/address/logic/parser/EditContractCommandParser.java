package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREMIUM;

import seedu.address.logic.commands.EditContractCommand;
import seedu.address.logic.commands.EditContractCommand.EditContractDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contract.ContractId;

/**
 * Parses input arguments and creates a new EditContractCommand object
 */
public class EditContractCommandParser implements Parser<EditContractCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditContractCommand
     * and returns an EditContractCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public EditContractCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap =
                ArgumentTokenizer.tokenize(args, PREFIX_CID, PREFIX_PID, PREFIX_NRIC, PREFIX_DATE, PREFIX_EXPIRY,
                        PREFIX_PREMIUM);

        String contractId;
        ContractId cId;
        EditContractDescriptor editContractDescriptor = new EditContractDescriptor();
        if (argMultiMap.getValue(PREFIX_CID).isPresent()) {
            contractId = argMultiMap.getValue(PREFIX_CID).get();
            editContractDescriptor.setCId(ParserUtil.parseContractId(argMultiMap.getValue(PREFIX_CID).get()));
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditContractCommand.MESSAGE_USAGE));
        }
        try {
            cId = ParserUtil.parseContractId(contractId);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditContractCommand.MESSAGE_USAGE), pe);
        }

        argMultiMap.verifyNoDuplicatePrefixesFor(PREFIX_CID, PREFIX_PID, PREFIX_NRIC, PREFIX_DATE, PREFIX_EXPIRY,
                PREFIX_PREMIUM);

        if (argMultiMap.getValue(PREFIX_PID).isPresent()) {
            editContractDescriptor.setPId(ParserUtil.parsePolicyId(argMultiMap.getValue(PREFIX_PID).get()));
        }
        if (argMultiMap.getValue(PREFIX_NRIC).isPresent()) {
            editContractDescriptor.setNric(ParserUtil.parseNric(argMultiMap.getValue(PREFIX_NRIC).get()));
        }
        if (argMultiMap.getValue(PREFIX_DATE).isPresent()) {
            editContractDescriptor.setDate(ParserUtil.parseDate(argMultiMap.getValue(PREFIX_DATE).get()));
        }
        if (argMultiMap.getValue(PREFIX_EXPIRY).isPresent()) {
            editContractDescriptor.setExpiryDate(ParserUtil.parseDate(argMultiMap.getValue(PREFIX_EXPIRY).get()));
        }
        if (argMultiMap.getValue(PREFIX_PREMIUM).isPresent()) {
            editContractDescriptor.setPremium(ParserUtil.parseContractPremium(argMultiMap.getValue(PREFIX_PREMIUM)
                    .get()));
        }

        if (!editContractDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditContractCommand.MESSAGE_NOT_EDITED);
        }

        return new EditContractCommand(cId, editContractDescriptor);
    }
}
