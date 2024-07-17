package Challenge3.Controller;

import Challenge3.Repository.TopicRepository;
import Challenge3.Service.TokenService;

@RestController
@RequestMapping("/topics")
public class TopicController {
    @Autowired
    private TopicRepository topicRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<Topic> createTopic(@RequestBody @Valid Topic topic) {
        if (topicRepository.findByTitleAndMessage(topic.getTitle(), topic.getMessage()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Topic with same title and message already exists");
        }
        topic.setCreatedAt(new Date());
        topic.setEnabled(true);
        Topic savedTopic = topicRepository.save(topic);
        return ResponseEntity.ok(savedTopic);
    }
    @GetMapping
    public ResponseEntity<List<Topic>> getAllTopics() {
        List<Topic> topics = topicRepository.findAll();
        return ResponseEntity.ok(topics);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Topic> getTopic(@PathVariable Long id) {
        Optional<Topic> topic = topicRepository.findById(id);
        if (!topic.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found");
        }
        return ResponseEntity.ok(topic.get());
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Topic> updateTopic(@PathVariable Long id, @RequestBody @Valid Topic topic) {
        Optional<Topic> existingTopic = topicRepository.findById(id);
        if (!existingTopic.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found");
        }
        if (topicRepository.findByTitleAndMessage(topic.getTitle(), topic.getMessage()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Topic with same title and message already exists");
        }
        Topic updatedTopic = existingTopic.get();
        updatedTopic.setTitle(topic.getTitle());
        updatedTopic.setMessage(topic.getMessage());
        updatedTopic.setAuthor(topic.getAuthor());
        updatedTopic.setCourse(topic.getCourse());
        topicRepository.save(updatedTopic);
        return ResponseEntity.ok(updatedTopic);
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        Optional<Topic> topic = topicRepository.findById(id);
        if (!topic.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found");
        }
        topicRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @RestController
    @RequestMapping("/topics")
    public class TopicController {
        @Autowired
        private TokenService tokenService;

        @PostMapping
        @Transactional
}
