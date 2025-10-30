package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.commands.AddContactCommand;
import seedu.address.logic.commands.AddContractCommand;
import seedu.address.logic.commands.AddPolicyCommandType;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditAppointmentCommand;
import seedu.address.logic.commands.EditContactCommand;
import seedu.address.logic.commands.EditContractCommand;
import seedu.address.logic.commands.EditPolicyCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.RemoveAppointmentCommand;
import seedu.address.logic.commands.RemoveContactCommand;
import seedu.address.logic.commands.RemoveContractCommand;
import seedu.address.logic.commands.RemovePolicyCommand;
import seedu.address.logic.commands.SortAppointmentCommand;
import seedu.address.logic.commands.SortContactCommand;
import seedu.address.logic.commands.SortContractCommand;
import seedu.address.logic.commands.ViewAppointmentCommand;
import seedu.address.logic.commands.ViewContactCommand;
import seedu.address.logic.commands.ViewContractCommand;
import seedu.address.logic.commands.ViewPolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddContactCommand.COMMAND_WORD:
            return new AddContactCommandParser().parse(arguments);

        case EditContactCommand.COMMAND_WORD:
            return new EditContactCommandParser().parse(arguments);

        case RemoveContactCommand.COMMAND_WORD:
            return new RemoveContactCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ViewContactCommand.COMMAND_WORD:
            return new ViewContactCommandParser().parse(arguments);

        case SortContactCommand.COMMAND_WORD:
            return new SortContactCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddAppointmentCommand.COMMAND_WORD:
            return new AddAppointmentCommandParser().parse(arguments);

        case ViewAppointmentCommand.COMMAND_WORD:
            return new ViewAppointmentCommandParser().parse(arguments);

        case RemoveAppointmentCommand.COMMAND_WORD:
            return new RemoveAppointmentCommandParser().parse(arguments);

        case SortAppointmentCommand.COMMAND_WORD:
            return new SortAppointmentCommandParser().parse(arguments);

        case EditAppointmentCommand.COMMAND_WORD:
            return new EditAppointmentCommandParser().parse(arguments);

        case AddPolicyCommandType.COMMAND_WORD:
            return new AddPolicyCommandParser().parse(arguments);

        case EditPolicyCommand.COMMAND_WORD:
            return new EditPolicyCommandParser().parse(arguments);

        case RemovePolicyCommand.COMMAND_WORD:
            return new RemovePolicyCommandParser().parse(arguments);

        case ViewPolicyCommand.COMMAND_WORD:
            return new ViewPolicyCommandParser().parse(arguments);

        case AddContractCommand.COMMAND_WORD:
            return new AddContractCommandParser().parse(arguments);

        case RemoveContractCommand.COMMAND_WORD:
            return new RemoveContractCommandParser().parse(arguments);

        case EditContractCommand.COMMAND_WORD:
            return new EditContractCommandParser().parse(arguments);

        case ViewContractCommand.COMMAND_WORD:
            return new ViewContractCommandParser().parse(arguments);

        case SortContractCommand.COMMAND_WORD:
            return new SortContractCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
