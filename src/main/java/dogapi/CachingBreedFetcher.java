package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    // TODO Task 2: Complete this class
    private int callsMade = 0;
    private final BreedFetcher breedFetcher;
    private final Map<String,List<String>> cache;
    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.breedFetcher = fetcher;
        this.cache = new HashMap<>();
    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        try{
            if (cache.containsKey(breed)) {
                return cache.get(breed);
            }

            callsMade++;
            List<String> subBreeds = breedFetcher.getSubBreeds(breed);
            cache.put(breed, subBreeds);
            return subBreeds;
        }
        catch(Exception e) { throw new BreedNotFoundException("Failed to fetch breed for " + breed);
        }
        // return statement included so that the starter code can compile and run.
    }

    public int getCallsMade() {
        return callsMade;
    }
}