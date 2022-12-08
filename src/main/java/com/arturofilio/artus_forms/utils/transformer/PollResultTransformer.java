package com.arturofilio.artus_forms.utils.transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.arturofilio.artus_forms.interfaces.IPollResult;
import com.arturofilio.artus_forms.models.responses.PollResultRest;
import com.arturofilio.artus_forms.models.responses.ResultDetailRest;

public class PollResultTransformer implements ITransformer<List<IPollResult>, List<PollResultRest>> {
    @Override
    public List<PollResultRest> transformData(List<IPollResult > data) {
        Map<String, PollResultRest> transformedData = new HashMap<String, PollResultRest>();
        
        for(IPollResult result : data) {
            PollResultRest pollResult;
            String key = Long.toString(result.getQuestionId());
            if (!transformedData.containsKey(key)) {
                List<ResultDetailRest> details = new ArrayList<ResultDetailRest>();
                details.add(new ResultDetailRest(result.getAnswer(), result.getResult()));
                pollResult = new PollResultRest(result.getQuestion(), details);
                transformedData.put(key, pollResult);
            } else {
                pollResult = transformedData.get(key);
                pollResult.getDetails().add(new ResultDetailRest(result.getAnswer(), result.getResult()));
            }
        }
        return new ArrayList<>(transformedData.values());
    }
    
}
