package uk.co.techblue.service.interfaces;

import uk.co.techblue.dto.CommandContext;
import uk.co.techblue.dto.ExecutionResults;

/**
 * The Interface CommandCallback.
 */
public interface CommandCallback {

    /**
     * On command done.
     *
     * @param ctx the ctx
     * @param results the results
     */
    void onCommandDone(CommandContext ctx, ExecutionResults results);
}
