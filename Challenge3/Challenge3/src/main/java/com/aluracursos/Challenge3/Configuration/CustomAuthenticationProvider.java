package Challenge3.Configuration;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = userRepository.findByUsername(username);
        if (user!= null && user.getPassword().equals(password)) {
            return new UsernamePasswordAuthenticationToken(username, password, getAuthorities(user.getRoles()));
        } else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    private List<GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
