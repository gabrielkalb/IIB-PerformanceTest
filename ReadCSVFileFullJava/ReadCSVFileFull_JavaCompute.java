import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;
import com.ibm.broker.plugin.MbXMLNSC;


public class ReadCSVFileFull_JavaCompute extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");

		MbMessage inMessage = inAssembly.getMessage();
		MbMessageAssembly outAssembly = null;
		try {
			// create new message as a copy of the input			
			MbMessage outMessage = new MbMessage();			
			MbElement outRoot = outMessage.getRootElement();
			outRoot.addAsFirstChild(inMessage.getRootElement().getFirstChild().copy());
			
			MbElement outBody = outRoot.createElementAsLastChild(MbXMLNSC.PARSER_NAME);
			outBody.createElementAsLastChild(MbElement.TYPE_NAME, "records", null);
			MbElement recordsOut = outBody.getLastChild();			
			MbElement record = inMessage.getRootElement().getLastChild().getFirstChild().getFirstChild();
			while(true) {
				MbElement recordOut = recordsOut.createElementAsLastChild(MbElement.TYPE_NAME, "record", null);
				MbElement field = record.getFirstChild();
				int count = 1;
				while(true) {
					recordOut.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "field" + count++, field.getValue());
					field = field.getNextSibling();
					if(field==null) break;
				}				
				record = record.getNextSibling();
				if(record == null){
					break;
				}
			}
			
			outAssembly = new MbMessageAssembly(inAssembly, outMessage);
		} catch (MbException e) {
			// Re-throw to allow Broker handling of MbException
			throw e;
		} catch (RuntimeException e) {
			// Re-throw to allow Broker handling of RuntimeException
			throw e;
		} catch (Exception e) {
			// Consider replacing Exception with type(s) thrown by user code
			// Example handling ensures all exceptions are re-thrown to be handled in the flow
			throw new MbUserException(this, "evaluate()", "", "", e.toString(),
					null);
		}
		// The following should only be changed
		// if not propagating message to the 'out' terminal
		out.propagate(outAssembly);

	}

}
