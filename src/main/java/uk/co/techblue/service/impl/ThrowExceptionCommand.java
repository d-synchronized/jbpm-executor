package uk.co.techblue.service.impl;

import javax.inject.Named;

import uk.co.techblue.dto.CommandContext;
import uk.co.techblue.dto.ExecutionResults;
import uk.co.techblue.service.interfaces.Command;

/**
 * The Class ThrowExceptionCommand.
 */
@Named(value = "ThrowExceptionCmd")
public class ThrowExceptionCommand implements Command {

    /* (non-Javadoc)
     * @see uk.co.techblue.service.interfaces.Command#execute(uk.co.techblue.dto.CommandContext)
     */
    public ExecutionResults execute(CommandContext ctx) {
        System.out.println(">>> Hi This is the Exception command!");
        throw new RuntimeException("Test Exception!");
    }

}
