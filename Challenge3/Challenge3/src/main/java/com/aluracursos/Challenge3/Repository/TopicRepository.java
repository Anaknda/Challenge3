package Challenge3.Repository;

import Challenge3.Topic.Topic;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long>{
    Optional<Topic> findByTitleAndMessage(String title, String message);
}
