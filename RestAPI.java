package tBonitaRestClient;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bonitasoft.engine.api.ApiAccessType;
import org.bonitasoft.engine.api.LoginAPI;
import org.bonitasoft.engine.api.ProcessAPI;
import org.bonitasoft.engine.api.TenantAPIAccessor;
import org.bonitasoft.engine.bpm.process.ProcessInstance;
import org.bonitasoft.engine.bpm.process.ProcessInstanceCriterion;
import org.bonitasoft.engine.bpm.process.ProcessInstanceSearchDescriptor;
import org.bonitasoft.engine.bpm.process.ProcessInstanceState;
import org.bonitasoft.engine.search.SearchOptionsBuilder;
import org.bonitasoft.engine.search.SearchResult;
import org.bonitasoft.engine.session.APISession;
import org.bonitasoft.engine.util.APITypeManager;

public class RestAPI {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub

		
		Map<String, String> settings = new HashMap<String, String>();
		settings.put("server.url", "http://localhost:8080");
		settings.put("application.name", "bonita");
		APITypeManager.setAPITypeAndParams(ApiAccessType.HTTP, settings);
		// get the LoginAPI using the TenantAPIAccessor
		LoginAPI loginAPI = TenantAPIAccessor.getLoginAPI();
		// log in to the tenant to create a session
		APISession apiSession = loginAPI.login("walter.bates", "bpm");
		
		final ProcessAPI processAPI = TenantAPIAccessor.getProcessAPI(apiSession);

		ProcessInstance pro = processAPI.getProcessInstance(5005);
		//List<ArchivedActivityInstance>  archPro = processAPI.getArchivedProcessInstances(1,100,null);
		
		final SearchOptionsBuilder builder = new SearchOptionsBuilder(0, 100);
		
     //   final SearchOptionsBuilder searchOptionsBuilder = new SearchOptionsBuilder(0, 2);
 //       searchOptionsBuilder.sort(ArchivedProcessInstancesSearchDescriptor.ARCHIVE_DATE, Order.ASC);
		builder.filter(ProcessInstanceSearchDescriptor.ID, "5005");
   //     searchOptionsBuilder.filter(ArchivedProcessInstancesSearchDescriptor.STATE_ID, ProcessInstanceState.STARTED.getId());
        
		final SearchResult<ProcessInstance> processInstanceResults = processAPI.searchProcessInstances(builder.done());

		List<ProcessInstance> li  = processInstanceResults.getResult();
		
		for (Iterator iterator = li.iterator(); iterator.hasNext();) {
			ProcessInstance type = (ProcessInstance) iterator.next();
			
			System.out.println("iiii---ss00000-------------"+type);
		}
		

		System.out.println("ffff---"+processAPI.getChildrenInstanceIdsOfProcessInstance(5005,0,100,ProcessInstanceCriterion.DEFAULT));
		
		
		final SearchOptionsBuilder searchOptionsBuilder = new SearchOptionsBuilder(0,100);
        searchOptionsBuilder.differentFrom(ProcessInstanceSearchDescriptor.ID, 5005);
        final SearchResult<ProcessInstance> processInstanceResults1 =	processAPI.searchProcessInstances(searchOptionsBuilder.done());
		
        List<ProcessInstance> li1  = processInstanceResults1.getResult();
        
        for (Iterator iterator = li1.iterator(); iterator.hasNext();) {
			ProcessInstance type = (ProcessInstance) iterator.next();
			System.out.println("iiifffi---ss00000-------------"+type.getId() + "  - - - -"+type.getName());
			System.out.println("iiifffi---ss00000-------------"+type.getId() + "  - - - -"+type.getName());
			System.out.println("======="+processAPI.getProcessDataInstance("s1Val",type.getId()).getValue());
		}

		loginAPI.logout(apiSession);
		
		
	}

}
