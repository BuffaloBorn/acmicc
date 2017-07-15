package com.epm.acmi.util;

import java.util.Map;

import com.fujitsu.iflow.model.util.ModelInternalException;
import com.fujitsu.iflow.model.workflow.ProcessInstance;

/**
 * HistoryEvent carries information from a process instance history. 
 * @author Cesar Cardenas
 *
 */
public class HistoryEvent 
{
	private static final String IS_HANDLED = "ishandled";
	
	private static final long serialVersionUID = 1L;
    public long historyId;
    public long timestamp;
    public String eventType;
    public String responsible;
    public long producerType;
    public long producerId;
    public long consumerType;
    public long consumerId;
    public long processInstanceId;
    public boolean isHandled;
    public String eventData;

    private HistoryEvent()
    {
    	//We don't want an instantiation without data
    }
    
	/**
	 * Instantiating a HistoryEvent from a Map with field data.
	 * Useful when using ProcessInstance.getHistory(), since it 
	 * translates the event map data into a nice object.
	 * 
	 * @param historyEventMap
	 * @throws ModelInternalException
	 */
	public HistoryEvent(Map historyEventMap) throws ModelInternalException
	{
		try
		{
			historyId = ((Long)historyEventMap.get(new Integer(ProcessInstance.HISTORY_ID))).longValue();
			timestamp = ((Long)historyEventMap.get(new Integer(ProcessInstance.HISTORY_TIME_STAMP))).longValue();
			eventType = (String)historyEventMap.get(new Integer(ProcessInstance.HISTORY_EVENT_TYPE));
			responsible = (String)historyEventMap.get(new Integer(ProcessInstance.HISTORY_RESPONSIBLE));
			producerType = ((Long)historyEventMap.get(new Integer(ProcessInstance.HISTORY_PRODUCER_TYPE))).longValue();
			producerId = ((Long)historyEventMap.get(new Integer(ProcessInstance.HISTORY_PRODUCER_ID))).longValue();
			consumerType = ((Long)historyEventMap.get(new Integer(ProcessInstance.HISTORY_CONSUMER_TYPE))).longValue();
			consumerId = ((Long)historyEventMap.get(new Integer(ProcessInstance.HISTORY_CONSUMER_ID))).longValue();
			processInstanceId = ((Long)historyEventMap.get(new Integer(ProcessInstance.HISTORY_PROCESSINSTANCE_ID))).longValue();			
			eventData = (String)historyEventMap.get(new Integer(ProcessInstance.HISTORY_EVENTDATA));
			
			String isHandledString = (String)historyEventMap.get(new Integer(ProcessInstance.HISTORY_ISHANDLED));
			isHandled = (IS_HANDLED.equalsIgnoreCase(isHandledString));
			
		} catch (Throwable t)
		{
			String exString = "failed to create HistoryEvent from history event map";
			throw new ModelInternalException(t, 0, exString);
		}
	}

	public long getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(long consumerId) {
		this.consumerId = consumerId;
	}

	public long getConsumerType() {
		return consumerType;
	}

	public void setConsumerType(long consumerType) {
		this.consumerType = consumerType;
	}

	public String getEventData() {
		return eventData;
	}

	public void setEventData(String eventData) {
		this.eventData = eventData;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public long getHistoryId() {
		return historyId;
	}

	public void setHistoryId(long historyId) {
		this.historyId = historyId;
	}

	public boolean isHandled() {
		return isHandled;
	}

	public void setHandled(boolean isHandled) {
		this.isHandled = isHandled;
	}

	public long getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public long getProducerId() {
		return producerId;
	}

	public void setProducerId(long producerId) {
		this.producerId = producerId;
	}

	public long getProducerType() {
		return producerType;
	}

	public void setProducerType(long producerType) {
		this.producerType = producerType;
	}

	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
