/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.rest.api.cycle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.rest.util.ActivitiRequest;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;


/**
 * @author Nils Preusker (nils.preusker@camunda.com)
 */
public class UserConfigGet extends ActivitiCycleWebScript {

  @Override
  void execute(ActivitiRequest req, Status status, Cache cache, Map<String, Object> model) {
    
    Map<String, List<String>> connectors = this.cycleService.getConfiguredConnectors(req.getCurrentUserId());
    Map<String, List<Map<String,String>>> connectorsForJson = new HashMap<String, List<Map<String, String>>>();
    
    for(String key : connectors.keySet()) {
      List <Map<String, String>> configs = new ArrayList<Map<String, String>>();
      for(String configId : connectors.get(key)) {
        configs.add(this.cycleService.getConfigurationValues(configId, req.getCurrentUserId()));
      }
      connectorsForJson.put(key, configs);
    }
    
    
    model.put("userConfig", connectorsForJson);
  }
}
