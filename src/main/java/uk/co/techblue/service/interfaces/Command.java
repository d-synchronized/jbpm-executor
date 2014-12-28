package uk.co.techblue.service.interfaces;

import uk.co.techblue.dto.CommandContext;
import uk.co.techblue.dto.ExecutionResults;

/**
 * The Interface Command.
 */
public interface Command {

    /**
     * Execute.
     * 
     * @param ctx the ctx
     * @return the execution results
     * @throws Exception the exception
     */
    public ExecutionResults execute(CommandContext ctx) throws Exception;
}
