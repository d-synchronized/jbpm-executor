package uk.co.techblue.service.impl;

import javax.inject.Named;

import uk.co.techblue.dto.CommandContext;
import uk.co.techblue.dto.ExecutionResults;
import uk.co.techblue.service.interfaces.Command;

@Named(value="PrintOutCmd")
public class PrintOutCommand implements Command{

    public ExecutionResults execute(CommandContext ctx) {
        System.out.println(">>> Hi This is the first command!");
        ExecutionResults executionResults = new ExecutionResults();
        return executionResults;
    }
    
}
