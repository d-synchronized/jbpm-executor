package uk.co.techblue;

import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Named;

import uk.co.techblue.dto.CommandContext;
import uk.co.techblue.dto.ExecutionResults;
import uk.co.techblue.service.interfaces.CommandCallback;

@Named(value = "SimpleIncrementCallback")
public class SimpleIncrementCallback implements CommandCallback {

    public void onCommandDone(CommandContext ctx, ExecutionResults results) {
        String businessKey = (String) ctx.getCommandContextData().get("businessKey");
        System.out.println(" >>> Before Incrementing = "
                + ((AtomicLong) BasicExecutorBaseTest.cachedEntities.get(businessKey)).get());
        ((AtomicLong) BasicExecutorBaseTest.cachedEntities.get(businessKey)).incrementAndGet();
        System.out.println(" >>> After Incrementing = " + BasicExecutorBaseTest.cachedEntities.get(businessKey));
    }
}
