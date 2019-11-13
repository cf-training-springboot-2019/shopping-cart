package com.trainingspringboot.shoppingcart.utils.constant;

public class ShoppingCartConstant {

	/**
	 * MDC Constants
	 */
	public static final String TRACE_ID = "trace-id";
	public static final String OPERATION = "operation";

	/**
	 * Entities
	 */
	public static final String CART = "Cart";
	public static final String CART_ITEM = "Cart Item";


	/**
	 * Header Names
	 */
	public static final String TRACE_ID_HEADER = "Trace-Id";

	/**
	 * Messages
	 */
	public static final String ENTITY_NOT_FOUND_MSG = "Entity {%s} :: UID {%s} not found.";
	public static final String LOGGING_HANDLER_INBOUND_MSG = "Received HTTP [%s] Request to [%s] at [%s]";
	public static final String LOGGING_HANDLER_OUTBOUND_MSG = "Responded with Status [%s] at [%s]";
	public static final String LOGGING_HANDLER_PROCESS_TIME_MSG = "Total processing time [%s] ms";

	/**
	 * Rest Client
	 */
	public static final String ITEM_STORAGE_BASE_URL = "http://localhost:8080/item-storage/api/v1/items";
	public static final String ITEM_STORAGE_DISPATCH_URI = "dispatch";

	/**
	 * Misc
	 */
	public static final String FRONT_SLASH_SEPARATOR = "/";

}
