package cn.tyiti.xfb.webservice.blacklist;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI 2.1.3-hudson-390-
 * Generated source version: 2.0
 * 
 */
@WebService(name = "ICreditInfoExtWebservice", targetNamespace = "http://webservice.internetfinance.com/")
public interface ICreditInfoExtWebservice {

	/**
	 * 
	 * @param requestParameter
	 * @return returns java.lang.String
	 */
	@WebMethod
	@WebResult(targetNamespace = "")
	@RequestWrapper(localName = "queryServiceItemByCode", targetNamespace = "http://webservice.internetfinance.com/", className = "com.tyiti.xfb.webservice.card.QueryServiceItemByCode")
	@ResponseWrapper(localName = "queryServiceItemByCodeResponse", targetNamespace = "http://webservice.internetfinance.com/", className = "com.tyiti.xfb.webservice.card.QueryServiceItemByCodeResponse")
	public String queryServiceItemByCode(
			@WebParam(name = "requestParameter", targetNamespace = "") String requestParameter);

}
