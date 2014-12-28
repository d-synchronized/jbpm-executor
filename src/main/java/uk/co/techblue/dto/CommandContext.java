package uk.co.techblue.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * The Class CommandContext.
 */
public class CommandContext implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1663994197725264071L;

    /** The command context data. */
    private Map<String, Object> commandContextData;

    /**
     * Gets the command context data.
     * 
     * @return the command context data
     */
    public Map<String, Object> getCommandContextData() {
        return commandContextData;
    }

    /**
     * Sets the command context data.
     * 
     * @param commandContextData the command context data
     */
    public void setCommandContextData(Map<String, Object> commandContextData) {
        this.commandContextData = commandContextData;
    }

}
