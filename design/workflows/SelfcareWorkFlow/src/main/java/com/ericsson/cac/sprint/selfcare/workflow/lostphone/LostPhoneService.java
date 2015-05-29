/**
 * Sprint Template
 */
package com.ericsson.cac.sprint.selfcare.workflow.lostphone;

import com.ericsson.cac.sprint.selfcare.workflow.lostphone.model.RestoreRequest;
import com.ericsson.cac.sprint.selfcare.workflow.lostphone.model.RestoreResponse;
import com.ericsson.cac.sprint.selfcare.workflow.lostphone.model.SuspendRequest;
import com.ericsson.cac.sprint.selfcare.workflow.lostphone.model.SuspendResponse;

/**
 * LostPhone is used to call the external adapters.
 * 
 * @author Ericsson
 * @version $Revision: 1.0 $
 */
public interface LostPhoneService {

	SuspendResponse suspendSubscriber(SuspendRequest request);

	RestoreResponse restoreSubscriber(RestoreRequest request);
}
