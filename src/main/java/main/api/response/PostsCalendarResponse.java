package main.api.response;

import lombok.Data;

import java.util.*;

@Data
public class PostsCalendarResponse implements ResponseApi {

    private List<Integer> years;
    private Map<String, Integer> posts;

    public PostsCalendarResponse(List<Integer> yearsList, HashMap<java.sql.Date, Integer> yearPosts) {
        years = new ArrayList<>(yearsList);
        years.sort(Comparator.comparingInt(o -> o));
        posts = new HashMap<>();
        for(Date date : yearPosts.keySet()){
            posts.put(String.valueOf(date), yearPosts.get(date));
        }
    }
}
