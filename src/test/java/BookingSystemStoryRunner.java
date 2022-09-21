import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.StoryFinder;
import org.junit.Test;

import java.util.List;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;

public class BookingSystemStoryRunner {
    @Test
    public void runBookingSystemStories() {
        Embedder embedder = new BookingSystemStoryEmbedder();
        List<String> storyPaths = new StoryFinder().findPaths(codeLocationFromClass(this.getClass()), "**/stories/bookingsystem/*.story", "");
        embedder.runStoriesAsPaths(storyPaths);
    }
}
