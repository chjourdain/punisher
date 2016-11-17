package com.punisher.balancer.service.impl;

import com.punisher.balancer.model.Programmer;
import com.punisher.balancer.model.Stat;
import com.punisher.balancer.service.ProgrammerService;
import com.punisher.balancer.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guillaumenostrenoff on 05/05/2016.
 */
@Service
public class StatServiceImpl implements StatService {

    @Autowired
    private ProgrammerService programmerService;

    /**
     * Send top 3 bad programmers every week
     */
    @Scheduled(fixedRate=(86_400_000*7))
    @Async
    private void sendStats() {
        // get stats
        Map<String, Stat> statMap = getStats(3);

        // send stats
        // TODO (still waiting for target address)

        // reset stats
        resetStats();
    }

    /**
     * provide a sorted top bad programmers map. Only 3 first programmers are labeled.
     * @param size number of programmers in the map
     * @return
     */
    @Override
    public Map<String, Stat> getStats(int size) {
        Map<String, Stat> statMap = new HashMap<>();

        // get top <size> (or less) bp
        List<Programmer> list = programmerService.get();
        Collections.sort(list, (p1, p2) -> p1.getStats() < p2.getStats() ? 1 : (p1.getStats() == p2.getStats() ? 0 : -1));
        for(int i = 0; i < (list.size() < size ? list.size() : size); i++) {
            String label = i == 0 ? "1-PLS" : i == 1 ? "2-very bad" : i == 2 ? "3-bad" : i + "-ok";
            statMap.put(label, new Stat(list.get(i).getName(), list.get(i).getStats()));
        }
        return statMap;
    }

    private void resetStats() {
        List<Programmer> list = programmerService.get();
        list.forEach(p -> {p.setStats(0); programmerService.update(p);});

    }
}
