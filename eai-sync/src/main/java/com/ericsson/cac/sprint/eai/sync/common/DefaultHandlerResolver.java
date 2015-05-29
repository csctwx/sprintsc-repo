package com.ericsson.cac.sprint.eai.sync.common;

import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

public class DefaultHandlerResolver implements HandlerResolver {
	private List<Handler> handlerList;

	public List<Handler> getHandlerChain(PortInfo portInfo) {
		return handlerList;
	}

	public void setHandlerList(List<Handler> handlerList) {
		this.handlerList = handlerList;
	}
}
